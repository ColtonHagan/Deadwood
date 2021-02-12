public class Role {
   private int rank;
   private Player usedBy;
   private boolean isExtra;
   private String name;
   private String tagLine;
   
   public Role(String name, String tagLine, int rank, boolean isExtra) {
      this.name = name;
      this.tagLine = tagLine;
      this.rank = rank;
      this.isExtra = isExtra;
      usedBy = null;
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