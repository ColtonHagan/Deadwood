public class Room {
   private int totalShotCounters;
   private int currentShotCounters;
   private Role[] extraRoles;
   private SceneCard sceneCard;
   private String[] adjacentRooms;
   private String name;
   
   public Room (String name, int shotCounters, Role[] extraRoles, SceneCard sceneCard) {
      this.name = name;
      totalShotCounters = shotCounters;
      currentShotCounters = 0;
      this.extraRoles = extraRoles;
      this.sceneCard = sceneCard;
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
   public void removeScene() {
   
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
}
