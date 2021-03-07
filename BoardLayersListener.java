/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

//for testing
import java.io.*; 
import java.util.*; 


import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class BoardLayersListener extends JFrame {
    // GameStateController, Acting as a listener
    GameStateController controller;

    // JLabels
    JLabel boardlabel;
    JLabel mLabel;
    JLabel nameLabel;
    JLabel creditLabel;
    JLabel moneyLabel;
    JLabel diceLabel;
    JLabel[] cardlabel;
    JLabel[] playerlabel;
    JLabel[][] shotlabel;

    //JButtons
    JButton bAct;
    JButton bRehearse;
    JButton bMove;
    JButton bWork;
    JButton bUpgrade;
    JButton[] bRooms;
    JButton[] bRoles;
    JButton[] bPlayerCount;
    JButton[] bTakeRole;
    JButton[] bPayment;
    JButton[] bRank;

    // JLayered Pane
    JLayeredPane bPane;

    // Constructor
    public BoardLayersListener() {
        super("Deadwood");
        cardlabel = new JLabel[10];
        createBoard();
        createButtonsPlayerCount();
    }

    public void addListener(GameStateController controller) {
        this.controller = controller;
    }

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
        setSize(icon.getIconWidth() + 350, icon.getIconHeight());
        // setPreferredSize(new Dimension(icon.getIconWidth()+200,icon.getIconHeight()));
        // setPreferredSize(new Dimension(1280,720));
    }

    public void createScenes(int[] cords, String image, int roomNumber) {
        // Add a scene card to this room
        cardlabel[roomNumber] = new JLabel();
        ImageIcon cIcon = new ImageIcon(image);
        cardlabel[roomNumber].setIcon(cIcon);
        cardlabel[roomNumber].setBounds(cords[0], cords[1], cIcon.getIconWidth(), cIcon.getIconHeight());

        // Add the card to the lower layer
        bPane.add(cardlabel[roomNumber], new Integer(1));
    }
    
    public void removeScene(int roomNumber) {
      cardlabel[roomNumber].setIcon(null);
    }
    
    public void removeShotCounter(int roomNumber) {
      for(int i = shotlabel[roomNumber].length - 1; i >= 0; i--) {
         if(shotlabel[roomNumber][i].getIcon() != null) {
            shotlabel[roomNumber][i].setIcon(null);
            break;
         }
      }
    }
    
    public void displayShotCounters(int roomNumber, int[][] cords) {
      shotlabel[roomNumber] = new JLabel[cords.length];
      for(int i = 0; i < cords.length; i++) {
         ImageIcon icon = new ImageIcon("dice/b1.png");
         shotlabel[roomNumber][i] = new JLabel();
         shotlabel[roomNumber][i].setIcon(icon);
         shotlabel[roomNumber][i].setBounds(cords[i][0], cords[i][1], 60, 60);
         bPane.add(shotlabel[roomNumber][i], new Integer(1));
      }
    }
    
    public void flipScene(int roomNumber, String imageName) {
      ImageIcon cardIcon = new ImageIcon(imageName);
      cardlabel[roomNumber].setIcon(cardIcon);
      bPane.add(cardlabel[roomNumber], new Integer(2));
    }
    public void displayRole(Role newRole, int playerNumber, int[] roomCords) {
         int[] roleCords = newRole.getCords();
         if(newRole.getExtra()) {
            playerlabel[playerNumber].setBounds(roleCords[0],roleCords[1], playerlabel[playerNumber].getIcon().getIconWidth(), playerlabel[playerNumber].getIcon().getIconHeight());
         } else {
            playerlabel[playerNumber].setBounds(roleCords[0] + roomCords[0],roleCords[1] + roomCords[1], playerlabel[playerNumber].getIcon().getIconWidth(), playerlabel[playerNumber].getIcon().getIconHeight());
         }
         bPane.add(playerlabel[playerNumber], new Integer[2]);
    }
    
    public void displayMove(int playerNumber, int[] roomCords) {
      int y = roomCords[1]+100;
      int x = roomCords[0];
      int slotsOver = 0;
      //checks if current player is at locationg
      for(int i = 0; i < playerlabel.length; i++) {
         if(playerlabel[i].getLocation().x == x && playerlabel[i].getLocation().y == y && playerNumber != i) {
            slotsOver++;
            x += playerlabel[playerNumber].getIcon().getIconHeight();
            if(slotsOver == 4) {
               y += playerlabel[playerNumber].getIcon().getIconHeight();
               x = roomCords[0];
            }
         }
      }
      playerlabel[playerNumber].setBounds(x,y, playerlabel[playerNumber].getIcon().getIconWidth(), playerlabel[playerNumber].getIcon().getIconHeight());
      bPane.add(playerlabel[playerNumber], new Integer(2));
      playerlabel[playerNumber].setVisible(true);
    }
    
    public void createPlayerDisplay() {
        nameLabel = new JLabel();
        nameLabel.setBounds(boardlabel.getWidth() + 20, bMove.getBounds().y + 100, 100, 20); //location of this may change if move button is no longer lowest button
        bPane.add(nameLabel, new Integer[2]);

        moneyLabel = new JLabel();
        moneyLabel.setBounds(boardlabel.getWidth() + 30, nameLabel.getBounds().y + 30, 100, 20);
        bPane.add(moneyLabel, new Integer[2]);

        creditLabel = new JLabel();
        creditLabel.setBounds(boardlabel.getWidth() + 30, moneyLabel.getBounds().y + 30, 100, 20);
        bPane.add(creditLabel, new Integer[2]);
        
        diceLabel = new JLabel();
        diceLabel.setBounds(moneyLabel.getLocation().x + 250, moneyLabel.getBounds().y, playerlabel[0].getIcon().getIconWidth(), playerlabel[0].getIcon().getIconHeight());
        bPane.add(diceLabel, new Integer[2]);
    }
    
    public void updatePlayerDisplay(String name, int money, int credits, int activePlayer) {
      nameLabel.setText("Name: " + name);
      moneyLabel.setText("Dollars: " + money);
      creditLabel.setText("Credits: " + credits);
      diceLabel.setIcon(null);
      diceLabel.setIcon(playerlabel[activePlayer].getIcon());
    }

    public int setTotalPlayers(int n) {
        // Add a dice to represent a player.
        // Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
        playerlabel = new JLabel[n];
        ImageIcon[] pIcon = new ImageIcon[n];
        String[] diceChoices = {"b1.png", "c1.png", "g1.png", "o1.png", "p1.png", "r1.png", "v1.png", "w1.png", "y1.png"};
        for (int i = 0; i < n; i++) {
            playerlabel[i] = new JLabel();
            pIcon[i] = new ImageIcon("dice/" + diceChoices[i]);
            playerlabel[i].setIcon(pIcon[i]);

            playerlabel[i].setBounds(0, 0, 46, 46);
            playerlabel[i].setOpaque(false);
            playerlabel[i].setVisible(false);
            bPane.add(playerlabel[i], new Integer[3]);
        }
        return n;
    }

    public void updatePlayerIcon(int playerNumber, int rank) {
        String[] diceChoices = {"b", "c", "g", "o", "p", "r", "v", "w", "y"};
        ImageIcon pIcon = new ImageIcon("dice/" + diceChoices[playerNumber] + rank + ".png");
        playerlabel[playerNumber].setIcon(pIcon);
    }

    public String createPlayers(int playerNumber) {
        String s = JOptionPane.showInputDialog(boardlabel, "Player number " + (playerNumber + 1) + ", Enter your name");
        return s;
    }

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
    }

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
    }

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

        // Default set
        bMove = new JButton("Move");
        bMove.setBackground(Color.white);
        bMove.setBounds(boardlabel.getWidth() + 10, 90, 100, 20);

        bWork = new JButton("Work");
        bWork.setBackground(Color.white);
        bWork.setBounds(boardlabel.getWidth() + 10, 30, 100, 20);

        bUpgrade = new JButton("Upgrade");
        bUpgrade.setBackground(Color.white);
        bUpgrade.setBounds(boardlabel.getWidth() + 10, 60, 100, 20);

        // Initialize for rooms and roles
        bRooms = new JButton[0];
        bRoles = new JButton[0];

        // Prompt set
        bTakeRole = new JButton[2];
        bTakeRole[0] = new JButton("Yes");
        bTakeRole[0].setBackground(Color.white);
        bTakeRole[0].setBounds(boardlabel.getWidth() + 10, 30, 100, 20);

        bTakeRole[1] = new JButton("No");
        bTakeRole[1].setBackground(Color.white);
        bTakeRole[1].setBounds(boardlabel.getWidth() + 10, 50, 100, 20);

        // Payment Type
        bPayment = new JButton[2];
        bPayment[0] = new JButton("Credits");
        bPayment[0].setBackground(Color.white);
        bPayment[0].setBounds(boardlabel.getWidth() + 10, 30, 100, 20);

        bPayment[1] = new JButton("Dollars");
        bPayment[1].setBackground(Color.white);
        bPayment[1].setBounds(boardlabel.getWidth() + 10, 50, 100, 20);

        // Place the action buttons in the top layer
        bPane.add(bAct, new Integer[2]);
        bPane.add(bRehearse, new Integer[2]);
        bPane.add(bMove, new Integer[2]);
        bPane.add(bWork, new Integer[2]);
        bPane.add(bUpgrade, new Integer[2]);
        bPane.add(bTakeRole[0], new Integer[2]);
        bPane.add(bTakeRole[1], new Integer[2]);

        // Hide a few buttons
        bAct.setVisible(false);
        bRehearse.setVisible(false);
        bTakeRole[0].setVisible(false);
        bTakeRole[1].setVisible(false);
    }

    public void hideAll() {
        bAct.setVisible(false);
        bRehearse.setVisible(false);
        bMove.setVisible(false);
        bWork.setVisible(false);
        bUpgrade.setVisible(false);
        mLabel.setVisible(false);
        bTakeRole[0].setVisible(false);
        bTakeRole[1].setVisible(false);
        bPayment[0].setVisible(false);
        bPayment[1].setVisible(false);
    }

    public void hideRoles() {
        for (JButton b : bRoles) {
            b.setVisible(false);
        }
        mLabel.setVisible(false);
    }

    public void hideRooms() {
        for (JButton b : bRooms) {
            b.setVisible(false);
        }
        mLabel.setVisible(false);
    }
    public void hideButtonsPlayerCount() {
        for (JButton b : bPlayerCount) {
            b.setVisible(false);
        }
        mLabel.setVisible(false);
    }

    public void showButtonsDefault() {
        bMove.setVisible(true);
        bWork.setVisible(true);
        bUpgrade.setVisible(true);

        mLabel = new JLabel("Choose Action");
        mLabel.setBounds(boardlabel.getWidth() + 40, 0, 100, 20);
        bPane.add(mLabel, new Integer[2]);
    }

    public void showButtonsHasRole() {
        bAct.setVisible(true);
        bRehearse.setVisible(true);

        mLabel = new JLabel("Choose Action");
        mLabel.setBounds(boardlabel.getWidth() + 40, 0, 100, 20);
        bPane.add(mLabel, new Integer[2]);
    }

    public void showPromptTakeRole() {
        // Create the Menu for action buttons
        mLabel = new JLabel("Take a Role?");
        mLabel.setBounds(boardlabel.getWidth() + 40, 0, 100, 20);
        bPane.add(mLabel, new Integer[2]);

        bTakeRole[0].setVisible(true);
        bTakeRole[1].setVisible(true);
    }

    public void showPromptPayment() {
        mLabel = new JLabel("Payment Type?");
        mLabel.setBounds(boardlabel.getWidth() + 40, 0, 100, 20);
        bPane.add(mLabel, new Integer[2]);

        bPayment[0].setVisible(true);
        bPayment[1].setVisible(true);
    }
}
