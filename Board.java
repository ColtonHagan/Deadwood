/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Contains/manages information about the deadwood GameBoard and all that is contained within
*/
class Board {
   private Room[] rooms;
   private int totalRooms;
   private int currentRooms;
   private CastingOffice office;
   
   public Board() {
      //includes rooms of office and trailer with 0 shot counters
      rooms = new Room[12];
      totalRooms = 10;
      currentRooms = totalRooms;
   }
   
   public void resetBoard(Scenes sceneLibray) {
      for(Room room : rooms) {
         room.resetShotCounters();
      }
      placeScenes(sceneLibray);
      currentRooms = totalRooms;
   }

   // Not used currently but will be used in gui
   public String[] adjacentRooms(Room currentRoom){
      return currentRoom.getAdjacentRooms();
   }

   public Room[] allRooms() {
      return rooms;
   }
   
   public void createBoard(parseData dataParser, Scenes sceneLibray) throws Exception {
      dataParser.parseBoard(rooms);
      placeScenes(sceneLibray);
   }
   
   public Room getTrailer() {
      return rooms[rooms.length-2];
   } 
   
   public void placeScenes(Scenes sceneLibray) {
      for(int i = 0; i < rooms.length - 2; i++) {
         rooms[i].setScene(sceneLibray.getRandomCard());
      }
   }
   
   public void removeRoom() {
      currentRooms--;
   }
   
   public int getCurrentRooms() {
      return currentRooms;
   }
}
