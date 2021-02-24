class GameState {
    private int currentDay;
    private int totalDays;
    private int currentPlayer;
    private final int totalPlayers;
    private PlayerModel[] players;
    private final Scenes sceneLibray = new Scenes();
    private Board board = new Board();

    public GameState(int totalPlayers) {
        this.totalPlayers = totalPlayers;
        this.currentDay = 1;
        this.currentPlayer = 0;
    }

    // Getters
    public int getCurrentDay() {
        return currentDay;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public int getCurrentPlayerInt() {
        return currentPlayer;
    }

    public PlayerModel getCurrentPlayer() {
        return players[currentPlayer];
    }

    public PlayerModel getExactPlayer(int i) {
        return players[i];
    }

    public int getTotalDays() {
        return totalDays;
    }

    public Scenes getSceneLibrary() {
        return sceneLibray;
    }

    public Board getBoard() {
        return board;
    }

    public PlayerModel[] getPlayers() {
        return players;
    }

    // Setters and Modifiers
    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public void setCurrentPlayerInt(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setAllPlayers(PlayerModel[] players) {
        this.players = players;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}