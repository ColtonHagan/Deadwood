public class CastingOffice {
    private int[][] possibleUpgrades;

    public CastingOffice(int[][] possibleUpgrades) {
        this.possibleUpgrades = possibleUpgrades;
    }

    // Checks if the targetRank is legally possible from the possibleUpgrades array
    public int costCredits(int tagetRank) {
        return possibleUpgrades[tagetRank - 2][2];
    }

    // Same as above but with Dollars
    public int costDollars(int tagetRank) {
        return possibleUpgrades[tagetRank - 2][1];
    }

}