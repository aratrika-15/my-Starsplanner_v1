package stars;
import java.io.Serializable;
public class RegisteredCourse implements Serializable {
    private int regIndex;
    private String regStatus;
    private String student;
    public RegisteredCourse(int regIndex, String regStatus, String student) {
        this.regIndex = regIndex;
        this.regStatus = regStatus;
        this.student=student;

    }
    public int getRegIndex() {
        return regIndex;
    }
    public void setRegIndex(int regIndex) {
        this.regIndex = regIndex;
    }
    public String getRegStatus() {
        return regStatus;
    }
    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus;
    }
    public void setStudent(String student)
    {
        this.student=student;
    }
    public String getStudent()
    {
        return student;
    }


}
