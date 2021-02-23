import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.Random;

class GameState {
   private int currentDay;
   private int totalDays;
   private int currentPlayer;
   private int totalPlayers;
   private PlayerModel[] players;
   private Scenes sceneLibray = new Scenes();
   private Board board = new Board();

   
   public GameState (int totalPlayers) throws Exception {
      this.totalPlayers = totalPlayers;
      this.currentPlayer = 0;
   }
   
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

   public Scenes getSceneLibrary() {
      return sceneLibray;
   }
   public Board getBoard() {
      return board;
   }
   public PlayerModel[] getPlayers() {
      return players;
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

   public void setBoard(Board board){
      this.board = board;
   }

}