public class SceneCard {
   private int budget;
   private String name;
   private String description;
   private Role[] roles;
   private boolean inUse; //if the card has been played on the board or not
   
   public SceneCard (String name, String description, int budget, Role[] roles) {
      this.name = name;
      this.description = description;
      this.budget = budget;
      this.roles = roles;
      inUse = false;
   }
   
   public boolean playerOn() {
      return true;
   }

   public boolean hasRole(Role role) {
      for(Role r : roles){
         if(r.equals(role)){
            return true;
         }
      }
      return false;
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
   public void setUse(boolean use) {
      inUse = use;
   }
   public boolean getUse() {
      return inUse;
   }
}