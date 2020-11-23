package stars;
import java.util.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Student extends User implements Serializable{
    private String   name;
    private String   matricNumber;
    private String   gender;
    private String   nationality;
    private int    year;
    private int    numberOfAUs;
    private ArrayList<RegisteredCourse> regCourses ;
    private String school;
    private HashMap<String, String> notificationType;
    private ArrayList<Course> pastCourses;// new
    private ArrayList<Review> myReviews ;//new
    public static final  int MAX_AUs = 22;
    FileController fc = new FileController();
    public Student()
    {
        super();
    }

    public Student(String name, String matricNumber, String gender, String nationality, int year, int numberOFAUs,
                   ArrayList<RegisteredCourse> regCourses, String school, String choice, String recipient, String email, String password, String typeOfUser, String userName) {
        super(email, password, typeOfUser, userName);
        this.name = name;
        this.matricNumber = matricNumber;
        this.gender = gender;
        this.nationality = nationality;
        this.year = year;
        this.numberOfAUs = numberOFAUs;
        this.regCourses = regCourses;
        this.school = school;
        notificationType.put(choice, recipient);
        this.myReviews= new ArrayList<Review>();
        this.pastCourses=new ArrayList<Course>();
    }
    public Student(String name, String matricNumber, String gender, String nationality, int year, String school,String choice,String recipient, String email, String password,String typeOfUser, String userName)

    {
        super(email, password, typeOfUser, userName);
        this.name = name;
        this.matricNumber = matricNumber;
        this.gender = gender;
        this.nationality = nationality;
        this.year = year;
        this.school = school;
        this.numberOfAUs=0;
        this.regCourses=new ArrayList<RegisteredCourse>();
        this.notificationType=new HashMap<String, String>();
        this.notificationType.put(choice,recipient);
        this.myReviews= new ArrayList<Review>();
        this.pastCourses=new ArrayList<Course>();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatricNumber() {
        return matricNumber;
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumberOfAUs() {
        return numberOfAUs;
    }

    public void setNumberOfAUs(int numberOFAUs) {
        this.numberOfAUs = numberOFAUs;
    }

    public ArrayList<RegisteredCourse> getRegCourses() {
        return regCourses;
    }

    public void setRegCourses(ArrayList<RegisteredCourse> regCourses) {
        this.regCourses = regCourses;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    //adding a registered course for the student
    public void addRegCourses(RegisteredCourse course) {
        this.regCourses.add(course);
    }
    //removing a course which the student unregistered from
    public void removeRegCourses(RegisteredCourse course) {
        this.regCourses.remove(course);
    }

    public HashMap<String, String> getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(HashMap<String, String> notificationType) {
        this.notificationType = notificationType;
    }
    public ArrayList<Review> getMyReviews() {
        return myReviews;
    }

    public void addMyReview(Review review) {
        this.myReviews.add(review);
    }
    public void deleteReview(Review review) {
        for (int i = 0 ; i < this.myReviews.size(); i ++){

            if(this.myReviews.get(i).getReviewID().equals(review.getReviewID())){
                this.myReviews.remove(i);
                break;
            }
        }
    }

    public ArrayList<Course> getPastCourses() {
        return pastCourses;
    }

    public void setPastCourses(ArrayList<Course> pastCourses) {
        this.pastCourses = pastCourses;
    }
    /*
    Get the study groups for all courses for a student. (E.g. lectures, tutorials, etc)
     */
    public ArrayList<StudyGroup> getStudyGroups() {

        ArrayList<StudyGroup> studyGroups = new ArrayList<>();

        if (this.getRegCourses().isEmpty()==false ) {
            for (RegisteredCourse regCourse : this.regCourses) {
                //System.out.println("Hello");
                //System.out.println(regCourse.getRegIndex());
                Index idx = fc.getIndexByID(regCourse.getRegIndex());

                if (regCourse.getRegStatus() != "Waitlist") {
                    for (StudyGroup studyGroup : idx.getStudyGroup()) {
                        studyGroups.add(studyGroup);
                    }
                }
            }
            return studyGroups;
        }
        else {
            return null;
        }

    }

}
