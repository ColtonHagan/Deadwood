public class Role {
   private int rank;
   private Player usedBy;
   private boolean isExtra;
   
   public Role(int rank, boolean isExtra) {
   
   }
   
   public void changeRole() {
   
   }
   
   public int getRank() {
      return rank;
   } 
   public boolean getExtra() {
      return isExtra;
   }
   public Player getUsedBy() {
      return usedBy;
   }
   public void setPlayer(Player player) {
      usedBy = player;
   }
}