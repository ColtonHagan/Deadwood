/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Prints statements about the game
*/
class DeadwoodView {
    ConsoleListener consoleListener;

    public void addListener(ConsoleListener consoleListener) {
        this.consoleListener = consoleListener;
    }

    // Logical prints for acting, rehearsing, upgrading, taking role, and moving
    public void showUpgradeSuccess(int rank, int oldRank) {
        consoleListener.printToLog("You have succeeded in upgrading!");
        consoleListener.printToLog("You have upgraded from " + oldRank + " to " + rank + "\n");
    }

    public void showUpgradeFail(int rank) {
        consoleListener.printToLog("You can not upgrade to rank " + rank);
        consoleListener.printToLog("Either you need more money / credits, or your rank is higher than the chosen rank \n");
    }

    public void showActingSuccess(int moneyGained, int creditsGained) {
        consoleListener.printToLog("You have succeeded in acting!");
        consoleListener.printToLog("You have gained " + moneyGained + " Dollars and " + creditsGained + " Credits. \n");
    }

    public void showActingFail(int moneyGained, int creditsGained) {
        consoleListener.printToLog("You have failed in acting!");
        consoleListener.printToLog("You have gained " + moneyGained + " Dollars and " + creditsGained + " Credits. \n");
    }

    public void showRehearsalSuccess(int practiceChips) {
        consoleListener.printToLog("Rehearsal success!");
        consoleListener.printToLog("You currently have " + practiceChips + " Practice chips. \n");
    }

    public void showRehearsalFail(int practiceChips) {
        consoleListener.printToLog("Rehearsal fail!");
        consoleListener.printToLog("Guaranteed to succeed in the next act action");
        consoleListener.printToLog("You currently have " + practiceChips + " Practice chips. \n");
    }

    public void showMoveSuccess(String roomName) {
        consoleListener.printToLog("Move successful!");
        consoleListener.printToLog("You are now in the " + roomName + "\n");
    }

    public void showMoveFail(String roomName) {
        consoleListener.printToLog("Move failed!");
        consoleListener.printToLog("This room is not adjacent to your current room or you are already in this room!");
        consoleListener.printToLog("You are still in the " + roomName + "\n");
    }

    public void showTakeRoleSuccess(String roleName) {
        consoleListener.printToLog("Role is now yours!");
        consoleListener.printToLog("You have the role: " + roleName + "\n");
    }

    // Errors for when player does something illegal that System catches
    public void printUpgradeError() {
        consoleListener.printToLog("Error: Cannot upgrade! \nYou are not at the casting office! \n");
    }

    public void printMoveError() {
        consoleListener.printToLog("Error: Cannot move! \nYou have already moved! \n");
    }

    public void printActError() {
        consoleListener.printToLog("Error: Cannot act! Either you have already worked (Act, Rehearse, or just took role) or you do not currently have a role! \n");
    }

    public void printAddRoleError() {
        consoleListener.printToLog("Error: Cannot take role, there are no available roles \nOr rank is too low \n");
    }

    public void printAlreadyHaveRole() {
        consoleListener.printToLog("Error: Cannot take role, there are no available roles \n");
    }

    // General player information
    public void printPlayerCount(int count) {
        consoleListener.printToLog("Amount of players: " + count + "\n");
    }

    public void printUnsupportedPlayers() {
        consoleListener.printToLog("This game is not playable with the given number of players!");
    }

    // General game info prints
    public void printSceneEnd() {
        consoleListener.printToLog("The Scene has ended");
    }

    public void printTie() {
        consoleListener.printToLog("\nThere was a tie!");
        consoleListener.printToLog("The winners are:");
    }

    public void promptRestart(){
        consoleListener.printToLog("Would you like to restart the game?");
        System.out.print("Enter 'Yes' to restart, anything else to end the game: ");
    }

    public void showWinnerTie(String name, int score) {
        consoleListener.printToLog(name + " with a score of " + score);
    }

    public void showBonusPayment(String playerName, String roleType, int bonus) {
        consoleListener.printToLog(playerName + " had a " + roleType + " role and earned a bonus of " + bonus);
    }

    public void noBonusPayment() {
        consoleListener.printToLog("No player was on card, so no bonuses were given");
    }

    public void showEndTurn(String name) {
        consoleListener.printToLog("\nTurn Ended, it is now " + name + "'s turn");
    }

    public void showEndDay() {
        consoleListener.printToLog("The day has ended!");
    }

    public void showEndGame() {
        consoleListener.printToLog("The game has ended!");
        consoleListener.printToLog("Final Scores:");
    }

    public void showScore(String name, int score) {
        consoleListener.printToLog(name + " got " + score);
    }

    public void showWinner(String name, int score) {
        consoleListener.printToLog("WINNER " + name + " with a score of " + score);
    }
}
