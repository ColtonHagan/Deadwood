class PlayerController {
   private PlayerModel model;
   private PlayerUI view;

   public PlayerController(PlayerModel model, PlayerUI view) {
      this.model = model;
      this.view = view;
      this.view.addListener(this);
   }

   public void updateView(){
      view.printPlayerDetails(model.getName(), model.getMoney(), model.getCredits(), model.getRank(), model.getPracticeChips());
   }

   public void updateMoney(int money) {
      model.updateMoney(money);
   }

   public void updateCredits(int credits) {
      model.updateCredits(credits);
   }

   public void addRole(Role role) {
      if(model.getCurrentRole() == null && role.getUsedBy() == null){
         model.updateRole(role);
      } else {
         System.out.println("Error: Player already has role or role is taken");
      }
   }

   public void removeRole() {
      model.updateRole(null);
   }

   public void rehearse(){
      int budget = model.getCurrentRoom().getSceneCard().getBudget();
      if(model.getPracticeChips() + 1 == budget) {
         System.out.println()
      }
   }

   public void act(){

   }

}