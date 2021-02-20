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
   public void endRoom() {
   }
   //maybe this should be in gamestate since it has access to diffrent players
   private void bonusPayment() {
      Role[] allRoles = availableRoles();
      PlayerModel[] players = new PlayerModel[5]; // this should probably be an arraylist to prevent multiple passes
      boolean onCard;
      for(int i = 0; i < allRoles.length; i++) {
         Role currentRole = allRoles[i];
         if(currentRole.getUsedBy() != null) {
            //add player to list
            if(!currentRole.getExtra())
               onCard = true;
            players[i] = currentRole.getUsedBy();
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
