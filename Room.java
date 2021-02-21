import java.util.ArrayList;
import java.util.Arrays;
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
   //maybe this should be in gamestate since it has access to diffrent players
   private void bonusPayment() {
      Role[] allRoles = availableRoles();
      ArrayList<PlayerModel> players = new ArrayList<PlayerModel>();  
      boolean onCard = false;
      for(Role currentRole : allRoles) {
         if(currentRole.getUsedBy() != null) {
            //add player to list
            if(!currentRole.getExtra())
               onCard = true;
            players.add(currentRole.getUsedBy());
         }
      }
      if(onCard) {
         Random rand = new Random();
         int[] diceRolls = new int[players.size()];
         for(int diceRoll : diceRolls) {
            diceRoll = rand.nextInt(6) + 1;
         }
         Arrays.sort(diceRolls);
         //Need to sort players
         for(int i = 0; i < players.size(); i++) {
            PlayerModel player = players.get(i);
            if(player.getCurrentRole().getExtra()) {
            
            } else {
               player.updateMoney(player.getCurrentRole().getRank() + player.getMoney());
            }
         }
      }
   } 
   public Role[] availableRoles() {
      Role[] sceneCardRoles = sceneCard.getRoles();
      Role[] allRoles = new Role[extraRoles.length + sceneCardRoles.length];
      int i = 0;
      for(; i < extraRoles.length; i++)
         allRoles[i] = extraRoles[i];
      for(; i < extraRoles.length; i++)
         allRoles[i] = sceneCardRoles[i];
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

   public int getCurrentShotCounters () {
      return currentShotCounters;
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
