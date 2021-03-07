/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Contains/manages information about a single room and all that is contained within
*/
public class Room {
    private final int totalShotCounters;
    private int currentShotCounters;
    private final Role[] extraRoles;
    private SceneCard sceneCard;
    private final String[] adjacentRooms;
    private final String name;
    private final int[] cords;
    private final int roomNumber;

    public Room(String name, int shotCounters, Role[] extraRoles, String[] adjacentRooms, int[] cords, int roomNumber) { //add room number and shot chounter cords
        this.name = name;
        totalShotCounters = shotCounters;
        currentShotCounters = shotCounters;
        this.extraRoles = extraRoles;
        this.adjacentRooms = adjacentRooms;
        this.cords = cords;
        this.roomNumber = roomNumber;
    }

    // Getters
    public int getRoomNumber() {
      return roomNumber;
    }
    public String[] getAdjacentRooms() {
        return adjacentRooms;
    }

    public String getName() {
        return name;
    }

    public SceneCard getSceneCard() {
        return sceneCard;
    }
    
    public int[] getCords() {
      return cords;
    }

    public int getShotCounters() {
        return currentShotCounters;
    }

    // Setters and Modifiers
    public void resetShotCounters() {
        currentShotCounters = totalShotCounters;
    }

    public void removeShotCounter() {
        currentShotCounters--;
    }

    public void setScene(SceneCard newScene) {
        sceneCard = newScene;
    }

    // Returns all roles on location that exist. Does not mean role can be taken - maybe no scenecard or already taken
    public Role[] availableRoles() {
        if (sceneCard != null) {
            Role[] sceneCardRoles = sceneCard.getRoles();
            Role[] allRoles = new Role[extraRoles.length + sceneCardRoles.length];
            int i = 0;
            for (; i < extraRoles.length; i++)
                allRoles[i] = extraRoles[i];
            for (int j = 0; j < sceneCardRoles.length; j++)
                allRoles[i + j] = sceneCardRoles[j];
            return allRoles;
        }
        return extraRoles;
    }

    // Checks if role exists on this Room - for extra roles
    public boolean hasRole(Role role) {
        for (Role r : extraRoles) {
            if (r.equals(role)) {
                return true;
            }
        }
        return false;
    }

    // Resets extras on room end so they can be used next day
    public void clearExtras() {
        for (Role r : extraRoles) {
            r.setUsedBy(null);
        }
    }
}
