import java.util.*;

class GameStateController extends DeadwoodController{
    private GameState gameModel;
    private DeadwoodView view;

    public GameStateController(int totalPlayers) throws Exception {
        this.gameModel = new GameState(totalPlayers);
        view = new DeadwoodView();
        view.addListener(this);
    }

    // FOR TESTING, DELETE LATER
    public GameState getGameModel(){
        return gameModel;
    }

    public void setUpGame() throws Exception {
        int rank = 1;
        int money = 0;
        int credits = 0;
        int days = 4;

        if(gameModel.getTotalPlayers() == 2 || gameModel.getTotalPlayers() == 3) {
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
        for(int i = 0; i < gameModel.getTotalPlayers(); i++) {
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
        for(PlayerModel player : gameModel.getPlayers()) {
            if(player.getCurrentRoom() == currentRoom) {
                player.removeRole();
            }
        }
    }

    public void bonusPayment(Room currentRoom) {
        Role[] allRoles = currentRoom.availableRoles();
        ArrayList<PlayerModel> playersOnCard = new ArrayList<PlayerModel>();
        ArrayList<PlayerModel> playersOffCard = new ArrayList<PlayerModel>();

        for(Role currentRole : allRoles) {
            if(currentRole.getUsedBy() != null) {
                if(!currentRole.getExtra()) {
                    playersOnCard.add(currentRole.getUsedBy());
                } else {
                    playersOffCard.add(currentRole.getUsedBy());
                }
            }
        }

        if(playersOnCard.size() != 0) {
            Random rand = new Random();
            int bonus = 0;
            Integer[] diceRolls = new Integer[currentRoom.getSceneCard().getBudget()];
            for(int i = 0; i < diceRolls.length; i++) {
                diceRolls[i] = rand.nextInt(6) + 1;
            }
            Arrays.sort(diceRolls, Collections.reverseOrder());
            playersOnCard.sort(Comparator.comparing(PlayerModel::getRoleRank));
            Collections.reverse(playersOnCard);
            for(int i = 0; i < playersOnCard.size(); i++) {
                bonus = diceRolls[i];
                playersOnCard.get(i).updateMoney(playersOnCard.get(i).getMoney() + diceRolls[i]);
                view.showBonusPayment(playersOnCard.get(i).getName(), "on card", bonus);
            }
            for(int i = 0; i < playersOffCard.size(); i++) {
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
        while(currentDays <= totalDays) {
            String userInput = getInput();
            if(userInput.equals("upgrade")) {

            } else if (userInput.equals("move")) {

            } else if (userInput.equals("work")) { //take role

            } else if (userInput.equals("act")) {

            } else if (userInput.equals("rehearsing")) {

            } else if (userInput.equals("active player?")) {
                //The active player is Jane Doe. She has $15, 3 credits and 10 fames. She is working Crusty Prospector, "Aww, peaches!"
            } else if (userInput.equals("where")) {
                //in Train Station shooting Law and the Old West scene 20
            } else if (userInput.equals("locations")) {
                //Display location of all players and indicate the active player
            } else if (userInput.equals("end")) {

            }
        }
    }

    public static String getInput() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public void endTurn() {
        if(gameModel.getCurrentPlayerInt() + 1 < gameModel.getTotalPlayers()) {
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
        for(PlayerModel player : gameModel.getPlayers()) {
            player.updateCurrentRoom(gameModel.getBoard().getTrailer());
            player.removeRole();
            player.updatePracticeChips(0);
        }
    }

    public void endGame(){
        int score = 0;
        int highestScore = 0;
        String player = "";
        String winningPlayer = "";
        for(int i = 0; i  < gameModel.getTotalPlayers(); i++) {
            PlayerModel currentPlayer = gameModel.getExactPlayer(i);
            score = currentPlayer.getRank() * 5 + currentPlayer.getCredits() + currentPlayer.getMoney();
            player = currentPlayer.getName();
            view.showScore(player, score);
            if(score > highestScore) {
                highestScore = score;
                winningPlayer = player;
            }
        }
        view.showWinner(winningPlayer, highestScore);
    }
}