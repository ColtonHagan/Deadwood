/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 3/7/21
Program Description : Displays gui elements about current gamestate

Refrenced Deadwood GUI helper file by Moushumi Sharmin
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class BoardView extends JFrame {
    // GameStateController, Acting as a listener
    GameStateController controller;

    ConsoleListener consoleListener;

    // JLabels
    JLabel boardlabel;
    JLabel mLabel;
    JLabel nameLabel;
    JLabel creditLabel;
    JLabel moneyLabel;
    JLabel diceLabel;
    JLabel dayLabel;
    JLabel practiceLabel;
    JLabel[] cardlabel;
    JLabel[] playerlabel;
    JLabel[][] shotlabel;

    //JButtons
    JButton bAct;
    JButton bRehearse;
    JButton bMove;
    JButton bWork;
    JButton bUpgrade;
    JButton bCancel;
    JButton bEndTurn;
    JButton[] bRooms;
    JButton[] bRoles;
    JButton[] bPlayerCount;
    JButton[] bTakeRole;
    JButton[] bPayment;

    // JLayered Pane
    JLayeredPane bPane;

    // Constructor
    public BoardView() {
        super("Deadwood");
        cardlabel = new JLabel[10];
        createBoard();
        createButtonsPlayerCount();
        createConsoleListener();
    }

    // Adds controller as a listener
    public void addListener(GameStateController controller) {
        this.controller = controller;
    }

    //Sets up the board picture
    public void createBoard() {
        // Set the title of the JFrame
        // Set the exit option for the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create the JLayeredPane to hold the display, cards, dice and buttons
        bPane = getLayeredPane();

        // Create the deadwood board
        boardlabel = new JLabel();
        shotlabel = new JLabel[10][];
        ImageIcon icon = new ImageIcon("board.jpg");
        boardlabel.setIcon(icon);
        boardlabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

        // Add the board to the lowest layer
        bPane.add(boardlabel, new Integer[0]);

        // Set the size of the GUI
        setSize(icon.getIconWidth() + 350, icon.getIconHeight() + 50);
    }

    // Hides all button elements and shows end game message
    public void endGame() {
         hideAll();
         JLabel endGame = new JLabel("Game Over");
         endGame.setBounds(boardlabel.getWidth() + 60, 20, 200, 100);
         endGame.setFont(new Font("Serif", Font.BOLD, 40));
         bPane.add(endGame, new Integer(4));
    }

    // Creates text box and bottom right that listens to DeadwoodView for strings
    private void createConsoleListener() {
        // Add a listener for console
        consoleListener = new ConsoleListener();
        consoleListener.setBounds(boardlabel.getWidth() + 20, 400, 300, 500);
        consoleListener.setVisible(true);
        bPane.add(consoleListener);
    }

    // Sets scene cards onto rooms and flips
    public void createScenes(int[] cords, String image, int roomNumber) {
        // Add a scene card to this room
        cardlabel[roomNumber] = new JLabel();
        ImageIcon cIcon = new ImageIcon(image);
        cardlabel[roomNumber].setIcon(cIcon);
        cardlabel[roomNumber].setBounds(cords[0], cords[1], cIcon.getIconWidth(), cIcon.getIconHeight());

        // Add the card to the lower layer
        bPane.add(cardlabel[roomNumber], new Integer(1));
    }

    // Removes scene card picture on scene end
    public void removeScene(int roomNumber) {
      if(cardlabel[roomNumber] != null) {
         cardlabel[roomNumber].setIcon(null);
      }
    }

    // Remove shot counter picture on act success
    public void removeShotCounter(int roomNumber) {
      for(int i = 0; i < shotlabel[roomNumber].length; i++) {
         if(shotlabel[roomNumber][i].getIcon() != null) {
            shotlabel[roomNumber][i].setIcon(null);
            break;
         }
      }
    }

    // Fills out all shot counters on board
    public void displayShotCounters(int roomNumber, int[][] cords) {
      shotlabel[roomNumber] = new JLabel[cords.length];
      for(int i = 0; i < cords.length; i++) {
         ImageIcon icon = new ImageIcon("clapperboard.png");
         shotlabel[roomNumber][i] = new JLabel();
         shotlabel[roomNumber][i].setIcon(icon);
         shotlabel[roomNumber][i].setBounds(cords[i][0], cords[i][1] - 15, 60, 60);
         bPane.add(shotlabel[roomNumber][i], new Integer(1));
      }
    }

    // Flips the sceneCard face up
    public void flipScene(int roomNumber, String imageName) {
      cardlabel[roomNumber].setIcon(null);
      ImageIcon cardIcon = new ImageIcon(imageName);
      cardlabel[roomNumber].setIcon(cardIcon);
      bPane.add(cardlabel[roomNumber], new Integer(2));
    }

    // Places the player's dice onto the role location on board
    public void displayRole(Role newRole, int playerNumber, int[] roomCords) {
         int[] roleCords = newRole.getCords();
         if(newRole.getExtra()) {
            playerlabel[playerNumber].setBounds(roleCords[0],roleCords[1], playerlabel[playerNumber].getIcon().getIconWidth(), playerlabel[playerNumber].getIcon().getIconHeight());
         } else {
            playerlabel[playerNumber].setBounds(roleCords[0] + roomCords[0],roleCords[1] + roomCords[1], playerlabel[playerNumber].getIcon().getIconWidth(), playerlabel[playerNumber].getIcon().getIconHeight());
         }
         bPane.add(playerlabel[playerNumber], new Integer[4]);
    }

    // Moves the player to a new room
    public void displayMove(int playerNumber, int[] roomCords) {
      int y = roomCords[1]+100;
      int x = roomCords[0];
      int slotsOver = -1;
      //checks if current player is at location
      while(slotsOver != 0) {
         slotsOver = 0;
         for(int i = 0; i < playerlabel.length; i++) {
            if(playerlabel[i].getLocation().x == x && playerlabel[i].getLocation().y == y && playerNumber != i) {
               slotsOver++;
               if(x > (2*playerlabel[playerNumber].getIcon().getIconWidth() + roomCords[0])) {
                  y += playerlabel[playerNumber].getIcon().getIconHeight();
                  x = roomCords[0];
               } else {
                  x += playerlabel[playerNumber].getIcon().getIconWidth();
               }
            }
         }
      }
      playerlabel[playerNumber].setBounds(x,y, playerlabel[playerNumber].getIcon().getIconWidth(), playerlabel[playerNumber].getIcon().getIconHeight());
      bPane.add(playerlabel[playerNumber], new Integer(4));
      playerlabel[playerNumber].setVisible(true);
    }

    // Displays player's name, credits, money, practicechips, and day count on right side
    public void createPlayerDisplay() {
        nameLabel = new JLabel();
        nameLabel.setBounds(boardlabel.getWidth() + 20, bMove.getBounds().y + 140, 100, 20); //location of this may change if move button is no longer lowest button
        bPane.add(nameLabel, new Integer[2]);

        moneyLabel = new JLabel();
        moneyLabel.setBounds(boardlabel.getWidth() + 30, nameLabel.getBounds().y + 30, 100, 20);
        bPane.add(moneyLabel, new Integer[2]);

        creditLabel = new JLabel();
        creditLabel.setBounds(boardlabel.getWidth() + 30, moneyLabel.getBounds().y + 30, 100, 20);
        bPane.add(creditLabel, new Integer[2]);
        
        practiceLabel = new JLabel();
        practiceLabel.setBounds(boardlabel.getWidth() + 30, creditLabel.getBounds().y + 30, 100, 20);
        bPane.add(practiceLabel, new Integer[2]);
        
        dayLabel = new JLabel();
        dayLabel.setBounds(boardlabel.getWidth() + 30, practiceLabel.getBounds().y + 30, 100, 20);
        bPane.add(dayLabel, new Integer[2]);
        
        diceLabel = new JLabel();
        diceLabel.setBounds(moneyLabel.getLocation().x + 250, moneyLabel.getBounds().y, playerlabel[0].getIcon().getIconWidth(), playerlabel[0].getIcon().getIconHeight());
        bPane.add(diceLabel, new Integer[2]);
    }

    // Updates the player display information with correct info
    public void updatePlayerDisplay(String name, int money, int credits, int practice, int activePlayer, int day, int totalDays) {
      nameLabel.setText("Name: " + name);
      moneyLabel.setText("Dollars: " + money);
      creditLabel.setText("Credits: " + credits);
      practiceLabel.setText("Practice Chips: " + practice);
      dayLabel.setText("Day: " + day + " of " + totalDays);
      diceLabel.setIcon(null);
      diceLabel.setIcon(playerlabel[activePlayer].getIcon());
    }

    // Creates the playerlabel for all the players
    public int setTotalPlayers(int n) {
        playerlabel = new JLabel[n];
        ImageIcon[] pIcon = new ImageIcon[n];
        for (int i = 0; i < n; i++) {
            playerlabel[i] = new JLabel();

            playerlabel[i].setBounds(0, 0, 46, 46);
            playerlabel[i].setOpaque(false);
            playerlabel[i].setVisible(false);
            pIcon[i] = new ImageIcon();
            if(n < 7) {
               updatePlayerIcon(i, 1);
            } else {
               updatePlayerIcon(i, 2);
            }
            bPane.add(playerlabel[i], new Integer[2]);
        }
        return n;
    }

    // Sets up the payment buttons with correct costs from the xml
    public void setPayment(int i, int payment) {
        bPayment[i] = new JButton("" + payment);
        bPayment[i].setBackground(Color.white);
        if(i < 5) {
            bPayment[i].setBounds(boardlabel.getWidth() + 10, 30 + (i * 20), 50, 20);
        } else {
            bPayment[i].setBounds(boardlabel.getWidth() + 60, 30 + ((i - 5) * 20), 50, 20);
        }
        bPane.add(bPayment[i], new Integer[2]);
    }

    // Sets player's dice to the given rank's dice
    public void updatePlayerIcon(int playerNumber, int rank) {
        String[] diceChoices = {"b", "c", "g", "o", "p", "r", "v", "w", "y"};
        ImageIcon pIcon = new ImageIcon("dice/" + diceChoices[playerNumber] + rank + ".png");
        playerlabel[playerNumber].setIcon(pIcon);
    }

    // Prompts at start for player names
    public String createPlayers(int playerNumber) {
        String s = JOptionPane.showInputDialog(boardlabel, "Player number " + (playerNumber + 1) + ", Enter your name");
        return s;
    }

    // Creates buttons at very start to choose total players
    public void createButtonsPlayerCount() {
        mLabel = new JLabel("How Many Players?");
        mLabel.setBounds(boardlabel.getWidth() + 10, 0, 150, 20);
        bPane.add(mLabel, new Integer[2]);

        bPlayerCount = new JButton[7];
        for (int i = 0; i < 7; i++){
            bPlayerCount[i] = new JButton("" + (i + 2));
            bPlayerCount[i].setBackground(Color.white);
            bPlayerCount[i].setBounds(boardlabel.getWidth() + 10, (30 + (i * 30)), 100, 20);
            bPane.add(bPlayerCount[i], new Integer[2]);
        }
    }

    // Creates room buttons from the given String array
    public void createButtonsRooms(String[] adjacentRooms) {
        mLabel = new JLabel("Choose Room");
        mLabel.setBounds(boardlabel.getWidth() + 40, 0, 100, 20);
        bPane.add(mLabel, new Integer[2]);

        bRooms = new JButton[adjacentRooms.length];
        for (int i = 0; i < adjacentRooms.length; i++) {
            bRooms[i] = new JButton(adjacentRooms[i]);
            bRooms[i].setBackground(Color.white);
            bRooms[i].setBounds(boardlabel.getWidth() + 10, 30 + (i * 20), 300, 20);
            bPane.add(bRooms[i], new Integer[2]);
        }
        bCancel.setVisible(true);
    }

    // Creates role buttons from the given String array
    public void createButtonsRoles(String[] roles) {
        mLabel = new JLabel("Choose Role");
        mLabel.setBounds(boardlabel.getWidth() + 40, 0, 100, 20);
        bPane.add(mLabel, new Integer[2]);

        bRoles = new JButton[roles.length];
        for (int i = 0; i < roles.length; i++) {
            bRoles[i] = new JButton(roles[i]);
            bRoles[i].setBackground(Color.white);
            bRoles[i].setBounds(boardlabel.getWidth() + 10, 30 + (i * 20), 300, 20);
            bPane.add(bRoles[i], new Integer[2]);
        }
        bCancel.setVisible(true);
    }

    // Creates all default buttons
    public void createButtons() {
        // Create the Menu for action buttons
        mLabel = new JLabel("Choose Action");
        mLabel.setBounds(boardlabel.getWidth() + 40, 0, 100, 20);
        bPane.add(mLabel, new Integer[2]);

        // Acting set
        bAct = new JButton("Act");
        bAct.setBackground(Color.white);
        bAct.setBounds(boardlabel.getWidth() + 10, 30, 100, 20);

        bRehearse = new JButton("Rehearse");
        bRehearse.setBackground(Color.white);
        bRehearse.setBounds(boardlabel.getWidth() + 10, 50, 100, 20);

        // Default buttons like moving, work, etc
        bMove = new JButton("Move");
        bMove.setBackground(Color.white);
        bMove.setBounds(boardlabel.getWidth() + 10, 90, 100, 20);

        bWork = new JButton("Work");
        bWork.setBackground(Color.white);
        bWork.setBounds(boardlabel.getWidth() + 10, 30, 100, 20);

        bUpgrade = new JButton("Upgrade");
        bUpgrade.setBackground(Color.white);
        bUpgrade.setBounds(boardlabel.getWidth() + 10, 60, 100, 20);

        bCancel = new JButton("Cancel");
        bCancel.setBackground(Color.white);
        bCancel.setBounds(boardlabel.getWidth() + 10, bMove.getBounds().y + 100, 200, 20);

        bEndTurn = new JButton("End Turn");
        bEndTurn.setBackground(Color.white);
        bEndTurn.setBounds(boardlabel.getWidth() + 10, bMove.getBounds().y + 120, 200, 20);

        // Initialize for rooms and roles
        bRooms = new JButton[0];
        bRoles = new JButton[0];
        bPayment = new JButton[10];

        // Prompt set for yes no buttons
        bTakeRole = new JButton[2];
        bTakeRole[0] = new JButton("Yes");
        bTakeRole[0].setBackground(Color.white);
        bTakeRole[0].setBounds(boardlabel.getWidth() + 10, 30, 100, 20);

        bTakeRole[1] = new JButton("No");
        bTakeRole[1].setBackground(Color.white);
        bTakeRole[1].setBounds(boardlabel.getWidth() + 10, 50, 100, 20);

        // Place the action buttons in the top layer
        bPane.add(bAct, new Integer[2]);
        bPane.add(bRehearse, new Integer[2]);
        bPane.add(bMove, new Integer[2]);
        bPane.add(bWork, new Integer[2]);
        bPane.add(bUpgrade, new Integer[2]);
        bPane.add(bCancel, new Integer[2]);
        bPane.add(bEndTurn, new Integer[2]);
        bPane.add(bTakeRole[0], new Integer[2]);
        bPane.add(bTakeRole[1], new Integer[2]);

        // Hide a few buttons
        bAct.setVisible(false);
        bRehearse.setVisible(false);
        bTakeRole[0].setVisible(false);
        bTakeRole[1].setVisible(false);
        bCancel.setVisible(false);
    }

    // Hides all the buttons that will not change, most of the non - array buttons
    public void hideAll() {
        bAct.setVisible(false);
        bRehearse.setVisible(false);
        bMove.setVisible(false);
        bWork.setVisible(false);
        bUpgrade.setVisible(false);
        bEndTurn.setVisible(false);
        mLabel.setVisible(false);
        bTakeRole[0].setVisible(false);
        bTakeRole[1].setVisible(false);
    }

    // Hides roles buttons which will change as game progresses
    public void hideRoles() {
        for (JButton b : bRoles) {
            if (!(b == null)) {
                b.setVisible(false);
            }
        }
        mLabel.setVisible(false);
        bCancel.setVisible(false);
    }

    // Hides rooms buttons which will change as game progresses
    public void hideRooms() {
        for (JButton b : bRooms) {
            if (!(b == null)) {
                b.setVisible(false);
            }
        }
        mLabel.setVisible(false);
        bCancel.setVisible(false);
    }

    // Hides paymentButtons as this can change depending on xml settings
    public void hidePayments() {
        for (JButton b : bPayment) {
            if (!(b == null)) {
                b.setVisible(false);
            }
        }
        mLabel.setVisible(false);
        bCancel.setVisible(false);
    }

    // Hides playercount
    public void hideButtonsPlayerCount() {
        for (JButton b : bPlayerCount) {
            b.setVisible(false);
        }
        mLabel.setVisible(false);
    }

    // Shows the default button setup on turn start
    public void showButtonsDefault() {
        bMove.setVisible(true);
        bWork.setVisible(true);
        bUpgrade.setVisible(true);
        bEndTurn.setVisible(true);

        mLabel = new JLabel("Choose Action");
        mLabel.setBounds(boardlabel.getWidth() + 40, 0, 100, 20);
        bPane.add(mLabel, new Integer[2]);
    }

    // Shows the button setup for when a player has a role
    public void showButtonsHasRole() {
        bAct.setVisible(true);
        bRehearse.setVisible(true);

        mLabel = new JLabel("Choose Action");
        mLabel.setBounds(boardlabel.getWidth() + 40, 0, 100, 20);
        bPane.add(mLabel, new Integer[2]);
    }

    // Shows the button setup after a player moves, which gives them choice to take role
    public void showPromptTakeRole() {
        // Create the Menu for action buttons
        mLabel = new JLabel("Take a Role?");
        mLabel.setBounds(boardlabel.getWidth() + 40, 0, 100, 20);
        bPane.add(mLabel, new Integer[2]);

        bTakeRole[0].setVisible(true);
        bTakeRole[1].setVisible(true);
    }

    // Shows button setup for when player chooses upgrade button, allows player to upgrade rank
    public void showPromptPayment() {
        mLabel = new JLabel("Dollars | Credits");
        mLabel.setBounds(boardlabel.getWidth() + 16, 0, 100, 20);
        bPane.add(mLabel, new Integer[2]);

        for(int i = 0; i < 10; i++) {
            bPayment[i].setVisible(true);
        }
        bCancel.setVisible(true);
    }
}
