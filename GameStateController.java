import java.util.*;

class GameStateController extends DeadwoodController {
    private final GameState gameModel;
    private final DeadwoodView view;

    public GameStateController(int totalPlayers) throws Exception {
        this.gameModel = new GameState(totalPlayers);
        view = new DeadwoodView();
        view.addListener(this);
    }

    // FOR TESTING, DELETE LATER
    public GameState getGameModel() {
        return gameModel;
    }

    public void setUpGame() throws Exception {
        int rank = 1;
        int money = 0;
        int credits = 0;
        int days = 4;

        if (gameModel.getTotalPlayers() == 2 || gameModel.getTotalPlayers() == 3) {
            days = 3;
        } else if (gameModel.getTotalPlayers() == 5) {
            credits = 2;
        } else if (gameModel.getTotalPlayers() == 6) {
            credits = 4;
        } else if (gameModel.getTotalPlayers() == 7 || gameModel.getTotalPlayers() == 8) {
            rank = 2;
        } else if (gameModel.getTotalPlayers() < 2 || gameModel.getTotalPlayers() > 8) {
            view.printUnsupportedPlayers();
            return;
        }

        view.printPlayerCount(gameModel.getTotalPlayers());
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
    }

    public void endRoom(Room currentRoom) {
        bonusPayment(currentRoom);
        currentRoom.setScene(null);
        gameModel.getBoard().removeRoom();
        for (PlayerModel player : gameModel.getPlayers()) {
            if (player.getCurrentRoom() == currentRoom) {
                player.removeRole();
            }
        }
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
                view.showBonusPayment(playersOnCard.get(i).getName(), "on card", bonus);
            }
            for (int i = 0; i < playersOffCard.size(); i++) {
                bonus = playersOffCard.get(i).getCurrentRole().getRank();
                playersOffCard.get(i).updateMoney(playersOffCard.get(i).getCurrentRole().getRank() + playersOffCard.get(i).getMoney());
                view.showBonusPayment(playersOffCard.get(i).getName(), "extra", bonus);
            }
        } else {
            view.noBonusPayment();
        }
    }

    public void playGame() {
        int currentDays = 0;
        int totalDays = 4;
        PlayerModel player = gameModel.getCurrentPlayer();
        getView().inputWelcome();

        while (currentDays <= totalDays) {
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
                    String roomName = concatenateArray(userInputArray, 1, userInputArray.length - 1);
                    Room room = roomNameToRoom(roomName);
                    if(room != null) {
                        move(room);
                    } else {
                        getView().inputMoveInvalidRoom();
                    }
                    break;

                case "Work":  //take role
                    String roleName = concatenateArray(userInputArray, 1, userInputArray.length - 1);
                    Role role = roleNameToRole(roleName);
                    if(role != null) {
                        addRole(role);
                    } else {
                        getView().inputWorkInvalidRole();
                    }
                    break;

                case "Act":
                    act();
                    if(gameModel.getCurrentPlayer().getCurrentRoom().getShotCounters() == 0) {
                        endRoom(gameModel.getCurrentPlayer().getCurrentRoom());
                    }
                    break;

                case "Rehearsing":
                    rehearse();
                    break;

                case "Active player?":
                    getView().printPlayerDetails(player.getName(), player.getMoney(), player.getCredits(), player.getRank(),
                            player.getCurrentRole().getName(), player.getCurrentRole().getTagLine());
                    break;

                case "Where":
                    if(player.getCurrentRole() == null) {
                        getView().playerLocation(player.getCurrentRoom().getName());
                    } else if (player.getCurrentRole().getExtra()) {
                        getView().playerLocationWithExtraRole(player.getCurrentRoom().getName(), player.getCurrentRole().getName());
                    } else {
                        getView().playerLocationWithOnCardRole(player.getCurrentRoom().getName(), player.getCurrentRole().getName(),
                                player.getCurrentRoom().getSceneCard().getName());
                    }
                    break;

                case "Locations":
                    for (PlayerModel currentPlayer : gameModel.getPlayers()) {
                        if(currentPlayer == player) {
                            getView().printPlayerDetails(player.getName(), player.getMoney(), player.getCredits(), player.getRank(),
                                    player.getCurrentRole().getName(), player.getCurrentRole().getTagLine());
                        } else {
                            getView().printInactivePlayerDetails(currentPlayer.getName(), currentPlayer.getMoney(), currentPlayer.getCredits(), currentPlayer.getRank(),
                                    currentPlayer.getCurrentRole().getName(), currentPlayer.getCurrentRole().getTagLine());
                        }
                    }
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
        for(Room room : gameModel.getBoard().allRooms()) {
            if(room.getName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

    public Role roleNameToRole(String roleName) {
        for(Room room : gameModel.getBoard().allRooms()) {
            for(Role role : room.availableRoles()) {
                if(role.getName().equals(roleName)) {
                    return role;
                }
            }
        }
        return null;
    }

    public void endTurn() {
        if (gameModel.getCurrentPlayerInt() + 1 < gameModel.getTotalPlayers()) {
            clearMoved();
            gameModel.setCurrentPlayerInt(gameModel.getCurrentPlayerInt() + 1);
            updateModel(gameModel.getCurrentPlayer());

            view.showEndTurn();
        } else {
            endDay();
        }
    }

    public void endDay() {
        gameModel.getBoard().resetBoard(gameModel.getSceneLibrary());
        for (PlayerModel player : gameModel.getPlayers()) {
            player.updateCurrentRoom(gameModel.getBoard().getTrailer());
            player.removeRole();
            player.updatePracticeChips(0);
        }
    }

    public void endGame() {
        int score;
        int highestScore = 0;
        String player;
        String winningPlayer = "";
        for (int i = 0; i < gameModel.getTotalPlayers(); i++) {
            PlayerModel currentPlayer = gameModel.getExactPlayer(i);
            score = currentPlayer.getRank() * 5 + currentPlayer.getCredits() + currentPlayer.getMoney();
            player = currentPlayer.getName();
            view.showScore(player, score);
            if (score > highestScore) {
                highestScore = score;
                winningPlayer = player;
            }
        }
        view.showWinner(winningPlayer, highestScore);
    }
}
