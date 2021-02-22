class DeadwoodView {
    DeadwoodController controller;

    public void addListener(DeadwoodController controller) {
        this.controller = controller;
    }

    public void printPlayerDetails(String name, int money, int credits, int rank, int practiceChips) {
        System.out.println("Actor: " + name);
        System.out.println("Money: " + money);
        System.out.println("Credits: " + credits);
        System.out.println("Rank: " + rank);
        System.out.println("Practice Chips: " + practiceChips + "\n");
    }

    public void printRoom(String room) {
        System.out.println("Currently in: " + room + "\n");
    }

    public void showUpgradeSuccess(int rank, int oldRank) {
        System.out.println("You have succeeded in upgrading!");
        System.out.println("You have upgraded from " + oldRank + " to " + rank + "\n");
    }

    public void showUpgradeFail(int rank) {
        System.out.println("You can not upgrade to rank " + rank);
        System.out.println("Either you need more money / credits, or your rank is higher than the chosen rank \n");
    }

    public void showActingSuccess(int moneyGained, int creditsGained) {
        System.out.println("You have succeeded in acting!");
        System.out.println("You have gained " + moneyGained + " Dollars and " + creditsGained + " Credits. \n");
    }

    public void showActingFail(int moneyGained, int creditsGained) {
        System.out.println("You have failed in acting!");
        System.out.println("You have gained " + moneyGained + " Dollars and " + creditsGained + " Credits. \n");
    }

    public void showRehearsalSuccess(int practiceChips) {
        System.out.println("Rehearsal success!");
        System.out.println("You currently have " + practiceChips + " Practice chips. \n");
    }

    public void showRehearsalFail(int practiceChips) {
        System.out.println("Rehearsal fail!");
        System.out.println("Player is guaranteed to succeed in the next act action");
        System.out.println("You currently have " + practiceChips + " Practice chips. \n");
    }

    public void showMoveSuccess(String roomName) {
        System.out.println("Move successful!");
        System.out.println("You are now in the " + roomName + "\n");
    }

    public void showMoveFail(String roomName) {
        System.out.println("Move failed!");
        System.out.println("This room is not adjacent to your current room or you are already in this room!");
        System.out.println("You are still in the " + roomName + "\n");
    }

    public void showTakeRoleSuccess(String roleName) {
        System.out.println("Role is now yours!");
        System.out.println("You have the role: " + roleName + "\n");
    }

    public void showTakeRoleFail() {
        System.out.println("Error: You were unable to take the role! \n");

    }

    public void printUpgradeError() {
        System.out.println("Error: Cannot upgrade! You are not at the casting office! \n");
    }

    public void printMoveError() {
        System.out.println("Error: Cannot move! Either you currently have a role or you have already moved! \n");
    }

    public void printRehearseError() {
        System.out.println("Error: Cannot rehearse! You do not currently have a role! \n");
    }

    public void printActError() {
        System.out.println("Error: Cannot act! You do not currently have a role! \n");
    }

    public void printAddRoleError() {
        System.out.println("Error: Cannot take role! This could be due to many reasons: You currently have a role, the role is already taken," +
                " you do not have enough rank for the role, or the role does not exist! \n");
    }

    public void printPlayerCount(int count) {
        System.out.println("Amount of players: " + count + "\n");
    }

    public void printUnsupportedPlayers() {
        System.out.println("This game is not playable with the given number of players!");
    }

    public void showCurrentRoom() {
        controller.getCurrentRoom();
    }
    
    public void showBonusPayment(String playerName, String roleType, int bonus) {
        System.out.println(playerName + " had a " + roleType + " role and earned a bonus of " + bonus);
    }
    
    public void noBonusPayment() {
        System.out.println("No player was on card, so no bonuses where given");
    }

    public void showEndTurn() {
        System.out.println("Turn Ended, next player's turn.");
    }

    public void showScore(String name, int score) {
        System.out.println(name + " got " + score);
    }

    public void showWinner(String name, int score) {
        System.out.println("WINNER " + name + " with a score of " + score);
    }

    public void move(Room room) {
        controller.move(room);
    }

    public void takeRole(Role role) {
        controller.addRole(role);
    }

    public void act() {
        controller.act();
    }

    public void rehearse() {
        controller.rehearse();
    }

    public void upgradeRank() {
        controller.upgradeRank("money", 5);
    }

    public void playerDetails() {
        controller.updateView();
    }

}