package stars;
import java.io.Serializable;
public class RegisteredCourse implements Serializable {
    /**
     * Index of this registered course
     */
    private int regIndex;
    /**
     * Registration status of this course registered for
     */
    private String regStatus;
    /**
     * Student name this registered course belongs to
     */
    private String student;

    /**
     * Create a new registered course object
     * @param regIndex Index of new registered course
     * @param regStatus status of new registered course
     * @param student student who registered for course
     */
    public RegisteredCourse(int regIndex, String regStatus, String student) {
        this.regIndex = regIndex;
        this.regStatus = regStatus;
        this.student=student;

    }

    /**
     * Get the index of this registered course
     * @return index of this registered course
     */
    public int getRegIndex() {
        return regIndex;
    }

    /**
     * Change the index of this registered course
     * @param regIndex new index of this registered course
     */
    public void setRegIndex(int regIndex) {
        this.regIndex = regIndex;
    }

    /**
     * Get registration status of this registered course
     * @return registration status of this registered course
     */
    public String getRegStatus() {
        return regStatus;
    }

    /**
     * Change the registration status of this registered course
     * @param regStatus new registration status of this registered course
     */
    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus;
    }

    /**
     * Change the student name this registered course belongs to
     * @param student new student name this registered course belongs to
     */
    public void setStudent(String student)
    {
        this.student=student;
    }

    /**
     * Get student name this registered course belongs to
     * @return student name this registered course belongs to
     */
    public String getStudent()
    {
        return student;
    }


}
