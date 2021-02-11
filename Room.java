public class Room {
   private int totalShotCounters;
   private int currentShotCounters;
   private Role[] extraRoles;
   private SceneCard sceneCard;
   private Room adjacentRooms;
   private boolean flippedCard;
   
   public Room (int totalShotCounters, Role[] extraRoles, SceneCard sceneCard) {
   
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
   private void removeScene() {
   
   }
   private Room getAdjacentRooms () {
      return adjacentRooms;
   }
   private boolean getFlippedCard () {
      return flippedCard;
   }
}