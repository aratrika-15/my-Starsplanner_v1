package stars;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controller class to display stored information for various entities and return a valid choice from user
 */
public class DisplayDataController {
    /**
     * FileController object
     */
    FileController fc = new FileController();
    /**
     * ValidateIntController object
     */
    ValidateIntController vc=new ValidateIntController();

    /**
     * default constructor
     */
    public DisplayDataController() {}

    /**
     * Retrieve list of schools from database and print it out for user to see and select.
     * @return School School user selected
     */
    public School schSelection() {
        int schSelection;
        boolean flag = true;

        ArrayList<School> schList = fc.getSchoolList();

        do {
            // Get user's selection of school

                System.out.println("Select the school:");
                for (int i = 0; i < schList.size(); i++) {
                    System.out.println(i + 1 + ". " + schList.get(i).getName());
                }

                schSelection=vc.validateInt(1,schList.size());

                if (schSelection > 0 && schSelection < schList.size() + 1) {
                    School school = schList.get(schSelection - 1);
                    flag = false;
                    return school;
                } else {
                    System.out.println("Invalid selection.");
                }


        } while(flag);


        return null;
    }

    /**
     * Retrieve list of courses from selected school and display for user to see and select.
     * Allows user to choose sorting method to sort by either number of reviews or by percentage recommended.
     * @param sch School user wants to select course from
     * @return Course Course user selected
     */
    public Course courseSelection(School sch) {
        int courseSelection; int select;
        boolean flag = true;

        ArrayList<Course> schCourses = sch.getCourses();
        flag = true;
        do {
            // Get user's selection of courses
            System.out.println("Select the course:");
            for(int i = 0; i < schCourses.size(); i++) {
                System.out.println(i+1 + ". " + schCourses.get(i).getName());
            }
            courseSelection = vc.validateInt(1,schCourses.size());

            if (courseSelection > 0 && courseSelection < schCourses.size() + 1) {
                Course course = schCourses.get(courseSelection - 1);
                flag = false;
                return course;
            }
            else {
                System.out.println("Invalid selection.");
            }
        } while(flag);
        return null;
    }

    /**
     * Retrieve list of indexes from selected course and display for user to see and select.
     * @param course Course user wants to select index from
     * @return Index Index selected by user
     */
    public Index indexSelection(Course course) {
        int indexSelection;
        boolean flag = true;
        ArrayList<Index> indexList = course.getIndex();

        do {
            System.out.println("Select the index:");
            for(int i = 0; i < indexList.size(); i++){
                System.out.println(i+1 + ". " + indexList.get(i).getIndexNum());
            }
            indexSelection = vc.validateInt(1,indexList.size());

            if(indexSelection> 0 && indexSelection < indexList.size()+1){
                Index index = indexList.get(indexSelection - 1);
                flag = false;
                return index;
            }
            else {
                System.out.println("Invalid selection.");
            }
        } while(flag);
        return null;
    }

    /**
     * Student to select a registered course object from their array list of registred course
     * @param s student who is making the selection
     * @return registered course object selected
     */
    public RegisteredCourse selectRegisteredCourses(Student s) {
        ArrayList<RegisteredCourse> allc = s.getRegCourses();
        ArrayList<RegisteredCourse> regCourses = new ArrayList<>();
        if (!allc.isEmpty()){
            for (RegisteredCourse rc : allc){
                if (rc.getRegStatus().equals("Registered")){
                    regCourses.add(rc);
                }
            }
        }
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        int choice;

        do {
            if (!regCourses.isEmpty()) {
                System.out.println("Select the course:");
                for(int i =0; i< regCourses.size(); i++) {
                        Index idx = fc.getIndexByID(regCourses.get(i).getRegIndex());
                        Course c = fc.getCourseByCode(idx.getCourse());
                        System.out.println(i + 1 + ". " + c.getCourseCode());
                }
            }
            else {
                System.out.println("Sorry! No Course Registered found for this Student");
                flag = false;
                break;
            }

            choice = vc.validateInt(1, regCourses.size());

            if(choice> 0 && choice < regCourses.size()+1){
                RegisteredCourse rc = regCourses.get(choice - 1);
                flag = false;
                return rc;
            }
            else {
                System.out.println("Invalid selection.");
            }
        }while(flag);

        return null;
    }

    /**
     * Print course list of all the courses
     */
    public void printCourseList() {
        ArrayList<School> schoolList = fc.getSchoolList();
        System.out.println("==========List of Courses==========\n");
        System.out.println("School\t\t\t\t\t     Course Code\t        Course Name");
        System.out.println("----------------------------------------------------------------------------------------");
        for (int i=0;i<schoolList.size();i++) {
            for (Course c : schoolList.get(i).getCourses()) {
                if (c.getCourseCode() != null) {
                    System.out.printf("%-50s",schoolList.get(i).getName());
                    System.out.printf("%-15s",c.getCourseCode());
                    System.out.printf("%-50s\n",c.getName());
                }
            }
        }


    }

    /**
     * Prints student list of all the students
     */
    public void printStudentList()
    {   ArrayList<Student> studentList = fc.getStudentList();
        System.out.println("");
        System.out.println("List of students");
        System.out.println(" Student Name\t\t   Matriculation Number\t      Gender \t  Nationality \t\t   School\t\t\t      Year of Study\t\t");
        System.out.println("_________________________________________________________________________________________________________________");
        for(int i=0;i<studentList.size();i++)
        {
            Student student=studentList.get(i);
            String column0Format = "%3d";
            String column1Format = "%-25s";
            String column2Format = "%-23s";
            String column3Format = "%-10s";
            String column4Format = "%-20s";
            String column5Format = "%-50s";
            String column6Format = "%-1d";
            String formatInfo = column0Format+ ". " + column1Format + " " + column2Format + " " + column3Format + " " + column4Format + " " + column5Format + " " + column6Format;
            System.out.format(formatInfo, (i+1), student.getName(), student.getMatricNumber(), student.getGender(), student.getNationality(), fc.getSchoolByName(student.getSchool()).getName(), student.getYear());
            System.out.println();
        }

    }
}