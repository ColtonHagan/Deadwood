/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Contains information about a single roles
*/
public class Role {
    private final int rank;
    private PlayerModel usedBy;
    private final boolean isExtra;
    private final String name;
    private final String tagLine;
    private final int[] cords;

    public Role(String name, String tagLine, int rank, boolean isExtra, int[] cords) {
        this.name = name;
        this.tagLine = tagLine;
        this.rank = rank;
        this.isExtra = isExtra;
        this.cords = cords;
        usedBy = null;
    }

    // Getters
    public int getRank() {
        return rank;
    }

    public boolean getExtra() {
        return isExtra;
    }

    public PlayerModel getUsedBy() {
        return usedBy;
    }
    
    public int[] getCords() {
      return cords;
    }

    public String getName() {
        return name;
    }

    // Setter
    public void setUsedBy(PlayerModel player) {
        usedBy = player;
    }
}
