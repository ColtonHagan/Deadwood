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

   public void showActingResults(boolean actCheck, boolean onCard, int moneyGained, int creditsGained) {
      if(actCheck) {
         System.out.println("You have succeeded in acting!");
      } else {
         System.out.println("You have failed in acting!");
      }

      if(onCard) {
         System.out.println("You were working on the card.");
      } else {
         System.out.println("You were working off the card.");
      }
      System.out.println("You have gained " + moneyGained + " Dollars and " + creditsGained + " Credits.");
   }

   public void showRehearsalResults(boolean check, int practiceChips) {
      if(check){
         System.out.println("Rehearsal success!");
      } else {
         System.out.println("Rehearsal fail!");
         System.out.println("Error: Player is guaranteed to succeed in the next act action");
      }
      System.out.println("You currently have " + practiceChips + " Practice chips.");
   }

   public void showMoveResults(boolean check, String roomName){
      if(check) {
         System.out.println("Move successful!");
         System.out.println("You are now in the " + roomName);
      } else {
         System.out.println("Move failed!");
         System.out.println("Error: this room is not adjacent to your current room!");
         System.out.println("You are still in the " + roomName);
      }

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
      controller.upgradeRank();
   }

   public void playerDetails() {
      controller.updateView();
   }

}