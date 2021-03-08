/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Contains information about a single sceneCard
*/
public class SceneCard {
    private final int budget;
    private final int sceneNumber;
    private boolean inUse = false; //if the card has been played on the board or not
    private boolean flipped = false;
    private final String name;
    private final Role[] roles;
    private final String image;

    public SceneCard(String name, int budget, int sceneNumber, Role[] roles, String image) {
        this.name = name;
        this.budget = budget;
        this.roles = roles;
        this.sceneNumber = sceneNumber;
        this.image = image;
    }

    // Getters
    public int getBudget() {
        return budget;
    }

    public Role[] getRoles() {
        return roles;
    }

    public String getImage() {
       return image;
    }

    public boolean getUse() {
        return inUse;
    }

    public boolean getFlip() {
        return flipped;
    }

    // Setters and modifiers
    public void setUse(boolean use) {
        inUse = use;
    }

    public void setFlip(boolean flip) {
        flipped = flip;
    }

}