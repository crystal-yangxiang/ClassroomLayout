package caroline_Classroom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UIComponentLibrary
{

    public static JButton CreateJButton(String name, int sizeX, int sizeY, int posX, int posY, ActionListener listener, Frame frame, SpringLayout layout)
    {
        JButton myButton = new JButton(name); //Creates button and sets text
        myButton.addActionListener(listener); //Adds action listener to register click events
        myButton.setPreferredSize(new Dimension(sizeX,sizeY)); //Sets button size
        layout.putConstraint(SpringLayout.WEST,myButton,posX, SpringLayout.WEST,  frame); //Sets button's X Coordinates
        layout.putConstraint(SpringLayout.NORTH,myButton,posY, SpringLayout.NORTH,  frame); //Sets button's Y Coordinates
        frame.add(myButton); //Adds button to frame
        return myButton; //Returns completed button to caller.
    }

    public static JTextField CreateAJTextField(int sizeX, int sizeY, int posX, int posY, Frame frame, SpringLayout layout)
    {
        JTextField myTextField = new JTextField(); //Creates JTextField and sets size
        myTextField.setPreferredSize(new Dimension(sizeX, sizeY));
        layout.putConstraint(SpringLayout.WEST,myTextField,posX, SpringLayout.WEST,  frame);//Sets text field's X Coordinates
        layout.putConstraint(SpringLayout.NORTH,myTextField,posY, SpringLayout.NORTH,  frame);//Sets text field's Y Coordinates
        frame.add(myTextField); //Adds text field to frame
        return  myTextField; //Returns completed text field to caller
    }

    public static JLabel CreateAJLabel(String text, int posX, int posY, Frame frame, SpringLayout layout)
    {
        // Instantiate the Label
        JLabel myLabel = new JLabel(text); //Creates label and sets text
        // Set the position of the Label (From left hand side of the frame (West), and from top of frame (North))
        layout.putConstraint(SpringLayout.WEST,myLabel,posX, SpringLayout.WEST,  frame);//Sets labels's X Coordinates
        layout.putConstraint(SpringLayout.NORTH,myLabel,posY, SpringLayout.NORTH,  frame);//Sets label's Y Coordinates
        frame.add(myLabel); // Adds label to frame
        return myLabel; // Return the label to the calling method
    }
}
