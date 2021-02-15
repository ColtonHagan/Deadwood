import java.util.Random;

class PlayerController {
   private PlayerModel model;
   private PlayerUI view;

   public PlayerController(PlayerModel model, PlayerUI view) {
      this.model = model;
      this.view = view;
      view.addListener(this);
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

   public void upgradeRank(){
      //Handle in CastingOffice
   }

   public void move(Room room){
      boolean checkChange = false;
      for (String s : model.getCurrentRoom().getAdjacentRooms()) {
         if(room.equals(s)){
            model.updateCurrentRoom(room);
            checkChange = true;
            break;
         }
      }

      if(checkChange) {
         view.showMoveResults(true, model.getCurrentRoom().getName());
      } else {
         view.showMoveResults(false, model.getCurrentRoom().getName());
      }
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
         view.showRehearsalResults(false, model.getPracticeChips());
      } else {
         model.updatePracticeChips(model.getPracticeChips() + 1);
         view.showRehearsalResults(true, model.getPracticeChips());
      }
   }

   public void clearRehearse(){
      model.updatePracticeChips(0);
   }

   private int rollDice() {
      Random rand = new Random();
      return rand.nextInt(6) + 1;
   }

   public void act(){
      int budget = model.getCurrentRoom().getSceneCard().getBudget();
      if((rollDice() + model.getPracticeChips()) >= budget){
         //If Actor succeeds in acting:
         model.getCurrentRoom().updateShotCounter();


         if(model.getCurrentRole().getExtra()){
            //Off card:
            model.updateMoney(model.getMoney() + 1);
            model.updateCredits(model.getCredits() + 1);
            view.showActingResults(false, true, 1, 1);
         } else {
            //On card:
            model.updateCredits(model.getCredits() + 2);
            view.showActingResults(false, true, 0, 2);
         }
      } else {
         //If actor fails in acting:
         if(model.getCurrentRole().getExtra()){
            //Off card:
            model.updateMoney(model.getMoney() + 1);
            view.showActingResults(false, false, 1, 0);
         } else {
            //On card
            view.showActingResults(false, true, 0, 0);
         }
      }
   }
}