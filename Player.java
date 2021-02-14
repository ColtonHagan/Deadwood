class Player{
   private String name;
   private int money;
   private int credits;
   private int rank;
   private int practiceChips;
   private Role role;
   
   public Player(String name, int money, int credits, int rank){
      this.name = name;
      this.money = money;
      this.credits = credits;
      this.rank = rank;
   }
   
   public void upgrade() {
      
   }
   
   public void move() {
   
   }
   
   public void takeRole(Role role){
   
   }
   
   public void act(){
   
   }
   
   public void rehearse(){
   
   }
   
   public int rollDice(){
      return 0;
   }
   
   public int getMoney(){
      return 0;
   }
   
   public int getCredits(){
      return 0;
   }
   
   public int getRank(){
      return 0;
   }
   
   public int getPracticeChips(){
      return 0;
   }
   
   public Room getCurrentRoom(){
      return null;
   }
   
   public String getName(){
      return null;
   }
   
   public void updateMoney(int money){
   
   }
   
   public void updateCredits(int credits){
   
   }
   
   public void upgradeRank(){
   
   }
   
}