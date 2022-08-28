package caroline_Classroom;

/** --------------------------------------------------------
 * Class: MainScreen
 *
 * @author Yang Xiang
 *
 * Developed: 2021
 *
 * Purpose: Caroline classroom Java application for view, edit, save and read classroom details
 *          with student names and classroom layout
 *
 * Demonstrating the implementation of:
 * - An array of objects
 * - Frames
 * - Action and Window Listeners
 * - Text file data management
 *
 * ----------------------------------------------------------
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;
import java.util.RandomAccess;

public class MainScreen extends JFrame implements ActionListener

{
    // Declaration of the various screen objects - labels, textfields and buttons
    SpringLayout myLayout = new SpringLayout();
    JLabel lblTeacher, lblClass, lblRoom, lblDate;
    JButton btnExit, btnOpen, btnSave, btnBinary, btnClear, btnSort,btnRaf;
    JTextField[][] textFields = new JTextField[19][10]; // 19 rows and 10 columns
    JTextField txtSearch, txtTeacher, txtClass, txtRoom, txtDate;
    private String searchName;
    Color highlightColor = Color.MAGENTA; // set highlight background color with megenta of binary search



    public MainScreen()
    {
        //Sets up the screen layout and listeners.
        setSize(870,700);
        setLocation(200,28);
        AddWindowListenerToForm();
        setLayout(myLayout);
        SetupLabel();
        SetupButton();
        SetupTextFields();


        setVisible(true); // always do last
    }

    private void SetupLabel()
    {
        // Each line calls the LocateALabel method to establish each Label
        lblTeacher = UIComponentLibrary.CreateAJLabel("Teacher:", 40, 45, this, myLayout);
        lblClass = UIComponentLibrary.CreateAJLabel("Class:", 240, 45, this, myLayout);
        lblRoom = UIComponentLibrary.CreateAJLabel("Room:", 440, 45, this, myLayout);
        lblDate = UIComponentLibrary.CreateAJLabel("Date:", 640, 45, this, myLayout);
    }

    private void SetupButton()
    {
        // Each line calls the LocateAButton method to establish each button
        btnClear = UIComponentLibrary.CreateJButton("Clear", 70,25,40,550,this, this, myLayout);
        btnSave = UIComponentLibrary.CreateJButton("Save", 70, 25, 120, 550,this,this,myLayout);
        btnSort = UIComponentLibrary.CreateJButton("Sort", 70,25,200,550,this, this, myLayout);
        btnOpen = UIComponentLibrary.CreateJButton("Open",70, 25, 280,550,this,this,myLayout);
        btnRaf = UIComponentLibrary.CreateJButton("Raf", 70, 25, 360, 550,this,this,myLayout);
        btnBinary = UIComponentLibrary.CreateJButton("Binary Search", 120, 25, 440,550,this,this,myLayout);
        btnExit = UIComponentLibrary.CreateJButton("Exit", 70,25,730,550,this, this, myLayout);
    }

    private void SetupTextFields()
    {
        // Each line calls the LocateATextField method to establish each textfield
        txtSearch = UIComponentLibrary.CreateAJTextField(120,25,570,550,this,myLayout);
        txtTeacher = UIComponentLibrary.CreateAJTextField(120,23,100,45,this,myLayout);
        txtClass = UIComponentLibrary.CreateAJTextField(120,23,290,45,this,myLayout);
        txtRoom = UIComponentLibrary.CreateAJTextField(120,23,490,45,this,myLayout);
        txtDate = UIComponentLibrary.CreateAJTextField(120,23,680,45,this,myLayout);

        // set up textfield rows and columns size
        for(int y = 0; y < textFields.length; y++)
        {
            for(int x = 0; x < textFields[y].length; x++)
            {
                int xPos = x * 76 + 40;
                int yPos = y * 24 + 80;
                textFields[y][x] = UIComponentLibrary.CreateAJTextField(78,25,xPos,yPos,this, myLayout);

                textFields[y][x].addFocusListener(new FocusAdapter()
                {
                    @Override
                    public void focusLost(FocusEvent e )
                    {
                        super.focusLost(e);
                        JTextField text = (JTextField)e.getSource();

                        //call checkTectFieldForTable method
                        CheckTextFieldForTable( text );
                        //if (text.getText().equalsIgnoreCase("table"))
                        //{
                        // text.setBackground((Color.));
                        // }
                    }
                })
                ;
            }
        }
    }
    // add window listener to form
    private void AddWindowListenerToForm()
    {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
    }
    // call action listener
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == btnClear) // Clear button
        {
            ClearTextFields();
        }
        if(e.getSource() == btnExit)// Exit button
        {
            System.exit(0);
        }
        if(e.getSource() == btnOpen)// open button
        {
            //ReadFromFile();
            SelectFileToLoad();
        }
        if(e.getSource() == btnSave)// save button
        {
            SaveToFile();
        }
        if(e.getSource() == btnRaf)// raf button
        {
            SaveToRafFile();
        }
        if(e.getSource() == btnBinary)// bianry search button
        {
            String findString = txtSearch.getText(); // search string in the binary search textbox and get text
            HighlightCell( findString ); // highlight the string background

            Student[] array = GetStudentDetailForSortForm();// call GetStudentDetailForSortForm method
            //Student[] students = GetStudentDetailForSortForm();
            new SortingPage(array, findString,this);
            /*int index = Arrays.binarySearch(students,searchName);

            if(index > -1)
            {
                for(int i = 0; i <textFields[index].length; i++ )
                {
                    if(txtSearch.getText() == null)
                    {

                    }
                }
            }*/
        }
        if(e.getSource() == btnSort) //sort button
        {
            Student[] students = GetStudentDetailForSortForm(); // call GetStudentDetailForSortForm method
            new SortingPage(students, "",this);
        }
    }

    public void ClearHighlight()
    {
        for(int y = 0; y < textFields.length; y++)
        {
            for (int x = 0; x < textFields[y].length; x++) {
                JTextField currentCellField = textFields[y][x];

                if (currentCellField.getBackground() == highlightColor)
                {
                    currentCellField.setBackground(Color.white);
                }
            }
        }
    }
    // delaration of ClearTextFields method to clear all entries from any text fields
    private void ClearTextFields()
    {
        txtTeacher.setText("");
        txtClass.setText("");
        txtRoom.setText("");
        txtDate.setText("");

        for (int y = 0; y < textFields.length; y++)
        {
            for (int x = 0; x < textFields[y].length; x++)
            {
                textFields[y][x].setText("");
                textFields[y][x].setBackground(Color.white); // white background color
            }
        }
    }

    // Delaration of GetStudentDetailForSortForm method
    private  Student[] GetStudentDetailForSortForm()
    {
        //student list
        LinkedList<Student> studentList = new LinkedList<>();
        for (int y = 0; y < textFields.length; y++)
        {
            for (int x = 0; x <textFields[y].length ; x++)
            {
                // if the text field if not empty and get text table and bkgrnd fill (ignore case)
                if (textFields[y][x].getText().isEmpty() == false && !textFields[y][x].getText().equalsIgnoreCase("Table")
                        && !textFields[y][x].getText().equalsIgnoreCase("BKGRND FILL"))
                {
                    // add to student list
                    studentList.add(new Student(textFields[y][x].getText(),y,x));
                }
            }
        }
        return studentList.toArray(new Student[studentList.size()]);
    }

    private void SaveToFile()// save to .csv file
    {
        FileDialog file = new FileDialog(this,"Save File!", FileDialog.SAVE);
        file.setDirectory("C://"); // set directory to C:// drive
        file.setVisible(true); // set file frame visible
        String filePath = file.getDirectory() + file.getFile();// set file path as file directory plus file name
        if (file.getFile() == null)
        {
            return;
        }
        if(filePath.endsWith(".csv") == false)// if filepath not ends with .csv and .txt, make it .csv
        {
            if(filePath.endsWith(".txt") == false)
            {
                filePath +=".csv";
            }
        }
        try
        {
            //write header data
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("Teacher:, " + txtTeacher.getText());
            writer.newLine();
            writer.write("Class:, " + txtClass.getText());
            writer.newLine();
            writer.write("Room:, " + txtRoom.getText());
            writer.newLine();
            writer.write("Date:, " + txtDate.getText());
            writer.newLine();

            for(int y = 0; y < textFields.length; y++)
            {
                for(int x = 0; x < textFields[y].length; x++)
                {
                    if(textFields[y][x].getText().isEmpty() == false)
                    {
                        writer.write((x + "," + y + "," + textFields[y][x].getText() + ",Cyan"));
                        writer.newLine();
                    }
                }
            }
            writer.close();
            JOptionPane.showMessageDialog(null,"Records Saved!");//show "Records Saved!" in message dialog
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"Something went wrong! Try again!");
            //show "Something went wrong! Try again!" in message dialog
        }
    }

    //ReadFromFile method
    private void ReadFromFile(String filePath)
    {
        /*FileDialog fd = new FileDialog(this, "Choose a file to open", FileDialog.LOAD);
        fd.setDirectory("C://");
        fd.setVisible(true);
        String filePath = fd.getDirectory() + fd.getFile();
        if(fd.getFile() == null)
        {
            return;
        }*/
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;// line is used to temporarily store the line read in from the data file
            String [] header; // header means "teacher, class, room and date line
            //int i = 0;
            header = br.readLine().split(",");// split with comma
            txtTeacher.setText(header[1]);
            header = br.readLine().split(",");
            txtClass.setText(header[1]);
            header = br.readLine().split(",");
            txtRoom.setText(header[1]);
            header = br.readLine().split(",");
            txtDate.setText(header[1]);

            while((line = br.readLine()) != "")
            {
                String[] temp = line.split(",");

                int yPos = Integer.parseInt(temp[1]);
                int xPos = Integer.parseInt(temp[0]);
                textFields[yPos][xPos].setText(temp[2]);

                //textFields[yPos][xPos].setBackground(Color.white);

                //if (textFields[yPos][xPos].getText().equalsIgnoreCase("table"))
                //{
                // textFields[yPos][xPos].setBackground(Color.cyan);
                // textFields[yPos][xPos].setText("");
                //}

                CheckForTable(temp[2],xPos,yPos);
                if(temp.length> 3 && temp[3].contains("Cyan"))
                {
                    //textFields[yPos][xPos].setBackground(Color.cyan);
                }
            }
                /*else
                {
                    text[i] = temp[0];
                    txtTeacher.setText(text[0]);
                    txtClass.setText(text[1]);
                    txtRoom.setText(text[2]);
                    txtDate.setText(text[3]);
                    i++;
                }*/
        }
        //read.close();

        catch(Exception e)
        {

        }
    }

    //delaration of SelectFileToLoad method
    private void SelectFileToLoad()
    {
        FileDialog file = new FileDialog(this,"Save File!", FileDialog.LOAD);
        file.setDirectory("C://");// set directory to C:// drive
        file.setVisible(true);// set file frame visible
        String filePath = file.getDirectory() + file.getFile();
        if (file.getFile() == null)
        {
            return;
        }

        ClearTextFields();

        if (file.getFile().endsWith(".RAF"))
        {
            ReadFromRafFile(filePath);
            // RUN RAF READ METHOD
            return;
        }
        ReadFromFile(filePath);
    }

    private void SaveToRafFile()// save to .raf file
    {
        FileDialog fd = new FileDialog(this, "Choose a file to open", FileDialog.LOAD);
        fd.setDirectory("C://");
        fd.setVisible(true);
        String filePath = fd.getDirectory() + fd.getFile();

        if(fd.getFile() == null)
        {
            return;
        }
        if(!filePath.endsWith(".RAF"))
        {
            filePath += ".RAF";
        }

        try
        {
            RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
            int count = 0;

            raf.seek(0); // seek and start at position 0
            raf.writeUTF(txtTeacher.getText());
            raf.seek(50); // seek and start at position 50
            raf.writeUTF(txtClass.getText());
            raf.seek(100); // seek and start at position 100
            raf.writeUTF(txtRoom.getText());
            raf.seek(150); // seek and start at position 150
            raf.writeUTF(txtDate.getText());

            count = 2; // 2 = 2*100 line351


            for(int y = 0; y < textFields.length; y++)
            {
                for(int x = 0; x < textFields[y].length; x++)
                {
                    if(textFields[y][x].getText().isEmpty() == false) // if it's not empty
                    {
                        // entry
                        int pointer = count * 100;
                        raf.seek(pointer); // seek a position
                        raf.writeUTF(textFields[y][x].getText());
                        raf.seek(pointer + 50); // seek position
                        raf.writeInt(y); // write
                        raf.seek(pointer + 75);
                        raf.writeInt(x);
                        count++;
                    }
                }
            }
            raf.close();
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

    private void ReadFromRafFile(String filePath)// read from either both .csv or .raf files
    {
        try
        {
            RandomAccessFile raf = new RandomAccessFile(filePath, "r");
            int count = 0;
            long length = raf.length(); // find the length of raf file
            String header;// header is used to temporarily store the header read in from the data file

            //seek and read from 0, 50, 100 and 150 pointer
            raf.seek(0);
            header = raf.readUTF();
            txtTeacher.setText(header);

            raf.seek(50);
            header = raf.readUTF();
            txtClass.setText(header);

            raf.seek(100);
            header = raf.readUTF();
            txtRoom.setText(header);

            raf.seek(150);
            header = raf.readUTF();
            txtDate.setText(header);

            count = 2;



            while(count * 100 < length) // while loop #is reading to true
                // check it has value in it
            {
                int pointer = count * 100;
                raf.seek(pointer);

                String name = raf.readUTF();
                if (name.isEmpty())
                {
                    count++;
                    continue; // inside of loop if using "continue", stop this cycle of loop start from top
                }
                raf.seek(pointer + 50);
                int yPos = raf.readInt();
                raf.seek(pointer + 75);
                int xPos = raf.readInt();

                // take value and push into textfile
                textFields[yPos][xPos].setText(name);
                CheckForTable(name,xPos,yPos);
                //textFields[yPos][xPos].setBackground(Color.cyan);
                count++;
            }
            raf.close();
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
    // declaration of CheckForTable
    private void CheckForTable(String name, int x, int y)
    {
        if(StringToTableCheck(name)) // if string match, set background and foreground color cyan
        {
            textFields[y][x].setBackground(Color.cyan);
            textFields[y][x].setForeground(Color.cyan);
        }
    }

    // declaration of CheckTextFieldForTable method
    private void CheckTextFieldForTable( JTextField myTextField)
    {
        if(StringToTableCheck(myTextField.getText()))
        {
            myTextField.setBackground(Color.cyan);
            myTextField.setForeground(Color.cyan);
        }
    }

    // declaration of StringToTableCheck method to check text with table and bkfrnd fiil (ignore case)
    private boolean StringToTableCheck( String text )
    {
        if(text.equalsIgnoreCase("table")
                || text.equalsIgnoreCase("bkgrnd fill"))
        {
            return true;
        }
        return false;
    }

    //// declaration of HighlightCell method to highlight the cell with meganta color
    private void HighlightCell(String searchString)
    {
        for(int y = 0; y < 19; y++)
        {
            for( int x = 0; x < 10; x++)
            {
                JTextField textField = textFields[y][x];

                if(searchString.equalsIgnoreCase(textField.getText()) && textField.getText().isEmpty() == false)
                {
                    textField.setBackground(highlightColor);
                }
            }
        }
    }
}

