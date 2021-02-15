class PlayerUI {
   PlayerController controller;

   public PlayerUI(){
   }

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

   public void move() {

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

}