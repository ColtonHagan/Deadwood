class GameState {
   private int days;
   private Player currentPlayersTurn;
   //private Board board; 
   private int totalDays;
   private Player[] players;
   private Scenes sceneLibray = new Scenes();
   private Board board = new Board();
   
   public GameState () throws Exception {
      setUpGame();
   }
   
   public void setUpGame() throws Exception {
      parseData dataParser = new parseData();
      sceneLibray.createScenes(dataParser);
      board.createBoard(dataParser, sceneLibray);
   }
   
   public void endGame(){
   
   }
   
   public void passTurn(){
   
   }
   
   public int getCurrentDay(){
      return 0;
   }
   
   public int getPlayerCount(){
      return 0;
   }
   
   public void setTotalDays(int days){
   
   }
}

