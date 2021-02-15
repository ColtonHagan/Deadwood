class GameStateModel {
   private int currentDay;
   private PlayerModel currentPlayersTurn;
   private int totalDays;
   private PlayerModel[] players;
   private Scenes sceneLibray = new Scenes();
   private Board board = new Board();
   public GameStateModel () throws Exception {
   }
   public int getCurrentDay() {
      return 0;
   }
   
   public int getPlayerCount(){
      return 0;
   }
   public Scenes getSceneLibray() {
      return sceneLibray;
   }
   public Board getBoard() {
      return board;
   }
   public PlayerModel[] getPlayers() {
      return players;
   }
   public void setTotalDays(int days) {
      totalDays = days;
   }

}