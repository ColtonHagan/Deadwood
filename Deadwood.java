public class Deadwood {
   public static void main(String args[]) throws Exception {
      int totalPlayers = Integer.parseInt(args[0]);
      GameState game = new GameState(totalPlayers);

      game.setUpGame();

      PlayerModel[] test = game.getPlayers();
      DeadwoodController control = new DeadwoodController(test[0]);

      Room[] allRooms = game.getBoard().allRooms();

      control.updateView();
      control.move(allRooms[4]);
      control.getCurrentRoom();

      Role holden = allRooms[4].getSceneCard().getRoles()[0];
      test[0].updateRank(6);
      control.addRole(holden);
      control.rehearse();
      control.rehearse();
      control.rehearse();
      control.rehearse();
      control.rehearse();
      control.rehearse();
      control.rehearse();
      control.rehearse();
      control.act();
      control.updateMoney(2000);
      control.upgradeRankCredits(3);
      control.upgradeRankDollars(5);

      game.endTurn();
      game.endGame();
   }
}