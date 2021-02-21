class PlayerModel {
   private String name;
   private int money;
   private int credits;
   private int rank;
   private int practiceChips;
   private Room currentRoom;
   private Role role;
   private CastingOffice office;
   private boolean moved;

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
   
   public CastingOffice getOffice() {
      return office;
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

   public boolean getMoved() {
      return moved;
   }
   
   public int getRoleRank() {
      return role.getRank();
   }

   public void updateMoney(int money) {
      this.money = money;
   }

   public void updateCredits(int credits) {
      this.credits = credits;
   }

   public void updateRank(int rank) {
      this.rank = rank;
   }

   public void updateRole(Role role) {
      this.role = role;
   }

   public void updatePracticeChips(int practiceChips) {
      this.practiceChips = practiceChips;
   }

   public void updateCurrentRoom(Room currentRoom) {
      this.currentRoom = currentRoom;
   }
   
   public void createOffice(int[][] upgrades) {
      office = new CastingOffice(upgrades);
   }
   public void updateMoved(boolean moved){
      this.moved = moved;
   }
   
}