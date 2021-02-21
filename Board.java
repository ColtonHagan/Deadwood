class Board {
   private Room[] rooms;
   private int totalRooms;
   private int currentRooms;
   private CastingOffice office;
   
   public Board() {
      //includes rooms of office and trailer with 0 shot counters
      rooms = new Room[12];
      totalRooms = 12;
      currentRooms = totalRooms;
   }
   
   public void resetBoard(Scenes sceneLibray){
      for(Room room : rooms) {
         room.getSceneCard().setUse(false);
      }
      placeScenes(sceneLibray);
      currentRooms = totalRooms;
   }
   
   public void endRoom(Room currentRoom) {
      currentRooms--;
      if(currentRooms == 1) {
         //endDay();
      }
   }
   
   public String[] adjacentRooms(Room currentRoom){
      return currentRoom.getAdjacentRooms();
   }

   public Room[] allRooms() {
      return rooms;
   }
   
   public void createBoard(parseData dataParser, Scenes sceneLibray) throws Exception {
      dataParser.parseBoard(rooms);
      placeScenes(sceneLibray);
      office = new CastingOffice(dataParser.parseOffice(office));
   }
   
   public Room getTrailer() {
      return rooms[totalRooms-2];
   } 
   
   public void placeScenes(Scenes sceneLibray) {
      for(int i = 0; i < rooms.length - 2; i++) {
         rooms[i].setScene(sceneLibray.getRandomCard());
      }
   }
}
