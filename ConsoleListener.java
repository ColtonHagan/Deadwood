/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 3/7/21
Program Description : Shows text printouts of error messages and actions onto a JTextArea in JPane

Referenced the textDemo file from java docs:
https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextDemoProject/src/components/TextDemo.java
*/

import java.awt.*;
import javax.swing.*;

public class ConsoleListener extends JPanel {
    private final JTextArea textArea;

    // Sets up the ConsoleListener which will print text in a JTextArea at the bottom right
    public ConsoleListener() {
        super(new GridBagLayout());

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }

    // Prints given string to the JTextArea
    public void printToLog(String s) {
        textArea.append(s + "\n");

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

}