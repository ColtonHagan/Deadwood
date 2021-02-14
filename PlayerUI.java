import java.util.Random;

class PlayerUI {
   PlayerController controller;

   public PlayerUI(){
   }

   public void printPlayerDetails(String name, int money, int credits, int rank, int practiceChips) {
      System.out.println("Actor: " + name);
      System.out.println("Money: " + money);
      System.out.println("Credits: " + credits);
      System.out.println("Rank: " + rank);
      System.out.println("Practice Chips: " + practiceChips);
   }

   public void showActingResults() {
   
   }

   public void showRehearsalResults() {
   
   }

   public void addListener(PlayerController controller){
      this.controller = controller;
   }

   public void move() {

   }

   public void takeRole(Role role) {

   }

   public void act() {

   }

   public void rehearse() {
      controller.rehearse();
   }

   public void upgradeRank(){

   }

   private int rollDice() {
      Random rand = new Random();
      return rand.nextInt(6) + 1;
   }
}