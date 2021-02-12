public class SceneCard {
   private int budget;
   private String name;
   private String description;
   private Role[] roles;
   private boolean flippedCard;
   
   public SceneCard (String name, String description, int budget, Role[] roles) {
      this.name = name;
      this.description = description;
      this.budget = budget;
      this.roles = roles;
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
   public String getName () {
      return name;
   }
   public boolean flippedCard () {
      return flippedCard;
   }
}