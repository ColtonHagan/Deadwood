/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Contains and manages upgrade possibilities
*/
public class CastingOffice {
    private int[][] possibleUpgrades;

    public CastingOffice(int[][] possibleUpgrades) {
        this.possibleUpgrades = possibleUpgrades;
    }

    public int costCredits(int tagetRank) {
        return possibleUpgrades[tagetRank - 2][2];
    }

    public int costDollars(int tagetRank) {
        return possibleUpgrades[tagetRank - 2][1];
    }

}