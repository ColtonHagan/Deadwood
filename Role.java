public class Role {
    private final int rank;
    private PlayerModel usedBy;
    private final boolean isExtra;
    private final String name;
    private final String tagLine;

    public Role(String name, String tagLine, int rank, boolean isExtra) {
        this.name = name;
        this.tagLine = tagLine;
        this.rank = rank;
        this.isExtra = isExtra;
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

    public String getName() {
        return name;
    }

    public String getTagLine() {
        return tagLine;
    }

    // Setter
    public void setUsedBy(PlayerModel player) {
        usedBy = player;
    }
}
