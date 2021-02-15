class GameStateController {
   private GameStateModel model; 
	private GameStateUI view; 
   
   public GameStateController (GameStateModel model, GameStateUI view) throws Exception {
      this.model = model;
      this.view = view;
   }
   
   public void setUpGame() throws Exception {
      int totalPlayers = -1; //will need to request this from user -- code later
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
      }
      
      PlayerModel[] players = new PlayerModel[totalPlayers];
      for(int i = 0; i < totalPlayers; i++) {
         String name = "find latter"; //will request player name here
         //PlayerModel[i] = new Player(name, money, credit, rank, model.getBoard().getTrailer()); wip
      }
      parseData dataParser = new parseData();
      model.getSceneLibray().createScenes(dataParser);
      model.getBoard().createBoard(dataParser, model.getSceneLibray());
   }
   
   public void endGame(){
      
   }
   
   public void passTurn(){
   
   }
}

