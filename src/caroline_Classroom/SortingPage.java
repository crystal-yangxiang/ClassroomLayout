package caroline_Classroom;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class SortingPage extends JFrame
{

    private Student[] students;
    private String searchName;
    JTextField[][] textFields;
    SpringLayout myLayout = new SpringLayout();
    MainScreen previousForm;

    // declaration of sorting page constructor
    public SortingPage(Student[] array, String search, MainScreen previousFrame)
    {
        previousForm = previousFrame;

        students = array;
        searchName = search;
        textFields = new JTextField[students.length][3];
        setSize(250, students.length * 20 + 100); // second form size based on the student number
        // size depends on student number
        setLocation(400,50);
        setLayout(myLayout);
        AddWindowListenerToForm();
        SetupTextFields(); // have to SetupTextFields FIRST
        DisplayStudents(); // SECOND DisplayStudents
        DisplaySearchedStudent();


        setVisible(true);
    }
    private void AddWindowListenerToForm()
    {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                previousForm.ClearHighlight();
                dispose();
            }
        });
    }

    // method setup textfields
    private void SetupTextFields()
    {
        for (int y = 0; y < textFields.length; y++)
        {
            for (int x = 0; x < textFields[y].length; x++)
            {
                //popup dialog box textfield setup
                int xPos = x * 60 + 25;
                int yPos = y * 20 + 25;
                textFields[y][x] = UIComponentLibrary.CreateAJTextField(60,20,xPos,yPos,this,myLayout);
                textFields[y][x].setEditable(false); // cannot edit popup window
            }
        }
    }

    private void DisplayStudents()
    {
        Arrays.sort(students);
        for (int i = 0; i < students.length ; i++)
        {
            textFields[i][0].setText(students[i].getStudentName());
            textFields[i][1].setText(Integer.toString(students[i].getyPos()));
            textFields[i][2].setText(Integer.toString(students[i].getxPos()));
            System.out.println(Integer.toString(students[i].getyPos())+ " , "+Integer.toString(students[i].getyPos()));
        }
    }

    private void DisplaySearchedStudent()
    {
        // binary search for student name and highlight with megenta color if has found matched name
        int index = Arrays.binarySearch(students,searchName);
        if (index > -1)
        {
            for (int i = 0; i < textFields[index].length ; i++)
            {
                textFields[index][i].setBackground(Color.MAGENTA);
            }
        }
    }
}

