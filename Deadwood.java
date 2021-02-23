public class Deadwood {
   public static void main(String args[]) throws Exception {
      GameStateController control = new GameStateController(Integer.parseInt(args[0]));
      control.setUpGame();
   }
}
