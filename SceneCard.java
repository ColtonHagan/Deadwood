/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Contains information about a single sceneCard
*/
public class SceneCard {
   private int budget;
   private String name;
   private String description;
   private Role[] roles;
   private boolean inUse = false; //if the card has been played on the board or not
   private boolean flipped = false;
   private int sceneNumber;
   
   public SceneCard (String name, String description, int budget, int sceneNumber, Role[] roles) {
      this.name = name;
      this.description = description;
      this.budget = budget;
      this.roles = roles;
      this.sceneNumber = sceneNumber;
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
   public int getSceneNumber() {
      return sceneNumber;
   }
   public void setUse(boolean use) {
      inUse = use;
   }
   public boolean getUse() {
      return inUse;
   }
   public boolean getFlip() {
      return flipped;
   }
   public void setFlip(boolean flip) {
      flipped = flip;
   }
}