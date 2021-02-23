import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.Random;

class GameState {
   private int currentDay;
   private int totalDays;
   private int currentPlayer;
   private PlayerModel[] players;
   private Scenes sceneLibray = new Scenes();
   private Board board = new Board();
   private DeadwoodController playerController;
   private int totalPlayers;
   
   public GameState (int totalPlayers) throws Exception {
      this.totalPlayers = totalPlayers;
      this.currentPlayer = 0;
   }
   
   public int getCurrentDay() {
      return currentDay;
   }
   
   public int getPlayerCount() {
      return totalPlayers;
   }
   public Scenes getSceneLibray() {
      return sceneLibray;
   }
   public Board getBoard() {
      return board;
   }
   public PlayerModel[] getPlayers() {
      return players;
   }
   
   public void setUpGame() throws Exception {
      int rank = 1;
      int money = 0;
      int credits = 0;
      int days = 4;

      if(totalPlayers == 2 || totalPlayers == 3) {
         days = 3;
      } else if (totalPlayers == 5) {
         credits = 2;
      } else if (totalPlayers == 6) {
         credits = 4;
      } else if (totalPlayers == 7 || totalPlayers == 8) {
         rank = 2;
      } else if (totalPlayers < 2 || totalPlayers > 8) {
         //playerView.printUnsupportedPlayers();
         return;
      }
      //playerView.printPlayerCount(totalPlayers);

      parseData dataParser = new parseData();
      sceneLibray.createScenes(dataParser);
      board.createBoard(dataParser, sceneLibray);

      String[] namesList = {"Blue", "Cyan", "Green", "Orange", "Pink", "Red", "Violet", "Yellow"};

      PlayerModel[] players = new PlayerModel[totalPlayers];
      for(int i = 0; i < totalPlayers; i++) {
         String name = namesList[i];
         players[i] = new PlayerModel(name, money, credits, rank, board.getTrailer());
      }
      
      this.players = players;

      playerController = new DeadwoodController(players[0]);
      playerController.createOffice(dataParser);
   }
   
   public void endRoom(Room currentRoom) {
      bonusPayment(currentRoom);
      currentRoom.setScene(null);
      board.removeRoom();
      for(PlayerModel player : players) {
         if(player.getCurrentRoom() == currentRoom) {
            player.removeRole();
         }
      }
   }
   
   public void bonusPayment(Room currentRoom) {
      Role[] allRoles = currentRoom.availableRoles();
      ArrayList<PlayerModel> playersOnCard = new ArrayList<PlayerModel>();
      ArrayList<PlayerModel> playersOffCard = new ArrayList<PlayerModel>(); 
       
      for(Role currentRole : allRoles) {
         if(currentRole.getUsedBy() != null) {
            if(!currentRole.getExtra()) {
               playersOnCard.add(currentRole.getUsedBy());
            } else {
               playersOffCard.add(currentRole.getUsedBy());
            }
         }
      }
      
      if(playersOnCard.size() != 0) {
         Random rand = new Random();
         int bonus = 0;
         Integer[] diceRolls = new Integer[currentRoom.getSceneCard().getBudget()];
         for(int i = 0; i < diceRolls.length; i++) {
            diceRolls[i] = rand.nextInt(6) + 1;
         }
         Arrays.sort(diceRolls, Collections.reverseOrder());
         playersOnCard.sort(Comparator.comparing(PlayerModel::getRoleRank));
         Collections.reverse(playersOnCard);
         for(int i = 0; i < playersOnCard.size(); i++) {
            bonus = diceRolls[i];
            playersOnCard.get(i).updateMoney(playersOnCard.get(i).getMoney() + diceRolls[i]);
            //playerView.showBonusPayment(playersOnCard.get(i).getName(), "on card", bonus);
         }
         for(int i = 0; i < playersOffCard.size(); i++) {
            bonus = playersOffCard.get(i).getCurrentRole().getRank();
            playersOffCard.get(i).updateMoney(playersOffCard.get(i).getCurrentRole().getRank() + playersOffCard.get(i).getMoney());
            //playerView.showBonusPayment(playersOffCard.get(i).getName(), "extra", bonus);
         }
      } else {
         //playerView.noBonusPayment();
      }
   }

   public void playGame() {

   }

   public void endTurn() {
      if(currentPlayer + 1 < totalPlayers) {
         playerController.clearMoved();
         currentPlayer++;
         playerController.updateModel(players[currentPlayer]);

         //playerView.showEndTurn();
      } else {
         endDay();
      }
   }

   public void endDay() {
      board.resetBoard(sceneLibray);
      for(PlayerModel player : players) {
         player.updateCurrentRoom(board.getTrailer());
         player.removeRole();
         player.updatePracticeChips(0);
      }
   }
   
   public void endGame(){
      int score = 0;
      int highestScore = 0;
      String player = "";
      String winningPlayer = "";
      for(int i = 0; i  < players.length; i++) {
         PlayerModel currentPlayer = players[i];
         score = currentPlayer.getRank() * 5 + currentPlayer.getCredits() + currentPlayer.getMoney();
         player = currentPlayer.getName();
         //playerView.showScore(player, score);
         if(score > highestScore) {
            highestScore = score;
            winningPlayer = player;
         }
      }
      //playerView.showWinner(winningPlayer, highestScore);
   }

}