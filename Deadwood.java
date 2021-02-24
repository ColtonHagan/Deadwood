/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Playalbe deadwood game with console interface
*/
public class Deadwood {
   public static void main(String[] args) throws Exception {
      GameStateController control = new GameStateController(Integer.parseInt(args[0]));
      control.setUpGame();
   }
}