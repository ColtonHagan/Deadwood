public class SceneCard {
   private int budget;
   private Role[] roles;
   private boolean flippedCard;
   
   public SceneCard (int budget, Role[] roles) {
   
   }
   
   public boolean playerOn() {
      return true;
   }
   public Role[] availableRoles() {
      return null;
   }
   public int getBudget () {
      return budget;
   }
   public Role[] getRoles () {
      return roles;
   }
   public boolean flippedCard () {
      return flippedCard;
   }
}