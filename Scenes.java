import java.util.Random;
public class Scenes {
   private SceneCard[] possibleScenes;
   public Scenes () {
      possibleScenes = new SceneCard[40];
   }
   //issue stop a card from being picked twice
   public SceneCard getRandomCard() {
      Random rand = new Random();
      SceneCard randomCard = possibleScenes[rand.nextInt(40)];
      if(!randomCard.getUse()) {
         randomCard.setUse(true);
         return randomCard;
      }
      return getRandomCard();
   }
   public void createScenes(parseData dataParser) throws Exception {
      dataParser.parseScenes(possibleScenes);
   }
}