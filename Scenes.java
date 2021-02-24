import java.util.Random;

public class Scenes {
    private final SceneCard[] possibleScenes;

    public Scenes() {
        possibleScenes = new SceneCard[40];
    }

    // Grabs a random Scenecard from possible cards
    public SceneCard getRandomCard() {
        Random rand = new Random();
        SceneCard randomCard = possibleScenes[rand.nextInt(40)];
        if (!randomCard.getUse()) {
            randomCard.setUse(true);
            randomCard.setFlip(false);
            return randomCard;
        }
        return getRandomCard();
    }

    // Resets all scenecards back to false
    public void resetLibray() {
        for (SceneCard card : possibleScenes)
            card.setUse(false);
    }

    // Creates all Scenecards from parsed data from XML
    public void createScenes(parseData dataParser) throws Exception {
        dataParser.parseScenes(possibleScenes);
    }
}