package stars;
import java.util.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a Student in the system
 */
public class Student extends User implements Serializable{
    /**
     * name of this student
     */
    private String   name;
    /**
     * matricNumber of this student
     */
    private String   matricNumber;
    /**
     * gender of this student
     */
    private String   gender;
    /**
     * nationality of this student
     */
    private String   nationality;
    /**
     * year of study of this student
     */
    private int    year;
    /**
     * number of AUs this student is taking
     */
    private int    numberOfAUs;
    /**
     * array list of all the courses this student is registered in
     */
    private ArrayList<RegisteredCourse> regCourses = new ArrayList<RegisteredCourse>();
    /**
     * School name of the school this student belongs to
     */
    private String school;
    /**
     * hash map for the notification type of this student
     */
    private HashMap<String, String> notificationType=new HashMap<String, String>();
    /**
     * array list of the past courses this student had taken
     */
    private ArrayList<Course> pastCourses;// new
    /**
     * array list of all the reviews this student had writen
     */
    private ArrayList<Review> myReviews = new ArrayList<Review>();//new
    /**
     * maximum number of AUs this student can take
     */
    public static final  int MAX_AUs = 21;
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

    /**
     * Create student object
     * @param name name of new student
     * @param matricNumber matriculation number of new student
     * @param gender gender of new student
     * @param nationality nationality of new student
     * @param year year of study of new student
     * @param school school name this new student belongs to
     * @param choice notification choice of new student
     * @param recipient recipient address of new student
     * @param email email of new student
     * @param password actual password of new student
     * @param typeOfUser  user type of new student
     * @param userName username of new student
     */
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
    /**
     * Get the name of this student
     * @return name of this student
     */
    public String getName() {
        return name;
    }

    /**
     * Change the name of this student
     * @param name new name of this student
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the matriculation number of this student
     * @return matriculation number of this student
     */
    public String getMatricNumber() {
        return matricNumber;
    }
    /**
     * Change the matriculation number of this student
     * @param matricNumber new matriculation number of this student
     */
    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    /**
     * Get the gender of this student
     * @return gender of this student
     */
    public String getGender() {
        return gender;
    }

    /**
     * Change the gender of this student
     * @param gender new gender of this student
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    /**
     * Get the nationality of this student
     * @return nationality of this student
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Change the nationality of this student
     * @param nationality new nationality of this student
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Get the year of study of this student
     * @return the year of study of this student
     */
    public int getYear() {
        return year;
    }
    /**
     * Change the year of study of this student
     * @param year new year of study of this student
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Get the number of AUs of this student
     * @return number of AUs of this student
     */
    public int getNumberOfAUs() {
        return numberOfAUs;
    }

    /**
     * Change number of AUs of this student
     * @param numberOFAUs new number of AUs of this student
     */
    public void setNumberOfAUs(int numberOFAUs) {
        this.numberOfAUs = numberOFAUs;
    }
    /**
     * Get the array list of courses this student is registered in
     * @return array list of courses this student is registered in
     */
    public ArrayList<RegisteredCourse> getRegCourses() {
        return regCourses;
    }

    /**
     * Change the array list of courses this student is registered in
     * @param regCourses new array list of courses this student is registered in
     */
    public void setRegCourses(ArrayList<RegisteredCourse> regCourses) {
        this.regCourses = regCourses;
    }

    /**
     * Get the school name of the school this student belongs to
     * @return school name of the school this student belongs to
     */
    public String getSchool() {
        return school;
    }

    /**
     * Change the school name of the school this student belongs to
     * @param school new school name of the school this student belongs to
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * Add a course to the array list of course this student is registered in
     * @param course new course this student is registered in
     */
    public void addRegCourses(RegisteredCourse course) {
        this.regCourses.add(course);
    }

    /**
     * Remove a course from the array list of course this student is registered in
     * @param course course to remove from the array list
     */
    public void removeRegCourses(RegisteredCourse course) {
        this.regCourses.remove(course);
    }
    /**
     * Get the notification type of this student
     * @return notification type of this student
     */
    public HashMap<String, String> getNotificationType() {
        return notificationType;
    }

    /**
     * Change the notification type of this student
     * @param notificationType new notification type of this student
     */
    public void setNotificationType(HashMap<String, String> notificationType) {
        this.notificationType = notificationType;
    }

    /**
     * Get the array list of reviews written by this student
     * @return array list of reviews written by this student
     */
    public ArrayList<Review> getMyReviews() {
        return myReviews;
    }
    /**
     * Add a new review to the array list of reviews written by this student
     * @param review new review written by this student
     */
    public void addMyReview(Review review) {
        this.myReviews.add(review);
    }
    /**
     * Remove a review from the array list of reviews written by this student
     * @param review review to be removed
     */
    public void deleteReview(Review review) {
        for (int i = 0 ; i < this.myReviews.size(); i ++){

            if(this.myReviews.get(i).getReviewID().equals(review.getReviewID())){
                this.myReviews.remove(i);
                break;
            }
        }
    }

    /**
     * Get the array list of past courses of this student
     * @return array list of past courses of this student
     */
    public ArrayList<Course> getPastCourses() {
        return pastCourses;
    }

    /**
     * Change the array list of past courses of this student
     * @param pastCourses new array list of past courses of this student
     */
    public void setPastCourses(ArrayList<Course> pastCourses) {
        this.pastCourses = pastCourses;
    }

    /**
     * Get the array list of all the study groups of all the registered course of this student
     * @return array list of all the study groups
     */


}
