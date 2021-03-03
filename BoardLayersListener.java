/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class BoardLayersListener extends JFrame {

    // JLabels
    JLabel boardlabel;
    JLabel[] cardlabel;
    JLabel[] playerlabel;
    JLabel mLabel;

    //JButtons
    JButton bAct;
    JButton bRehearse;
    JButton bMove;

    // JLayered Pane
    JLayeredPane bPane;

    // Constructor
    public BoardLayersListener() {
        super("Deadwood");
        cardlabel = new JLabel[10];
        createBoard();
        createButtons();
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

    public int getTotalPlayers() {
        // Add a dice to represent a player.
        // Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file

        Integer[] options = {2, 3, 4, 5, 6, 7, 8};
        int n = (Integer)JOptionPane.showInputDialog(boardlabel, "How many players? (2-8)",
                "Total Players", JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
        playerlabel = new JLabel[n];

        ImageIcon[] pIcon = new ImageIcon[n];
        String[] diceChoices = {"b1.png", "c1.png", "g1.png", "o1.png", "p1.png", "r1.png", "v1.png", "w1.png", "y1.png"};
        for(int i = 0; i < n; i++) {
            playerlabel[i] = new JLabel();
            pIcon[i] = new ImageIcon(diceChoices[i]);
            playerlabel[i].setIcon(pIcon[i]);

            //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());
            //playerlabel[playerNumber].setBounds(114, 227, 46, 46);
            if(i < 4) {
                playerlabel[i].setBounds(991 + (46*i), 248, 46, 46);
            } else {
                playerlabel[i].setBounds(991 + (46*(i - 4)), 294, 46, 46);
            }

            playerlabel[i].setOpaque(true);
            playerlabel[i].setVisible(true);
            bPane.add(playerlabel[i], new Integer[3]);
        }
        return n;
    }

    public String createPlayers(int playerNumber) {
        String s = JOptionPane.showInputDialog(boardlabel, "Player number " + (playerNumber + 1) + ", Enter your name");
        return s;
    }

    public void createButtons() {
        // Create the Menu for action buttons
        mLabel = new JLabel("MENU");
        mLabel.setBounds(boardlabel.getWidth() + 40, 0, 100, 20);
        bPane.add(mLabel, new Integer[2]);

        // Create Action buttons
        bAct = new JButton("ACT");
        bAct.setBackground(Color.white);
        bAct.setBounds(boardlabel.getWidth() + 10, 30, 100, 20);
        bAct.addMouseListener(new boardMouseListener());

        bRehearse = new JButton("REHEARSE");
        bRehearse.setBackground(Color.white);
        bRehearse.setBounds(boardlabel.getWidth() + 10, 60, 100, 20);
        bRehearse.addMouseListener(new boardMouseListener());

        bMove = new JButton("MOVE");
        bMove.setBackground(Color.white);
        bMove.setBounds(boardlabel.getWidth() + 10, 90, 100, 20);
        bMove.addMouseListener(new boardMouseListener());

        // Place the action buttons in the top layer
        bPane.add(bAct, new Integer[2]);
        bPane.add(bRehearse, new Integer[2]);
        bPane.add(bMove, new Integer[2]);
    }

    // This class implements Mouse Events
    class boardMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == bAct) {
                playerlabel[0].setVisible(true);
                System.out.println("Acting is Selected\n");
            } else if (e.getSource() == bRehearse) {
                System.out.println("Rehearse is Selected\n");
            } else if (e.getSource() == bMove) {
                System.out.println("Move is Selected\n");
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }
/*
    public static void main(String[] args) {

        BoardLayersListener board = new BoardLayersListener();
        board.setVisible(true);

        // Take input from the user about number of players
        JOptionPane.showInputDialog(board, "How many players?");
    }

 */
}
