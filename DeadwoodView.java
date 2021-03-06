/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Prints statements about the game
*/
class DeadwoodView {
    DeadwoodController controller;

    public void addListener(DeadwoodController controller) {
        this.controller = controller;
    }

    // General info on players here
    public void printPlayerDetails(String name, int money, int credits, int rank, String roleName, String tagLine) {
        System.out.print("The active player is " + name + ". They have $" + money + ", " + credits + " credits and has rank " + rank);
        System.out.println(" they are currently working " + roleName + " \"" + tagLine + "\"\n");
    }
    
    public void printPlayerDetailsNoRole(String name, int money, int credits, int rank) {
        System.out.println("The active player is " + name + ". They have $" + money + ", " + credits + " credits and has rank " + rank + "\n");
    }
    
    public void playerLocationWithExtraRole(String roomName, String roleName) {
        System.out.println("In " + roomName + " shooting " + roleName + "\n");
    }
    
    public void inactivePlayerLocationWithRole(String playerName, String roomName, String roleName) {
        System.out.println(playerName + " is in " + roomName + " shooting " + roleName + "\n");
    }
    
    public void activePlayerLocationWithRole(String playerName, String roomName, String roleName) {
       System.out.println("Active Player " + playerName + " is in " + roomName + " shooting " + roleName + "\n");
    }
    
    public void playerLocationWithOnCardRole(String roomName, String roleName, String sceneNumber) {
        System.out.println("In " + roomName + " shooting " + roleName + "scene " + sceneNumber + "\n");
    }
    
    public void playerLocation(String roomName) {
        System.out.println("In " + roomName + "\n");
    }
    
    public void activePlayerLocation(String playerName, String roomName) {
       System.out.println("Active Player " + playerName + " is in " + roomName + "\n");
    }
    
    public void inactivePlayerLocation(String playerName, String roomName) {
        System.out.println(playerName + " is in " + roomName + "\n");
    }

    // Logical prints for acting, rehearsing, upgrading, taking role, and moving
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

    // Errors for when player does something illegal that System catches
    public void printUpgradeError() {
        System.out.println("Error: Cannot upgrade! You are not at the casting office! \n");
    }

    public void printMoveError() {
        System.out.println("Error: Cannot move! Either you currently have a role or you have already moved! \n");
    }

    public void printRehearseError() {
        System.out.println("Error: Cannot rehearse! Either you have already worked (Act, Rehearse, or just took role) or you do not currently have a role! \n");
    }

    public void printActError() {
        System.out.println("Error: Cannot act! Either you have already worked (Act, Rehearse, or just took role) or you do not currently have a role! \n");
    }

    public void printAddRoleError() {
        System.out.println("Error: Cannot take role! This could be due to many reasons: You currently have a role, the role is already taken," +
                " you do not have enough rank for the role, there is no scene card on location, or the role exists in a different location! \n");
    }

    // General player information
    public void printPlayerCount(int count) {
        System.out.println("Amount of players: " + count + "\n");
    }

    public void printUnsupportedPlayers() {
        System.out.println("This game is not playable with the given number of players!");
    }

    // General game info prints
    public void printSceneEnd() {
        System.out.println("The Scene has ended");
    }

    public void printTie() {
        System.out.println("\nThere was a tie!");
        System.out.println("The winners are:");
    }

    public void promptRestart(){
        System.out.println("Would you like to restart the game?");
        System.out.print("Enter 'Yes' to restart, anything else to end the game: ");
    }

    public void showWinnerTie(String name, int score) {
        System.out.println(name + " with a score of " + score);
    }

    public void showBonusPayment(String playerName, String roleType, int bonus) {
        System.out.println(playerName + " had a " + roleType + " role and earned a bonus of " + bonus);
    }

    public void noBonusPayment() {
        System.out.println("No player was on card, so no bonuses where given");
    }

    public void showEndTurn(String name) {
        System.out.println("Turn Ended, it is now " + name + "'s turn");
    }

    public void showEndDay() {
        System.out.println("The day has ended!");
    }

    public void showEndGame() {
        System.out.println("The game has ended!");
        System.out.println("Final Scores:");

    }

    public void showScore(String name, int score) {
        System.out.println(name + " got " + score);
    }

    public void showWinner(String name, int score) {
        System.out.println("WINNER " + name + " with a score of " + score);
    }

    // General input
    public void inputWelcome() {
        System.out.println("Welcome to Deadwood!");
    }

    public void inputChoose() {
        System.out.println("Please input one of the following actions: Move, Roles, Upgrade, Work, Act, Rehearsing, Active player?, Where, Locations, or End");
    }

    public void inputUpgradeMissingInfo() {
        System.out.println("Please enter payment method (Credits or Dollars) followed by desired rank to upgrade");
        System.out.println("Example: \"Upgrade Credits 5\" \n");
    }

    public void inputMoveMissingInfo() {
        System.out.println("Please enter location to move to");
        System.out.println("Example: \"Move Main Street\" \n");
    }

    public void inputWorkMissingInfo() {
        System.out.println("Please enter role to take");
        System.out.println("Example: \"Work Chef\" \n");
    }

    public void inputMoveInvalidRoom() {
        System.out.println("There is no room with that name \n");
    }

    public void inputWorkInvalidRole() {
        System.out.println("There is no active role with that name \n");
    }

    public void inputError() {
        System.out.println("User input not recognize \n");
    }

    // This will print all roles given and their name, rank requirement, and on or off card status
    public void printRoles(Role[] roles) {
        if(roles.length > 0) {
            for (Role r : roles) {
                System.out.println("Role \"" + r.getName() + "\" requiring a rank of " + r.getRank() + ". Off card: " + r.getExtra());
            }
            System.out.println();
        } else {
            System.out.println("There are no roles here! \n");
        }
    }
}
