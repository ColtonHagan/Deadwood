/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Controls how/when game progresses
*/

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

class GameStateController extends DeadwoodController {
    private final GameState gameModel;
    private final BoardView boardView;

    public GameStateController() {
        this.boardView = new BoardView();
        getView().addListener(boardView.consoleListener);
        boardView.addListener(this);
        boardView.setVisible(true);
        this.gameModel = new GameState();
    }

    // Sets up buttons to start the game
    public void preSetUp() {
        for (int i = 0; i < 7; i++) {
            boardView.bPlayerCount[i].addMouseListener(new boardMouseListener());
        }
    }

    public void setUpGame() throws Exception {
        // Remove Buttons for choosing player count
        boardView.hideButtonsPlayerCount();

        int rank = 1;
        int money = 0; // Testing, both should be 0 here
        int credits = 0;
        int days = 4;
        // Picking totalPlayers
        boardView.setTotalPlayers(gameModel.getTotalPlayers());
        if (gameModel.getTotalPlayers() == 2 || gameModel.getTotalPlayers() == 3) {
            days = 3;
        } else if (gameModel.getTotalPlayers() == 5) {
            credits = 2;
        } else if (gameModel.getTotalPlayers() == 6) {
            credits = 4;
        } else if (gameModel.getTotalPlayers() == 7 || gameModel.getTotalPlayers() == 8) {
            rank = 2;
        } else if (gameModel.getTotalPlayers() < 2 || gameModel.getTotalPlayers() > 8) {
            getView().printUnsupportedPlayers();
            return;
        }

        getView().printPlayerCount(gameModel.getTotalPlayers());
        gameModel.setTotalDays(days);

        // Setting up board from XML
        ParseData dataParser = new ParseData();
        gameModel.getSceneLibrary().createScenes(dataParser);
        gameModel.getBoard().createBoard(dataParser, gameModel.getSceneLibrary(), boardView);

        // Creating players with inputted names, variable stats based on above totalPlayers
        PlayerModel[] players = new PlayerModel[gameModel.getTotalPlayers()];
        for (int i = 0; i < gameModel.getTotalPlayers(); i++) {
            String name = boardView.createPlayers(i);
            players[i] = new PlayerModel(name, money, credits, rank, gameModel.getBoard().getTrailer());
            boardView.displayMove(i, gameModel.getBoard().getTrailer().getCords());
        }

        gameModel.setAllPlayers(players);
        updateModel(gameModel.getCurrentPlayer());
        createOffice(dataParser);


        // Button Setup
        boardView.createButtons();
        boardView.createPlayerDisplay();
        boardView.updatePlayerDisplay(gameModel.getCurrentPlayer().getName(), gameModel.getCurrentPlayer().getMoney(), gameModel.getCurrentPlayer().getCredits(), 0, 0, 1, gameModel.getTotalDays());
        boardView.bAct.addMouseListener(new boardMouseListener());
        boardView.bRehearse.addMouseListener(new boardMouseListener());
        boardView.bMove.addMouseListener(new boardMouseListener());
        boardView.bWork.addMouseListener(new boardMouseListener());
        boardView.bUpgrade.addMouseListener(new boardMouseListener());
        boardView.bCancel.addMouseListener(new boardMouseListener());
        boardView.bEndTurn.addMouseListener(new boardMouseListener());
        boardView.bTakeRole[0].addMouseListener(new boardMouseListener());
        boardView.bTakeRole[1].addMouseListener(new boardMouseListener());
    }

    // Used when shot counter hits zero, for SceneCard ending
    public void endRoom(Room currentRoom) {
        getView().printSceneEnd();
        bonusPayment(currentRoom);
        boardView.removeScene(currentRoom.getRoomNumber());
        currentRoom.setScene(null);
        gameModel.getBoard().removeRoom();
        gameModel.getCurrentPlayer().getCurrentRoom().clearExtras();

        // Resetting all players on location so they can take a new role.
        for (int i = 0; i < gameModel.getPlayers().length; i++) {
            PlayerModel player = gameModel.getPlayers()[i];
            if (player.getCurrentRoom() == currentRoom) {
                boardView.displayMove(i, gameModel.getCurrentPlayer().getCurrentRoom().getCords());
                player.removeRole();
                player.updateHasRole(false);
                player.updatePracticeChips(0);
            }
        }
        gameModel.getCurrentPlayer().updateMoved(true);
    }

    public void bonusPayment(Room currentRoom) {
        Role[] allRoles = currentRoom.availableRoles();
        ArrayList<PlayerModel> playersOnCard = new ArrayList<>();
        ArrayList<PlayerModel> playersOffCard = new ArrayList<>();

        // Defines which players are on and off card
        for (Role currentRole : allRoles) {
            if (currentRole.getUsedBy() != null) {
                if (!currentRole.getExtra()) {
                    playersOnCard.add(currentRole.getUsedBy());
                } else {
                    playersOffCard.add(currentRole.getUsedBy());
                }
            }
        }

        // Ensures there is a player on card, if not no bonus payment
        if (playersOnCard.size() != 0) {
            Random rand = new Random();
            int bonus;
            Integer[] diceRolls = new Integer[currentRoom.getSceneCard().getBudget()];
            for (int i = 0; i < diceRolls.length; i++) {
                diceRolls[i] = rand.nextInt(6) + 1;
            }
            Arrays.sort(diceRolls, Collections.reverseOrder());
            playersOnCard.sort(Comparator.comparing(PlayerModel::getRoleRank));
            Collections.reverse(playersOnCard);
            for (int i = 0; i < playersOnCard.size(); i++) {
                bonus = diceRolls[i];
                playersOnCard.get(i).updateMoney(playersOnCard.get(i).getMoney() + diceRolls[i]);
                getView().showBonusPayment(playersOnCard.get(i).getName(), "on card", bonus);
            }
            for (PlayerModel playerModel : playersOffCard) {
                bonus = playerModel.getCurrentRole().getRank();
                playerModel.updateMoney(playerModel.getCurrentRole().getRank() + playerModel.getMoney());
                getView().showBonusPayment(playerModel.getName(), "extra", bonus);
            }
        } else {
            getView().noBonusPayment();
        }
    }

    // For finding the specific Room from its roomName (given from user input in console)
    public Room roomNameToRoom(String roomName) {
        for (Room room : gameModel.getBoard().allRooms()) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

    // For finding the specific Role from its roleName (given from user input in console)
    public Role roleNameToRole(String roleName) {
        for (Room room : gameModel.getBoard().allRooms()) {
            for (Role role : room.availableRoles()) {
                if (role.getName().equals(roleName)) {
                    return role;
                }
            }
        }
        return null;
    }

    public void endTurn() throws Exception {
        clearMoved();
        clearWorked();
        // Sets the player of the next turn to the next player
        if (gameModel.getCurrentPlayerInt() + 1 < gameModel.getTotalPlayers()) {
            gameModel.setCurrentPlayerInt(gameModel.getCurrentPlayerInt() + 1);
        } else {
            gameModel.setCurrentPlayerInt(0);
        }
        updateModel(gameModel.getCurrentPlayer());
        getView().showEndTurn(gameModel.getCurrentPlayer().getName());

        // Resets buttons based on player status
        boardView.hideAll();
        if (gameModel.getCurrentPlayer().getHasRole()) {
            boardView.showButtonsHasRole();
        } else {
            boardView.showButtonsDefault();
        }
        boardView.updatePlayerDisplay(gameModel.getCurrentPlayer().getName(), gameModel.getCurrentPlayer().getMoney(), gameModel.getCurrentPlayer().getCredits(), gameModel.getCurrentPlayer().getPracticeChips(), gameModel.getCurrentPlayerInt(), gameModel.getCurrentDay(), gameModel.getTotalDays());
        // Checks if this is the last Scenecard on board, ends day if true;
        if (gameModel.getBoard().getCurrentRooms() == 1) {
            endDay();
        }
    }

    public void endDay() throws Exception {
        // Resets all players for next day and moves them to trailers
        for (int i = 0; i < gameModel.getPlayers().length; i++) {
            PlayerModel player = gameModel.getPlayers()[i];
            player.updateCurrentRoom(gameModel.getBoard().getTrailer());
            boardView.displayMove(i, gameModel.getBoard().getTrailer().getCords());
            player.removeRole();
            player.updatePracticeChips(0);
            clearWorked();
            clearMoved();
        }

        // Check if not last day
        if (gameModel.getCurrentDay() < gameModel.getTotalDays()) {
            gameModel.getBoard().resetBoard(gameModel.getSceneLibrary(), boardView);
        }

        getView().showEndDay();
        gameModel.setCurrentDay(gameModel.getCurrentDay() + 1);

        // Bring buttons back to default
        boardView.hideAll();
        boardView.showButtonsDefault();
        
        if (gameModel.getCurrentDay() > gameModel.getTotalDays()) {
            endGame();
        } else {
            boardView.updatePlayerDisplay(gameModel.getCurrentPlayer().getName(), gameModel.getCurrentPlayer().getMoney(), gameModel.getCurrentPlayer().getCredits(), gameModel.getCurrentPlayer().getPracticeChips(), gameModel.getCurrentPlayerInt(), gameModel.getCurrentDay(), gameModel.getTotalDays());
        }
    }

    public void endGame() {
        getView().showEndGame();
        boardView.endGame();
        int tieAmount = 1;
        int score;
        int highestScore = 0;
        String player;
        String[] winningPlayers = new String[gameModel.getTotalPlayers()];

        // Logic to find the winning player, can also be a tie
        for (int i = 0; i < gameModel.getTotalPlayers(); i++) {
            PlayerModel currentPlayer = gameModel.getExactPlayer(i);
            score = currentPlayer.getRank() * 5 + currentPlayer.getCredits() + currentPlayer.getMoney();
            player = currentPlayer.getName();
            getView().showScore(player, score);
            if (score > highestScore) {
                highestScore = score;
                winningPlayers[0] = player;
                tieAmount = 1;
            } else if (score == highestScore) {
                winningPlayers[tieAmount] = player;
                tieAmount += 1;
            }
        }

        // Checks if there was a tie, print multiple winners for tie and single for no tie
        if (tieAmount > 1) {
            getView().printTie();
            for (String s : winningPlayers) {
                getView().showWinnerTie(s, highestScore);
            }
        } else {
            getView().showWinner(winningPlayers[0], highestScore);
        }
    }

    // This class holds all the button press logic and also all the playing game logic
    class boardMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            // Button for Cancelling
            if (e.getSource() == boardView.bCancel) {
                boardView.hidePayments();
                boardView.hideRoles();
                boardView.hideRooms();
                boardView.hideAll();
                boardView.showButtonsDefault();

                //Button for Ending turn
            } else if (e.getSource() == boardView.bEndTurn) {
                try {
                    endTurn();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                // Button for acting
            } else if (e.getSource() == boardView.bAct) {
                if (getSystem().checkCanAct()) {
                    // If an act succeeds, also remove a shot counter
                    if (act()) {
                        boardView.removeShotCounter(gameModel.getCurrentPlayer().getCurrentRoom().getRoomNumber());
                    }

                    // Checks if all shot counters are done
                    if (gameModel.getCurrentPlayer().getCurrentRoom().getShotCounters() == 0) {
                        endRoom(gameModel.getCurrentPlayer().getCurrentRoom());
                    }
                    try {
                        endTurn();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    getView().printActError();
                }

                // Button for Rehearsing
            } else if (e.getSource() == boardView.bRehearse) {
                if (getSystem().checkCanRehearse()) {
                    rehearse();
                    try {
                        endTurn();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    getView().showRehearsalFail(gameModel.getCurrentPlayer().getPracticeChips());
                }

                // Button for Moving
            } else if (e.getSource() == boardView.bMove) {
                if (getSystem().checkCanMove()) {
                    String[] adjacentRooms = gameModel.getCurrentPlayer().getCurrentRoom().getAdjacentRooms();
                    boardView.hideAll();
                    boardView.createButtonsRooms(adjacentRooms);

                    for (JButton b : boardView.bRooms) {
                        b.addMouseListener(new boardMouseListener());
                    }
                } else {
                    getView().printMoveError();
                }


                // Button for Upgrading
            } else if (e.getSource() == boardView.bUpgrade) {
                if (getSystem().checkCanUpgrade()) {
                    boardView.hideAll();
                    for (int i = 0; i < 10; i++) {
                        // Applies correct values from xml file to costs of upgrading
                        if (i < 5) {
                            boardView.setPayment(i, gameModel.getExactPlayer(0).getCastingOffice().costDollars(i + 2));
                        } else {
                            boardView.setPayment(i, gameModel.getExactPlayer(0).getCastingOffice().costCredits(i - 3));
                        }

                        // Enables or disables buttons depending on if the current player can afford an upgrade
                        if (i < 5 && gameModel.getCurrentPlayer().getRank() < (i + 2) && gameModel.getCurrentPlayer().getMoney() >= gameModel.getExactPlayer(0).getCastingOffice().costDollars(i + 2)) {
                            boardView.bPayment[i].addMouseListener(new boardMouseListener());
                            boardView.bPayment[i].setEnabled(true);
                        } else if (i >= 5 && gameModel.getCurrentPlayer().getRank() < (i - 3) && gameModel.getCurrentPlayer().getCredits() >= gameModel.getExactPlayer(0).getCastingOffice().costCredits(i - 3)) {
                            boardView.bPayment[i].addMouseListener(new boardMouseListener());
                            boardView.bPayment[i].setEnabled(true);
                        } else {
                            boardView.bPayment[i].setEnabled(false);
                        }
                    }
                    boardView.showPromptPayment();
                } else {
                    getView().printUpgradeError();
                }
            }


            // Choosing total players Buttons (for start of game)
            for (int i = 0; i < 7; i++) {
                if (e.getSource() == boardView.bPlayerCount[i]) {
                    gameModel.setTotalPlayers(i + 2);
                    try {
                        setUpGame();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }

            // Choosing Upgrade Buttons
            for (int i = 0; i < 10; i++) {
                if (e.getSource() == boardView.bPayment[i]) {
                    // Depending ont he button clicked, will pay with credits or dollars
                    if (i < 5) {
                        upgradeRankDollars((i + 2), gameModel.getExactPlayer(0).getCastingOffice());
                    } else {
                        upgradeRankCredits((i - 3), gameModel.getExactPlayer(0).getCastingOffice());
                    }
                    boardView.updatePlayerIcon(gameModel.getCurrentPlayerInt(), gameModel.getCurrentPlayer().getRank());
                    boardView.hidePayments();
                    boardView.showButtonsDefault();
                    for (JButton b : boardView.bPayment) {
                        b.removeMouseListener(this);
                    }
                    boardView.updatePlayerDisplay(gameModel.getCurrentPlayer().getName(), gameModel.getCurrentPlayer().getMoney(), gameModel.getCurrentPlayer().getCredits(), gameModel.getCurrentPlayer().getPracticeChips(), gameModel.getCurrentPlayerInt(), gameModel.getCurrentDay(), gameModel.getTotalDays());
                }
            }

            // Choosing Room Buttons
            for (int i = 0; i < boardView.bRooms.length; i++) {
                boolean workRoom = false;
                if (e.getSource() == boardView.bRooms[i]) {
                    Room room = roomNameToRoom(boardView.bRooms[i].getText());
                    move(room);

                    // Checks if a scenecard is on the room
                    if (!(gameModel.getCurrentPlayer().getCurrentRoom().getSceneCard() == null)) {
                        if (!gameModel.getCurrentPlayer().getCurrentRoom().getSceneCard().getFlip()) {
                            gameModel.getCurrentPlayer().getCurrentRoom().getSceneCard().setFlip(true);
                            boardView.flipScene(gameModel.getCurrentPlayer().getCurrentRoom().getRoomNumber(), "cards/" + gameModel.getCurrentPlayer().getCurrentRoom().getSceneCard().getImage());
                        }
                        workRoom = true;
                    }

                    // Moves the players dice to the room
                    boardView.displayMove(gameModel.getCurrentPlayerInt(), gameModel.getCurrentPlayer().getCurrentRoom().getCords());
                    boardView.hideRooms();

                    // Prompts user to take a role if they want to and if a role exists
                    if (workRoom) {
                        boardView.showPromptTakeRole();
                    } else {
                        boardView.showButtonsDefault();
                    }

                    for (JButton b : boardView.bRooms) {
                        b.removeMouseListener(this);
                    }
                }
            }

            // Choosing Role Buttons
            for (int i = 0; i < boardView.bRoles.length; i++) {
                if (e.getSource() == boardView.bRoles[i]) {
                    // Gives the player the role and moves player's dice onto roll
                    Role role = roleNameToRole(boardView.bRoles[i].getText());
                    addRole(role);
                    boardView.displayRole(role, gameModel.getCurrentPlayerInt(), gameModel.getCurrentPlayer().getCurrentRoom().getCords());
                    boardView.hideRoles();

                    for (JButton b : boardView.bRoles) {
                        b.removeMouseListener(this);
                    }

                    try {
                        endTurn();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }

            // Take a role, before or after moving
            if (e.getSource() == boardView.bWork || e.getSource() == boardView.bTakeRole[0]) {
                if (getSystem().checkCanAddRole()) {
                    int i = 0;
                    Role[] valid = new Role[gameModel.getCurrentPlayer().getCurrentRoom().availableRoles().length];

                    // Parsing all the roles in a room (Off and oncard)
                    for (Role r : gameModel.getCurrentPlayer().getCurrentRoom().availableRoles()) {
                        if (getSystem().checkRoleValid(r)) {
                            valid[i] = r;
                            i++;
                        }
                    }

                    // Creates buttons for all roles that the player can take
                    if (i > 0) {
                        String[] validNames = new String[i];
                        for (int j = 0; j < i; j++) {
                            validNames[j] = valid[j].getName();
                        }
                        boardView.hideAll();
                        boardView.createButtonsRoles(validNames);

                        for (JButton b : boardView.bRoles) {
                            b.addMouseListener(new boardMouseListener());
                        }
                    } else {
                        getView().printAddRoleError();
                        boardView.hideRoles();
                        boardView.hideAll();
                        boardView.showButtonsDefault();

                    }
                } else {
                    getView().printAlreadyHaveRole();
                }
                // After moving, refuse to take a role
            } else if (e.getSource() == boardView.bTakeRole[1]) {
                // Brings default menu back
                boardView.hideAll();
                boardView.showButtonsDefault();
            }

        }
        
        // Required MouseEvent Stuff, unused
        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }
}