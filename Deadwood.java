
import java.util.*; 
public class Deadwood {
   public static void main(String args[]) {
       
       
   }
}

class Player{
   private String name; //Added 
   private int money;
   private int credits;
   private int rank;
   private int practiceChips;
   private Role role;
   
   public Player(String name, int money, int credits, int rank){
      this.name = name;
      this.money = money;
      this.credits = credits;
      this.rank = rank;
   }
   
   public void upgrade(){
      
   }
   
   public void move(){
   
   }
   
   public void takeRole(Role role){
   
   }
   
   public void act(){
   
   }
   
   public void rehearse(){
   
   }
   
   public int rollDice(){
   
   }
}

class GameState{
   private int days;
   private Player currentPlayersTurn;
   private Board boardState; 
   private int totalDays;
   private Player[] players;
   
   
   public void gameState(){
   
   }
   
   public void setUpGame(){
   
   }
   
   public void engGame(){
   
   }
   
   public void passTurn(){
   
   }
}

class GameStateUI{
   public GameStateInterface(){
   
   }
   
   public int pickPlayers(){
   
   }
   
   public void showScore(){
   
   }
}

class Board{
   private Room[] rooms;
   private int totalRooms;
   private int currentRooms;
   
   public Board(){
   
   }
   
   public void resetBoard(){
   
   }
   
   public void endDay(){
   
   }
   
   public Room[] adjustRooms(){
   
   }
}


