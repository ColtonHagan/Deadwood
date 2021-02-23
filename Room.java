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
   public Role[] availableRoles() {
      if (sceneCard != null) {
         Role[] sceneCardRoles = sceneCard.getRoles();
         Role[] allRoles = new Role[extraRoles.length + sceneCardRoles.length];
         int i = 0;
         for(; i < extraRoles.length; i++)
            allRoles[i] = extraRoles[i];
         for(int j = 0; j < sceneCardRoles.length; j++)
            allRoles[i+j] = sceneCardRoles[j]; 
         return allRoles;
      }
      return extraRoles;
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
   public int getShotCounters() {
      return currentShotCounters;
   }
}
