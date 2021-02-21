public class Deadwood {
   public static void main(String args[]) throws Exception {
      int totalPlayers = Integer.parseInt(args[0]);
      GameState model = new GameState(totalPlayers);
      System.out.println("Amount of players: " + model.getPlayerCount() + "\n");

      model.setUpGame();
      PlayerModel[] test = model.getPlayers();
      PlayerUI view = new PlayerUI();
      PlayerController control = new PlayerController(test[0], view);

      Room[] allRooms = model.getBoard().allRooms();

      view.playerDetails();
      view.move(allRooms[4]);
      view.showCurrentRoom();

      Role holden = allRooms[4].getSceneCard().getRoles()[0];
      test[0].updateRank(6);
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


