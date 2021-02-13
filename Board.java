class Board {
   private Room[] rooms;
   private int totalRooms;
   private int currentRooms;
   private CastingOffice office;
   
   public Board() {
      //includes rooms of office and trailer with 0 shot counters
      rooms = new Room[12];
      totalRooms = 12;
   }
   
   public void resetBoard(){
   
   }
   
   public void endDay(){
   
   }
   
   public String adjacentRooms(){
      return null;
   }
   
   public void createBoard(parseData dataParser, Scenes sceneLibray) throws Exception {
      dataParser.parseBoard(rooms, office);
      placeScenes(sceneLibray);
   }
   
   public void placeScenes(Scenes sceneLibray) {
      for(int i = 0; i < rooms.length; i++) {
         rooms[0].setScene(sceneLibray.getRandomCard());
      }
   }
}
