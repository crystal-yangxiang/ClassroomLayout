package caroline_Classroom;


public class Student implements Comparable
{
    private String studentName;//Declaration of global variable student name (string)
    private int xPos;
    private int yPos;


    //  constructor for the class: student.
    public  Student(String name, int y,int x)
    {
        //When a student is instantiated and default entries for
        // the 3 properties - name, x position and y position- are
        // provided by the calling class, this constructor will run
        studentName = name;
        xPos=x;
        yPos=y;
    }

        //Purpose: A method that will allow this student class
        //to provide the calling class with the name, x position and y position data.
        //This method would allow this class to manage outgoing name, x position and y position
        //param   None.
        //returns student name (String), x position(int)and y position (int)
    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public int compareTo(Object o)
    {
        if (o.getClass() == Student.class)//Compare student name is match or not
            // if match and return student name
        // if not match returns nothing (void).
        {
            Student student = (Student)o;
            return this.studentName.compareToIgnoreCase(student.studentName);
        }
        return this.studentName.compareToIgnoreCase(o.toString());

    }

}
