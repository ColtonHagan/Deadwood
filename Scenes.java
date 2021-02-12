import java.util.Random;
public class Scenes {
   private SceneCard[] possibleScenes;
   public Scenes () {
      possibleScenes = new SceneCard[40];
   }
   //issue stop a card from being picked twice
   public SceneCard getRandomCard() {
      Random rand = new Random();
      int randNum = rand.nextInt(40);
      return possibleScenes[randNum];
   }
   public void createScenes(parseData dataParser) throws Exception {
      dataParser.parseScenes(possibleScenes);
   }
}