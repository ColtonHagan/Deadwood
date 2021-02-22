import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.Random;
public class Room {
   private int totalShotCounters;
   private int currentShotCounters;
   private Role[] extraRoles;
   private SceneCard sceneCard;
   private String[] adjacentRooms;
   private String name;
   
   public Room (String name, int shotCounters, Role[] extraRoles, String[] adjacentRooms) {
      this.name = name;
      totalShotCounters = shotCounters;
      currentShotCounters = 0;
      this.extraRoles = extraRoles;
      this.adjacentRooms = adjacentRooms;
   }
   
   public void bonusPayment() {
      Role[] allRoles = availableRoles();
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
         Integer[] diceRolls = new Integer[sceneCard.getBudget()];
         for(int i = 0; i < diceRolls.length; i++) {
            diceRolls[i] = rand.nextInt(6) + 1;
         }
         Arrays.sort(diceRolls, Collections.reverseOrder());
         playersOnCard.sort(Comparator.comparing(PlayerModel::getRoleRank));
         Collections.reverse(playersOnCard);
         for(int i = 0; i < playersOnCard.size(); i++) {
            playersOnCard.get(i).updateMoney(playersOnCard.get(i).getMoney() + diceRolls[i]);
         }
         for(int i = 0; i < playersOffCard.size(); i++) {
            playersOffCard.get(i).updateMoney(playersOffCard.get(i).getCurrentRole().getRank() + playersOffCard.get(i).getMoney());
         }
      }
   } 
   public Role[] availableRoles() {
      Role[] sceneCardRoles = sceneCard.getRoles();
      Role[] allRoles = new Role[extraRoles.length + sceneCardRoles.length];
      int i = 0;
      for(; i < extraRoles.length; i++)
         allRoles[i] = extraRoles[i];
      for(int j = 0; j < sceneCardRoles.length; j++)
         allRoles[i+j] = sceneCardRoles[j]; 
      return allRoles;
   }

   public boolean hasRole(Role role) {
      for(Role r : extraRoles){
         if(r.equals(role)){
            return true;
         }
      }
      return false;
   }

   public void resetShotCounters() {
      currentShotCounters = totalShotCounters;
   }
   public int setCurrentShotCounters (int shotCounters) {
      return shotCounters;
   }
   
   public void removeShotCounter() {
      currentShotCounters--;
   }
   public void setScene(SceneCard newScene) {
      sceneCard = newScene;
   }
   public String[] getAdjacentRooms () {
      return adjacentRooms;
   }
   public String getName() {
      return name;
   }
   public SceneCard getSceneCard(){
      return sceneCard;
   }
   public void updateShotCounter(){
      currentShotCounters++;
   }
}
