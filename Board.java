/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Contains/manages information about the deadwood GameBoard and all that is contained within
*/
class Board {
    private final int totalRooms;
    private int currentRooms;
    private final Room[] rooms;

    public Board() {
        //includes rooms of office and trailer with 0 shot counters
        rooms = new Room[12];
        totalRooms = 10;
        currentRooms = totalRooms;
    }

    // Getters
    public int getCurrentRooms() {
        return currentRooms;
    }

    public Room getTrailer() {
        return rooms[rooms.length - 2];
    }

    public Room[] allRooms() {
        return rooms;
    }

    // Setters
    public void removeRoom() {
        currentRooms--;
    }

    // Used on day end to refill shot counters
    public void resetBoard(Scenes sceneLibray) {
        for (Room room : rooms) {
            room.resetShotCounters();
        }
        placeScenes(sceneLibray);
        currentRooms = totalRooms;
    }

    // Creates the baord from parsed data
    public void createBoard(ParseData dataParser, Scenes sceneLibray) throws Exception {
        dataParser.parseBoard(rooms);
        placeScenes(sceneLibray);
    }

    // Sets the new scenes on day end
    public void placeScenes(Scenes sceneLibray) {
        for (int i = 0; i < rooms.length - 2; i++) {
            rooms[i].setScene(sceneLibray.getRandomCard());
        }
    }


}
