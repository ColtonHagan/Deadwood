class GameState {
   private int currentDay;
   private PlayerModel currentPlayersTurn;
   private int totalDays;
   private PlayerModel[] players;
   private Scenes sceneLibray = new Scenes();
   private Board board = new Board();
   private PlayerUI playerView;
   private PlayerController playerController;
   private int totalPlayers;
   
   public GameState (int totalPlayers) throws Exception {
      this.totalPlayers = totalPlayers;
   }
   
   public int getCurrentDay() {
      return currentDay;
   }
   
   public int getPlayerCount() {
      return totalPlayers;
   }
   public Scenes getSceneLibray() {
      return sceneLibray;
   }
   public Board getBoard() {
      return board;
   }
   public void setPlayers(PlayerModel[] players) {
      this.players = players;
   }
   public PlayerModel[] getPlayers() {
      return players;
   }
   public void setTotalDays(int days) {
      totalDays = days;
   }
   
   public void setUpGame() throws Exception {
      int rank = 1;
      int money = 0;
      int credits = 0;
      int days = 4;
      if(totalPlayers == 2 || totalPlayers == 3) {
         days = 3;
      } else if (totalPlayers == 5) {
         credits = 2;
      } else if (totalPlayers == 6) {
         credits = 4;
      } else if (totalPlayers == 7 || totalPlayers == 8) {
         rank = 2;
      } else if (totalPlayers < 2 || totalPlayers > 8) {
         System.out.println("This game is not playable with the given number of players");
         return;
      }

      parseData dataParser = new parseData();
      sceneLibray.createScenes(dataParser);
      board.createBoard(dataParser, sceneLibray);

      String[] namesList = {"Blue", "Cyan", "Green", "Orange", "Pink", "Red", "Violet", "Yellow"};

      PlayerModel[] players = new PlayerModel[totalPlayers];
      for(int i = 0; i < totalPlayers; i++) {
         String name = namesList[i];
         players[i] = new PlayerModel(name, money, credits, rank, board.getTrailer());
      }
      
      this.players = players;
      
      playerView = new PlayerUI();
      playerController = new PlayerController(players[0], playerView);
      playerController.createOffice(dataParser);
   }
   
   public void endGame(){
      int score = 0;
      int highestScore = 0;
      String player = "";
      String winningPlayer = "";
      for(int i = 0; i  < players.length; i++) {
         PlayerModel currentPlayer = players[i];
         score = currentPlayer.getRank() * 5 + currentPlayer.getCredits() + currentPlayer.getMoney();
         player = currentPlayer.getName();
         playerView.showScore(player, score);
         if(score > highestScore) {
            highestScore = score;
            winningPlayer = player;
         }
      }
      playerView.showWinner(winningPlayer, highestScore);
   }

}