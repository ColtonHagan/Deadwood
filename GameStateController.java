import java.util.*;

class GameStateController extends DeadwoodController {
    private final GameState gameModel;

    public GameStateController(int totalPlayers) throws Exception {
        this.gameModel = new GameState(totalPlayers);
    }

    public void setUpGame() throws Exception {
        int rank = 1;
        int money = 0;
        int credits = 0;
        int days = 4;

        //TEMPORARY TEST, CHANGE BACK LATER
        if (gameModel.getTotalPlayers() == 2 || gameModel.getTotalPlayers() == 3) {
            //days = 3;
            days = 2;
        } else if (gameModel.getTotalPlayers() == 5) {
            credits = 2;
        } else if (gameModel.getTotalPlayers() == 6) {
            credits = 4;
        } else if (gameModel.getTotalPlayers() == 7 || gameModel.getTotalPlayers() == 8) {
            rank = 2;
        } else if (gameModel.getTotalPlayers() < 2 || gameModel.getTotalPlayers() > 8) {
            getView().printUnsupportedPlayers();
            return;
        }

        getView().printPlayerCount(gameModel.getTotalPlayers());
        gameModel.setTotalDays(days);

        parseData dataParser = new parseData();
        gameModel.getSceneLibrary().createScenes(dataParser);
        gameModel.getBoard().createBoard(dataParser, gameModel.getSceneLibrary());

        String[] namesList = {"Blue", "Cyan", "Green", "Orange", "Pink", "Red", "Violet", "Yellow"};

        PlayerModel[] players = new PlayerModel[gameModel.getTotalPlayers()];
        for (int i = 0; i < gameModel.getTotalPlayers(); i++) {
            String name = namesList[i];
            players[i] = new PlayerModel(name, money, credits, rank, gameModel.getBoard().getTrailer());
        }

        gameModel.setAllPlayers(players);
        updateModel(gameModel.getCurrentPlayer());
        createOffice(dataParser);
        playGame();
    }

    public void endRoom(Room currentRoom) {
        getView().printSceneEnd();
        bonusPayment(currentRoom);
        currentRoom.setScene(null);
        gameModel.getBoard().removeRoom();

        gameModel.getCurrentPlayer().getCurrentRoom().clearExtras();

        for (PlayerModel player : gameModel.getPlayers()) {
            if (player.getCurrentRoom() == currentRoom) {
                player.removeRole();
                player.updateHasRole(false);
                player.updatePracticeChips(0);
            }
        }
        gameModel.getCurrentPlayer().updateMoved(true);
    }

    public void bonusPayment(Room currentRoom) {
        Role[] allRoles = currentRoom.availableRoles();
        ArrayList<PlayerModel> playersOnCard = new ArrayList<>();
        ArrayList<PlayerModel> playersOffCard = new ArrayList<>();

        for (Role currentRole : allRoles) {
            if (currentRole.getUsedBy() != null) {
                if (!currentRole.getExtra()) {
                    playersOnCard.add(currentRole.getUsedBy());
                } else {
                    playersOffCard.add(currentRole.getUsedBy());
                }
            }
        }

        if (playersOnCard.size() != 0) {
            Random rand = new Random();
            int bonus;
            Integer[] diceRolls = new Integer[currentRoom.getSceneCard().getBudget()];
            for (int i = 0; i < diceRolls.length; i++) {
                diceRolls[i] = rand.nextInt(6) + 1;
            }
            Arrays.sort(diceRolls, Collections.reverseOrder());
            playersOnCard.sort(Comparator.comparing(PlayerModel::getRoleRank));
            Collections.reverse(playersOnCard);
            for (int i = 0; i < playersOnCard.size(); i++) {
                bonus = diceRolls[i];
                playersOnCard.get(i).updateMoney(playersOnCard.get(i).getMoney() + diceRolls[i]);
                getView().showBonusPayment(playersOnCard.get(i).getName(), "on card", bonus);
            }
            for (int i = 0; i < playersOffCard.size(); i++) {
                bonus = playersOffCard.get(i).getCurrentRole().getRank();
                playersOffCard.get(i).updateMoney(playersOffCard.get(i).getCurrentRole().getRank() + playersOffCard.get(i).getMoney());
                getView().showBonusPayment(playersOffCard.get(i).getName(), "extra", bonus);
            }
        } else {
            getView().noBonusPayment();
        }
    }

    public void playGame() {
        getView().inputWelcome();

        while (gameModel.getCurrentDay() <= gameModel.getTotalDays()) {
            getView().inputChoose();
            String[] userInputArray = getInput().split(" ");
            switch (userInputArray[0]) {
                case "Upgrade":
                    if (userInputArray.length == 3) {
                        int targetRank = Integer.parseInt(userInputArray[2]);
                        if (userInputArray[0].equals("Credits")) {
                            upgradeRankCredits(targetRank);
                        } else if (userInputArray[1].equals("Dollars")) {
                            upgradeRankDollars(targetRank);
                        } else {
                            getView().inputUpgradeWrongPaymentType();
                        }
                    } else {
                        getView().inputUpgradeMissingInfo();
                    }
                    break;

                case "Move":
                    if (userInputArray.length > 1) {
                        String roomName = concatenateArray(userInputArray, 1, userInputArray.length - 1);
                        Room room = roomNameToRoom(roomName);
                        if (room != null) {
                            move(room);
                        } else {
                            getView().inputMoveInvalidRoom();
                        }
                    } else {
                        getView().inputMoveMissingInfo();
                    }
                    break;

                case "Work":  //take role
                    if (userInputArray.length > 1) {
                        String roleName = concatenateArray(userInputArray, 1, userInputArray.length - 1);
                        Role role = roleNameToRole(roleName);

                        if (role != null) {
                            if (getSystem().checkCanAddRole(role)) {
                                addRole(role);
                                role.setUsedBy(gameModel.getCurrentPlayer());
                            } else {
                                getView().printAddRoleError();
                            }
                        } else {
                            getView().inputWorkInvalidRole();
                        }
                    } else {
                        getView().inputWorkMissingInfo();
                    }
                    break;

                case "Act":
                    if (getSystem().checkCanAct()) {
                        act();
                        if (gameModel.getCurrentPlayer().getCurrentRoom().getShotCounters() == 0) {
                            endRoom(gameModel.getCurrentPlayer().getCurrentRoom());
                        }
                    } else {
                        getView().printActError();
                    }
                    break;

                case "Rehearsing":
                    rehearse();
                    break;

                case "Active":
                    if (userInputArray[1].equals("Player?")) {
                        if (gameModel.getCurrentPlayer().getHasRole()) {
                            getView().printPlayerDetails(gameModel.getCurrentPlayer().getName(), gameModel.getCurrentPlayer().getMoney(), gameModel.getCurrentPlayer().getCredits(), gameModel.getCurrentPlayer().getRank(),
                                    gameModel.getCurrentPlayer().getCurrentRole().getName(), gameModel.getCurrentPlayer().getCurrentRole().getTagLine());
                        } else {
                            getView().printPlayerDetailsNoRole(gameModel.getCurrentPlayer().getName(), gameModel.getCurrentPlayer().getMoney(), gameModel.getCurrentPlayer().getCredits(), gameModel.getCurrentPlayer().getRank());
                        }
                    } else {
                        getView().inputError();
                    }
                    break;

                case "Where":
                    if (!gameModel.getCurrentPlayer().getHasRole()) {
                        getView().playerLocation(gameModel.getCurrentPlayer().getCurrentRoom().getName());
                    } else if (gameModel.getCurrentPlayer().getCurrentRole().getExtra()) {
                        getView().playerLocationWithExtraRole(gameModel.getCurrentPlayer().getCurrentRoom().getName(), gameModel.getCurrentPlayer().getCurrentRole().getName());
                    } else {
                        getView().playerLocationWithOnCardRole(gameModel.getCurrentPlayer().getCurrentRoom().getName(), gameModel.getCurrentPlayer().getCurrentRole().getName(),
                                gameModel.getCurrentPlayer().getCurrentRoom().getSceneCard().getName());
                    }
                    break;

                case "Locations":
                    for (PlayerModel currentPlayer : gameModel.getPlayers()) {
                        if (currentPlayer == gameModel.getCurrentPlayer()) {
                            if (gameModel.getCurrentPlayer().getHasRole()) {
                                getView().activePlayerLocationWithRole(currentPlayer.getName(), currentPlayer.getCurrentRoom().getName(), currentPlayer.getCurrentRole().getName());
                            } else {
                                getView().activePlayerLocation(currentPlayer.getName(), currentPlayer.getCurrentRoom().getName());
                            }
                        } else {
                            if (currentPlayer.getHasRole()) {
                                getView().inactivePlayerLocationWithRole(currentPlayer.getName(), currentPlayer.getCurrentRoom().getName(), currentPlayer.getCurrentRole().getName());
                            } else {
                                getView().inactivePlayerLocation(currentPlayer.getName(),currentPlayer.getCurrentRoom().getName());
                            }
                        }
                    }
                    break;

                case "Roles":
                    getView().printRoles(gameModel.getCurrentPlayer().getCurrentRoom().availableRoles());
                    break;

                case "End":
                    endTurn();
                    break;

                default:
                    getView().inputError();
                    break;
            }
        }
        endGame();
    }

    public String getInput() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public String concatenateArray(String[] array, int startIndex, int endIndex) {
        String combined = array[startIndex];
        for (int i = startIndex + 1; i <= endIndex; i++) {
            combined += " " + array[i];
        }
        return combined;
    }

    public Room roomNameToRoom(String roomName) {
        for (Room room : gameModel.getBoard().allRooms()) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

    public Role roleNameToRole(String roleName) {
        for (Room room : gameModel.getBoard().allRooms()) {
            for (Role role : room.availableRoles()) {
                if (role.getName().equals(roleName)) {
                    return role;
                }
            }
        }
        return null;
    }

    public void endTurn() {
      clearMoved();
      clearWorked();
      if (gameModel.getCurrentPlayerInt() + 1 < gameModel.getTotalPlayers()) {
         gameModel.setCurrentPlayerInt(gameModel.getCurrentPlayerInt() + 1);
         updateModel(gameModel.getCurrentPlayer());
      } else {
         gameModel.setCurrentPlayerInt(0);
         updateModel(gameModel.getCurrentPlayer());
      }
      getView().showEndTurn(gameModel.getCurrentPlayer().getName());
      if (gameModel.getBoard().getCurrentRooms() == 1) {
            endDay();
      }
    }

    public void endDay() {
        gameModel.getBoard().resetBoard(gameModel.getSceneLibrary());
        for (PlayerModel player : gameModel.getPlayers()) {
            player.updateCurrentRoom(gameModel.getBoard().getTrailer());
            player.removeRole();
            player.updatePracticeChips(0);
            clearWorked();
            clearMoved();
        }
        getView().showEndDay();
        gameModel.setCurrentDay(gameModel.getCurrentDay() + 1);
    }

    public void endGame() {
        getView().showEndGame();
        int score;
        int highestScore = 0;
        String player;
        String winningPlayer = "";
        for (int i = 0; i < gameModel.getTotalPlayers(); i++) {
            PlayerModel currentPlayer = gameModel.getExactPlayer(i);
            score = currentPlayer.getRank() * 5 + currentPlayer.getCredits() + currentPlayer.getMoney();
            player = currentPlayer.getName();
            getView().showScore(player, score);
            if (score > highestScore) {
                highestScore = score;
                winningPlayer = player;
            }
        }
        getView().showWinner(winningPlayer, highestScore);
    }
}
