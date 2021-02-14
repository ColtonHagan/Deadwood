public class Room {
   private int totalShotCounters;
   private int currentShotCounters;
   private Role[] extraRoles;
   private SceneCard sceneCard;
   private String[] adjacentRooms;
   private String name;
   
   public Room (String name, int shotCounters, Role[] extraRoles) {
      this.name = name;
      totalShotCounters = shotCounters;
      currentShotCounters = 0;
      this.extraRoles = extraRoles;
   }
   public void endRoom() {
   
   }
   private void bonusPayment() {
   
   } 
   public Role[] availableExtraRoles() {
      return null;
   }
   public int getCurrentShotCounters () {
      return currentShotCounters;
   }
   public void setScene(SceneCard newScene) {
      sceneCard = newScene;
   }
   public void createRoom() {
   
   }
   public void parseRoom() {
   
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
}
