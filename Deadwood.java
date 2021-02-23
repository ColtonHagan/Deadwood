public class Deadwood {
   public static void main(String args[]) throws Exception {
      int totalPlayers = Integer.parseInt(args[0]);
      GameStateController control = new GameStateController(totalPlayers);

      control.setUpGame();
      Room[] allRooms = control.getGameModel().getBoard().allRooms();



      control.updateView();
      control.move(allRooms[9]);

      control.getCurrentRoom();

      Role holden = allRooms[4].getSceneCard().getRoles()[0];
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


      control.endRoom(allRooms[4]);
      control.endTurn();
      control.endGame();
   }
}