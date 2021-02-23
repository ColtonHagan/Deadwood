public class CastingOffice {
    private int[][] possibleUpgrades;

    public CastingOffice(int[][] possibleUpgrades) {
        this.possibleUpgrades = possibleUpgrades;
    }

    public boolean rankPossible(int rank, int targetRank) {
        return rank < 6 && rank < targetRank && targetRank <= 6;
    }

    public int costCredits(int tagetRank) {
        return possibleUpgrades[tagetRank - 2][2];
    }

    public int costDollars(int tagetRank) {
        return possibleUpgrades[tagetRank - 2][1];
    }

    public int[][] getPossibleUpgrades() {
        return possibleUpgrades;
    }
}