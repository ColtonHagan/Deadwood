/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Contains information about current gamestate
*/
class GameState {
    private int currentDay;
    private int totalDays;
    private int currentPlayer;
    private int totalPlayers;
    private PlayerModel[] players;
    private final Scenes sceneLibrary = new Scenes();
    private Board board = new Board();

    public GameState() {
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
        return sceneLibrary;
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

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public void setCurrentPlayerInt(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setAllPlayers(PlayerModel[] players) {
        this.players = players;
    }

}