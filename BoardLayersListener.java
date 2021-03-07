/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

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
    JLabel[] cardlabel;
    JLabel[] playerlabel;


    //JButtons
    JButton bAct;
    JButton bRehearse;
    JButton bUpgrade;
    JButton bWork;
    JButton bMove;
    JButton[] bRooms;
    JButton[] bRoles;
    JButton[] bPlayerCount;

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
        ImageIcon icon = new ImageIcon("board.jpg");
        boardlabel.setIcon(icon);
        boardlabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

        // Add the board to the lowest layer
        bPane.add(boardlabel, new Integer[0]);

        // Set the size of the GUI
        setSize(icon.getIconWidth() + 200, icon.getIconHeight());
        // setPreferredSize(new Dimension(icon.getIconWidth()+200,icon.getIconHeight()));
        // setPreferredSize(new Dimension(1280,720));
    }

    public void createScenes(int[] cords, String image, int roomNumber) {
        // Add a scene card to this room
        cardlabel[roomNumber] = new JLabel();
        ImageIcon cIcon = new ImageIcon("cards/" + image);
        cardlabel[roomNumber].setIcon(cIcon);
        cardlabel[roomNumber].setBounds(cords[0], cords[1], cIcon.getIconWidth() + 2, cIcon.getIconHeight());
        cardlabel[roomNumber].setOpaque(true);

        // Add the card to the lower layer
        bPane.add(cardlabel[roomNumber], new Integer(1));
    }

    public void playerDisplay(String name, int credits, int dollars, int rank) {
        JLabel nameLabel = new JLabel("Name: " + name);
        nameLabel.setBounds(boardlabel.getWidth() + 20, bMove.getBounds().y + 30, 100, 20); //location of this may change if move button is no longer smallest button
        bPane.add(nameLabel, new Integer[2]);

        JLabel moneyLabel = new JLabel("Dollars: " + dollars);
        moneyLabel.setBounds(boardlabel.getWidth() + 30, nameLabel.getBounds().y + 30, 100, 20);
        bPane.add(moneyLabel, new Integer[2]);

        JLabel creditLabel = new JLabel("Credits: " + credits);
        creditLabel.setBounds(boardlabel.getWidth() + 30, moneyLabel.getBounds().y + 30, 100, 20);
        bPane.add(creditLabel, new Integer[2]);

        JLabel rankLabel = new JLabel("Rank: " + rank);
        rankLabel.setBounds(boardlabel.getWidth() + 30, creditLabel.getBounds().y + 30, 100, 20);
        bPane.add(rankLabel, new Integer[2]);
    }

    public int setTotalPlayers(int n) {
        // Add a dice to represent a player.
        // Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
        playerlabel = new JLabel[n];

        ImageIcon[] pIcon = new ImageIcon[n];
        String[] diceChoices = {"b1.png", "c1.png", "g1.png", "o1.png", "p1.png", "r1.png", "v1.png", "w1.png", "y1.png"};
        for (int i = 0; i < n; i++) {
            playerlabel[i] = new JLabel();
            pIcon[i] = new ImageIcon(diceChoices[i]);
            playerlabel[i].setIcon(pIcon[i]);

            //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());
            //playerlabel[playerNumber].setBounds(114, 227, 46, 46);
            if (i < 4) {
                playerlabel[i].setBounds(991 + (46 * i), 248, 46, 46);
            } else {
                playerlabel[i].setBounds(991 + (46 * (i - 4)), 294, 46, 46);
            }

            playerlabel[i].setOpaque(false);
            playerlabel[i].setVisible(true);
            bPane.add(playerlabel[i], new Integer[3]);
        }
        return n;
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

    public void removeButtonsPlayerCount() {
        for (JButton b : bPlayerCount) {
            b.setVisible(false);
        }
        mLabel.setVisible(false);
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
        bWork = new JButton("Work");
        bWork.setBackground(Color.white);
        bWork.setBounds(boardlabel.getWidth() + 10, 30, 100, 20);

        bUpgrade = new JButton("Upgrade");
        bUpgrade.setBackground(Color.white);
        bUpgrade.setBounds(boardlabel.getWidth() + 10, 60, 100, 20);

        bMove = new JButton("Move");
        bMove.setBackground(Color.white);
        bMove.setBounds(boardlabel.getWidth() + 10, 90, 100, 20);

        // Place the action buttons in the top layer
        bPane.add(bAct, new Integer[2]);
        bPane.add(bRehearse, new Integer[2]);
        bPane.add(bMove, new Integer[2]);
        bPane.add(bWork, new Integer[2]);
        bPane.add(bUpgrade, new Integer[2]);

        // Hide a few buttons
        bAct.setVisible(false);
        bRehearse.setVisible(false);
    }

    public void hideAll() {
        bAct.setVisible(false);
        bRehearse.setVisible(false);
        bMove.setVisible(false);
        bWork.setVisible(false);
        bUpgrade.setVisible(false);
        mLabel.setVisible(false);
    }

    public void setButtonsDefault() {
        bMove.setVisible(true);
        bWork.setVisible(true);
        bUpgrade.setVisible(true);
        mLabel.setVisible(true);
    }
}
