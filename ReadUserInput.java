import java.util.Scanner; 
class ReadUserInput {
   public static void main(String args[]) throws Exception {
      Board board;
   
   
      Scanner in = new Scanner(System.in); 
      int currentDays = 0;
      int totalDays = 4;
      while(currentDays <= totalDays) {
         String[] userInputArray = getInput().split(" ");
         if(userInputArray[0].equals("Upgrade")) {
            if(userInputArray.length == 3) {
               int targetRank = Integer.parseInt(userInputArray[2]);
               if(userInputArray[0].equals("Credits")) {
                  upgradeRankCredits(targetRank);
               } else if (userInputArray[1].equals("Dollars")) {
                  upgradeRankDollars(targetRank);
               } else {
                  System.out.println("Please enter Credits or Dollars to upgrade");
               }
            } else {
               System.out.println("Please enter Credits or Dollars and target rank to upgrade to");
            }
         } else if (userInputArray[0].equals("Move")) {
            String roomName = concatenateArray(userInputArray,1,userInputArray.length-1);
         } else if (userInputArray[0].equals("Work")) { //take role
            String roleName = concatenateArray(userInputArray,1,userInputArray.length-1);
         } else if (userInputArray[0].equals("Act")) {
            act();
         } else if (userInputArray[0].equals("Rehearsing")) {
            rehearse();
         } else if (userInputArray[0].equals("Active player?")) {
            //The active player is Jane Doe. She has $15, 3 credits and 10 fames. She is working Crusty Prospector, "Aww, peaches!"
         } else if (userInputArray[0].equals("Where")) {
            //in Train Station shooting Law and the Old West scene 20
         } else if (userInputArray[0].equals("Locations")) {
            //Display location of all players and indicate the active player
         } else if (userInputArray[0].equals("End")) {
            endTurn();
         } else {
            System.out.println("User input not recognize");
            System.out.println("Please input one of the following actions : Upgrade, Work, Act, Rehearsing, Active player?, Where, Locations, or End");
         }
         currentDays++;
      }
      endGame();
   }
   
   public static String getInput() {
      Scanner in = new Scanner(System.in); 
      return in.nextLine();
   }
   
   public static String concatenateArray(String[] array, int startIndex, int endIndex) {
      String combined = array[startIndex];
      for(int i = startIndex+1; i <= endIndex; i++) {
         combined += " " + array[i];
      }
      return combined;
   }
   
   public static Room roomNameToRoom(String roomName) {
      /*for(Room room : board.allRooms()) {
         if(room.getName().equals(roomName)) {
            return room;
         }
      }*/
      return null;
   }
   
   public static Role roleNameToRole() {
      return null;
   }
   
   
   public static void upgradeRankCredits(int targetUpgrade) {}
   public static void upgradeRankDollars(int targetUpgrade) {}
   public static void endGame(){};
   public static void rehearse(){};
   public static void act(){};
   public static void endTurn() {};
   public static void updateView() {};
}