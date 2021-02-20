class GameState {
   private int currentDay;
   private PlayerModel currentPlayersTurn;
   private int totalDays;
   private PlayerModel[] players;
   private Scenes sceneLibray = new Scenes();
   private Board board = new Board();
   private PlayerUI playerView;
   
   public GameState () throws Exception {
      setUpGame();
   }
   
   public int getCurrentDay() {
      return currentDay;
   }
   
   public int getPlayerCount() {
      return players.length;
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
      int totalPlayers = 2; //will need to request this from user -- code later
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
      } else if (totalPlayers < 2 || totalPlayers > 2) {
         System.out.println("This game is not playable with given number of players");
         return;
      }     
      PlayerModel[] players = new PlayerModel[totalPlayers];
      for(int i = 0; i < totalPlayers; i++) {
         String name = "find latter"; //will request player name here
         players[i] = new PlayerModel(name, money, credits, rank, board.getTrailer());
      }
      parseData dataParser = new parseData();
      sceneLibray.createScenes(dataParser);
      board.createBoard(dataParser, sceneLibray);
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