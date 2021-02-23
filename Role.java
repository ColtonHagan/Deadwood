public class Role {
   private int rank;
   private PlayerModel usedBy;
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

   public int getRank() {
      return rank;
   }
   public boolean getExtra() {
      return isExtra;
   }
   public PlayerModel getUsedBy() {
      return usedBy;
   }
   public void setUsedBy(PlayerModel player) {
      usedBy = player;
   }
   public String getName(){
      return name;
   }
   public String getTagLine(){
      return tagLine;
   }
}
