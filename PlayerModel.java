class PlayerModel {
   private String name;
   private int money;
   private int credits;
   private int rank;
   private int practiceChips;
   private Room currentRoom;
   private Role role;
   
   public PlayerModel(String name, int money, int credits, int rank, Room currentRoom) {
      this.name = name;
      this.money = money;
      this.credits = credits;
      this.rank = rank;
      this.role = null;
      this.currentRoom = currentRoom;
   }
   
   public int getMoney() {
      return money;
   }
   
   public int getCredits() {
      return credits;
   }
   
   public int getRank() {
      return rank;
   }
   
   public int getPracticeChips() {
      return practiceChips;
   }
   
   public Room getCurrentRoom() {
      return currentRoom;
   }

   public Role getCurrentRole() {
      return role;
   }
   
   public String getName() {
      return name;
   }

   public void updateMoney(int money) {
      this.money += money;
   }

   public void updateCredits(int credits) {
      this.credits += credits;
   }

   public void updateRole(Role role) {
      this.role = role;
   }

   public void updatePracticeChips(int practiceChips) {
      this.practiceChips = practiceChips;
   }
}