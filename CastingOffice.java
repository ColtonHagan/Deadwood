public class CastingOffice {
   private int[][] possibleUpgrades;
   public CastingOffice(int[][] possibleUpgrades) {
      this.possibleUpgrades = possibleUpgrades;
   }
   public boolean rankPossible(int rank, int targetRank) {
      return rank < 6 && rank < targetRank;
   }
   
   public int cost(int tagetRank, String paymentType) {
      if(paymentType.equals("credits")) {
         return possibleUpgrades[tagetRank-2][2];
      }
      return possibleUpgrades[tagetRank-2][1];
   }
   
   public int[][] getPossibleUpgrades () {
      return possibleUpgrades;
   }
}