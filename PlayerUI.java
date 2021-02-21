class PlayerUI {
   PlayerController controller;

   public void addListener(PlayerController controller) {
      this.controller = controller;
   }

   public void printPlayerDetails(String name, int money, int credits, int rank, int practiceChips) {
      System.out.println("Actor: " + name);
      System.out.println("Money: " + money);
      System.out.println("Credits: " + credits);
      System.out.println("Rank: " + rank);
      System.out.println("Practice Chips: " + practiceChips);
   }

   public void printRoom(String room){
      System.out.println("Currently in: " + room);
   }
   
   public void showUpgradeResults(boolean check, int rank, int oldRank) {
      if(check) {
         System.out.println("You have succeeded in upgrading!");
         System.out.println("You have upgraded from " + oldRank + " to " + rank);
      } else {
         System.out.println("You can not upgrade to rank " + rank);
      }
   }

   public void showActingResults(boolean actCheck, int moneyGained, int creditsGained) {
      if(actCheck) {
         System.out.println("You have succeeded in acting!");
         System.out.println("You have gained " + moneyGained + " Dollars and " + creditsGained + " Credits. \n");
      } else {
         System.out.println("You have failed in acting!");
      }
   }

   public void showRehearsalResults(boolean check, int practiceChips) {
      if(check){
         System.out.println("Rehearsal success!");
      } else {
         System.out.println("Rehearsal fail!");
         System.out.println("Error: Player is guaranteed to succeed in the next act action");
      }
      System.out.println("You currently have " + practiceChips + " Practice chips. \n");
   }

   public void showMoveResults(boolean check, String roomName){
      if(check) {
         System.out.println("Move successful!");
         System.out.println("You are now in the " + roomName + "\n");
      } else {
         System.out.println("Move failed!");
         System.out.println("Error: this room is not adjacent to your current room!");
         System.out.println("You are still in the " + roomName + "\n");
      }
   }

   public void showTakeRoleResults(boolean check, String roleName){
      if(check) {
         System.out.println("Role is now yours");
         System.out.println("You have the role: " + roleName + "\n");
      } else {
         System.out.println("Error: You were unable to take the role! \n");
      }
   }

   public void printUpgradeError(){
      System.out.println("Error: Cannot upgrade! You are not at the casting office! \n");
   }

   public void printMoveError(){
      System.out.println("Error: Cannot move! Either you currently have a role or you have already moved! \n");
   }

   public void printRehearseError(){
      System.out.println("Error: Cannot rehearse! You do not currently have a role! \n");
   }

   public void printActError(){
      System.out.println("Error: Cannot act! You do not currently have a role! \n");
   }

   public void printAddRoleError(){
      System.out.println("Error: Cannot take role! This could be due to many reasons: You currently have a role, the role is already taken," +
              " you do not have enough rank for the role, or the role does not exist! \n");
   }

   public void showCurrentRoom(){
      controller.getCurrentRoom();
   }
   
   public void showScore(String name, int score){
      System.out.println(name + " got " + score);
   }
   public void showWinner(String name, int score) {
      System.out.println("WINNER " + name + " with a score of " + score);
   }
   
   public void move(Room room) {
      controller.move(room);
   }

   public void takeRole(Role role) {
      controller.addRole(role);
   }

   public void act() {
      controller.act();
   }

   public void rehearse() {
      controller.rehearse();
   }

   public void upgradeRank(){
      controller.upgradeRank("money", 5);
   }

   public void playerDetails() {
      controller.updateView();
   }

}