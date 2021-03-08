/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Contains and manages upgrade possibilities
*/
public class CastingOffice {
    private final int[][] possibleUpgrades;

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