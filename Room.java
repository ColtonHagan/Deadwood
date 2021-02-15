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
   public String[] getAdjacentRooms () {
      return adjacentRooms;
   }
   public String getName() {
      return name;
   }
<<<<<<< HEAD
=======
   public SceneCard getSceneCard(){
      return sceneCard;
   }
   public void updateShotCounter(){
      currentShotCounters++;
   }
>>>>>>> 19580e601be63dd97288cbf3678c53367ce3b81c
}
