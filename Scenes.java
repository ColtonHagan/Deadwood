import java.util.Random;
public class Scenes {
   private SceneCard[] possibleScenes;
   public Scenes () {
      possibleScenes = new SceneCard[40];
   }
   public SceneCard getRandomCard() {
      Random rand = new Random();
      SceneCard randomCard = possibleScenes[rand.nextInt(40)];
      if(!randomCard.getUse()) {
         randomCard.setUse(true);
         randomCard.setFlip(false);
         return randomCard;
      }
      return getRandomCard();
   }

   public void resetLibray() {
      for(SceneCard card : possibleScenes)
         card.setUse(false);
   }

   public void createScenes(parseData dataParser) throws Exception {
      dataParser.parseScenes(possibleScenes);
   }
}