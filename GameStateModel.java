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
      return currentDay;
   }
   
   public int getPlayerCount(){
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

}