class Board {
   private Room[] rooms;
   private int totalRooms;
   private int currentRooms;
   
   public Board() {
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
   
   public void createBoard(parseData dataParser,Scenes sceneLibray) throws Exception {
      dataParser.parseBoard(rooms, sceneLibray);
   }
}
