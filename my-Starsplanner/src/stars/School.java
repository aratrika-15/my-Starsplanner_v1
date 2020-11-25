package stars;
import java.io.File;
import java.util.*;
import java.io.Serializable;

/**
 * Represents a School in the system
 * contains myltiple Students and Courses
 */
public class School implements Serializable{
    /**
     * Name of this school
     */
    private String name;
    /**
     * Start date of access period of this school
     */
    private Date registrationStartPeriod = new Date();
    /**
     * End date of access period of this school
     */
    private Date registrationEndPeriod = new Date();
    /**
     * Array list of students in this school
     */
    private ArrayList<Student> students;
    /**
     * Array list of courses in this school
     */
    private ArrayList<Course> courses;

    /**
     * default constructor
     */
    public School(){}
    /**
     * Create School
     * @param name Name of new school
     * @param registrationStartPeriod Start date of access period of school
     * @param registrationEndPeriod End date of access period of school
     */
    public School(String name, Date registrationStartPeriod, Date registrationEndPeriod) {
        this.name = name;
        this.registrationStartPeriod = registrationStartPeriod;
        this.registrationEndPeriod = registrationEndPeriod;
        this.courses=null;
        this.students=null;
    }

    /**
     * Get name of this school
     * @return name of this school
     */
    public String getName() {
        return this.name;
    }

    /**
     * Change the name of this school
     * @param name new name of this school
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the start date of the access period of this school
     * @return start date of the access period of this school
     */
    public Date getRegistrationStartPeriod() {
        return this.registrationStartPeriod;
    }

    /**
     * Change the start date of the access period of this school
     * @param date new start date of the access period of this school
     */
    public void setRegistrationStartPeriod(Date date) {
        this.registrationStartPeriod = date;
    }
    /**
     * Get the end date of the access period of this school
     * @return end date of the access period of this school
     */
    public Date getRegistrationEndPeriod() {
        return this.registrationEndPeriod;
    }

    /**
     * Change the end date of the access period of this school
     * @param date new end date of the access period of this school
     */
    public void setRegistrationEndPeriod(Date date) {
        this.registrationEndPeriod = date;
    }

    /**
     * Change the array list of students in this school
     * @param students new array list of students in this school
     */
    public void setStudents(ArrayList<Student> students)
    {
        this.students=students;
    }
    /**
     * Get the array list of the students in this school
     * @return array list of students in this school
     */
    public ArrayList<Student> getStudents()
    {
        return students;
    }

    /**
     * Change the array list of courses in this school
     * @param courses new array list of courses in this school
     */
    public void setCourses(ArrayList<Course> courses)
    {
        this.courses=courses;
    }

    /**
     * Get the array list of the courses in this school
     * @return array list of courses in this school
     */
    public ArrayList<Course> getCourses()
    {
        return courses;
    }

    /**
     * Delete a course from this school
     * @param c course object that is going to be deleted
     */
    public void deleteCourse(Course c) {
        for (int i = 0 ; i < this.courses.size(); i ++){

            if(this.courses.get(i).getCourseCode().equals(c.getCourseCode())){
                this.courses.remove(i);
                System.out.println("Course successfully removed");
                break;
            }
        }
    }

    /**
     * Add a new student to the array list of students in this school
     * @param name name of new student
     * @param matricNo matriculation number of new student
     * @param gender gender of new student
     * @param nationality nationality of new student
     * @param year year of study of new student
     * @param school school name of new student
     * @param choice choice of notification of new student
     * @param recipient recipient address of new student
     * @param email email of new student
     * @param password password of new student
     * @param typeOfUser user type of new student
     * @param username username of new student
     * @return the new student object created
     */
    public Student addStudent(String name, String matricNo, String gender, String nationality, int year, String school, String choice, String recipient,String email, String password, String typeOfUser, String username)
    {
        Student student=new Student(name, matricNo, gender, nationality, year, school,choice,recipient, email, password, typeOfUser, username);
        this.students.add(student);
        System.out.println("Student successfully added!");
        return student;
    }

    /**
     * Add new course to the array list of courses in this school
     * @param name name of the course
     * @param courseCode course code of the course
     * @param courseType course type of the course
     * @param totalAUs total number of AUs of the course
     * @param nLectures number of lectures conducted in the course
     * @return the new course object created
     */
    public Course addCourse(String name, String courseCode, CourseType courseType, int totalAUs, int nLectures) {
        Course c = new Course(name, courseCode, name,courseType,totalAUs, nLectures);
        this.courses.add(c);
        System.out.println("Course successfully added!");
        return c;
    }

    /**
     * Check if current date and time of login is the access period of the school
     * @return boolean of whether it is the access period or not
     */
    public boolean checkWithinAccessPeriod()
    { //recheck the functionality of this function
        if(this.registrationEndPeriod!=null && this.registrationEndPeriod!=null)
        {
            Date currentDate=new Date();
            if (currentDate.compareTo(registrationStartPeriod) >= 0 && currentDate.compareTo(registrationEndPeriod) < 0)
                { //within access period
                    return true;
                }
            else
                { // not within the access period
                return false;
                }
        }
        else {
            System.out.println("Sorry, access period for the school has not been set ");
            return false;
        }
    }

}
