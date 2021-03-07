/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Contains information about a single player
*/
class PlayerModel {
    private final String name;
    private int money;
    private int credits;
    private int rank;
    private int practiceChips;
    private boolean moved;
    private boolean worked;
    private boolean hasRole;
    private Room currentRoom;
    private Role role;
    private CastingOffice office;


    public PlayerModel(String name, int money, int credits, int rank, Room currentRoom) {
        this.name = name;
        this.money = money;
        this.credits = credits;
        this.rank = rank;
        this.role = null;
        this.currentRoom = currentRoom;
        this.hasRole = false;
        this.moved = false;
        this.worked = false;
    }

    // Getters
    public int getMoney() {
        return money;
    }

    public int getCredits() {
        return credits;
    }

    public CastingOffice getOffice() {
        return office;
    }

    public int getRank() {
        return rank;
    }

    public int getPracticeChips() {
        return practiceChips;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Role getCurrentRole() {
        return role;
    }

    public boolean getHasRole() {
        return hasRole;
    }

    public String getName() {
        return name;
    }

    public boolean getMoved() {
        return moved;
    }

    public boolean getWorked() {
        return !worked;
    }

    public int getRoleRank() {
        return role.getRank();
    }

    public CastingOffice getCastingOffice() {
        return office;
    }

    // Setters and Modifiers
    public void updateMoney(int money) {
        this.money = money;
    }

    public void updateCredits(int credits) {
        this.credits = credits;
    }

    public void updateRank(int rank) {
        this.rank = rank;
    }

    public void updateMoved(boolean moved) {
        this.moved = moved;
    }

    public void updateWorked(boolean worked) {
        this.worked = worked;
    }

    public void updateHasRole(boolean hasRole) {
        this.hasRole = hasRole;
    }

    public void updatePracticeChips(int practiceChips) {
        this.practiceChips = practiceChips;
    }

    public void updateCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void takeRole(Role role) {
        this.role = role;
        this.hasRole = true;
    }

    public void removeRole() {
        this.role = null;
        this.hasRole = false;
    }

    public void createOffice(int[][] upgrades) {
        office = new CastingOffice(upgrades);
    }
}