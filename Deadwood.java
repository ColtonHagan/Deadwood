public class Deadwood {
   public static void main(String args[]) throws Exception {
      GameState model = new GameState();

      model.setUpGame();
      PlayerModel[] test = model.getPlayers();
      PlayerUI view = new PlayerUI();
      PlayerController control = new PlayerController(test[0], view);

      Room[] allRooms = model.getBoard().allRooms();

      view.playerDetails();
      view.move(allRooms[4]);
      view.showCurrentRoom();

      Role holden = allRooms[4].getSceneCard().getRoles()[0];
      test[1].updateRank(6);
      view.takeRole(holden);
      view.rehearse();
      view.rehearse();
      view.rehearse();
      view.rehearse();
      view.rehearse();
      view.rehearse();
      view.rehearse();
      view.rehearse();
      view.act();
      control.updateMoney(2000);
      view.upgradeRank();
   }
}


