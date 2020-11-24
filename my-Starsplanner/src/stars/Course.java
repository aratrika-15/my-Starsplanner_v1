package stars;
import java.util.*;
import java.io.Serializable;
public class Course implements Serializable {
    /**
     * Name of this course
     */
    private String name;
    /**
     * Course code of this course
     */
    private String courseCode;
    /**
     * School this course belongs to
     */
    private String school;
    /**
     * Indexes of this course stored in an array list
     */
    private ArrayList<Index> index = new ArrayList<Index>();
    /**
     * course type of this course
     */
    private CourseType courseType;
    /**
     * vacancies in this course
     */
    private int vacancy;
    /**
     * total number of AUs for this course
     */
    private int totalAUs;
    /**
     * Number of lectures conducted in this course
     */
    private int nLectures;
    /**
     * percentage of recommendation for this course
     */
    private double percRecommended;//new
    /**
     * total reviews for this course
     */
    private int totalReviews;//new
    /**
     * all the reviews for this course stored in an array list
     */
    private ArrayList<Review> reviews = new ArrayList<Review>();//new
    public Course()
    {

    }
    /**
     * Create a new course with the following details
     * @param name Name of this course
     * @param courseCode Course code of this course
     * @param school Which school this course belongs to
     * @param courseType Course type of this course
     * @param vacancy Number of vacancies in this course
     * @param totalAUs Number of total AUs for this course
     * @param nLectures Number of lectures conducted in this course
     */
    public Course(String name, String courseCode, String school, CourseType courseType,int vacancy, int totalAUs, int nLectures)
    {	//need to do object creation for ArrayList index here maybe
        //if school is a composition of course, can we add a school object inside course?
        this.name=name;
        this.courseCode=courseCode;
        this.school=school;
        this.courseType=courseType;
        this.vacancy=vacancy;
        this.totalAUs=totalAUs;
        this.nLectures=nLectures;

    }
    public Course(String name, String courseCode, String school, CourseType courseType, int totalAUs, int nLectures)
    {	//need to do object creation for ArrayList index here maybe
        //if school is a composition of course, can we add a school object inside course?
        this.name=name;
        this.courseCode=courseCode;
        this.school=school;
        this.courseType=courseType;
        this.vacancy=0;
        this.totalAUs=totalAUs;
        this.nLectures = nLectures;

    }
    /**
     * Chnage the name of this course
     * @param name New name of this course
     */
    public void setName(String name)
    {
        this.name=name;
    }
    /**
     * Get the name of this course
     * @return name of this course
     */
    public String getName()
    {
        return this.name;
    }
    /**
     * Change the course code of this course
     * @param courseCode new course code of this course
     */
    public void setCourseCode(String courseCode)
    {
        this.courseCode=courseCode;
    }
    /**
     * Get the course code of this course
     * @return course code of this course
     */
    public String getCourseCode()
    {
        return this.courseCode;
    }
    /**
     * Change the course type of this course
     * @param courseType new course type of this course
     */
    public void setCourseType(CourseType courseType)
    {
        this.courseType=courseType;
    }
    /**
     * Get course type of this course
     * @return course type of this course
     */
    public CourseType getCourseType()
    {
        return this.courseType;
    }
    /**
     * Get number of lectures conducted in this course
     * @return number of lectures conducted in this course
     */
    public int getnLectures() {
        return nLectures;
    }
    /**
     * Change the number of lectures conducted in this course
     * @param nLectures new number of lectures to be conducted in this course
     */
    public void setnLectures(int nLectures) {
        this.nLectures = nLectures;
    }
    /**
     * Change the vacancy in this course
     * @param vacancy new number of vacancy in this course
     */
    public void setVacancy(int vacancy)
    {
        this.vacancy=vacancy;
    }
    /**
     * Get the number of vacancy in this course
     * @return number of vacancy in this course
     */
    public int getVacancy()
    {
        return this.vacancy;
    }
    /**
     * Change the number of total AUs in this course
     * @param totalAUs new total AUs in this course
     */
    public void setTotalAUs(int totalAUs)
    {
        this.totalAUs=totalAUs;
    }
    /**
     * Get the number of total AUs in this course
     * @return total AUs in this course
     */
    public int getTotalAUs()
    {
        return this.totalAUs;
    }
    /**
     * Change the school this course belongs to
     * @param school new school this course belongs to
     */
    public void setSchool(String school)
    {
        this.school=school;
    }
    /**
     * Get the school this course belongs to
     * @return school this course belongs to
     */
    public String getSchool()
    {
        return this.school;
    }

    /**
     * Add new index to the array list of this course
     * @param indexNum Index number of index to be added
     * @param groupNum Group number of index to be added
     * @param vacancies Vacancies of index to be added
     * @return new index added to this course
     */
    public Index setIndex(int indexNum, String groupNum, int vacancies)
    {
        Index i = new Index(indexNum, groupNum, vacancies, this.courseCode);
        this.index.add(i);
        this.editVacancies(vacancies);
        return i;
    }
    /**
     * Change the array list of indexes for this course
     * @param index new array list of index for this course
     */
    public void setIndexList(ArrayList<Index> index) {
        this.index = index;
    }
    /**
     * Remove index from the array list of index in this course
     * @param indID index to be removed from the array list
     */
    public void deleteIndex(Index indID) {
        for (int i = 0 ; i < this.index.size(); i ++){

            if(this.index.get(i).getIndexNum() == indID.getIndexNum()){
                this.index.remove(i);
                System.out.println("Index successfully removed");
                break;
            }
        }
    }
    /**
     * Change the vacancies of this course by n
     * @param n n number of vacancies to be added or removed
     */
    public void editVacancies(int n) {
        this.vacancy = this.vacancy + n;
    }
    /**
     * Get the array list of index of this course
     * @return array list of index of this course
     */
    public ArrayList<Index> getIndex()
    {
        return this.index;
    }

    /**
     * Get the percentage recommendation of this course
     * @return double of the percentage recommendation of this course
     */
    public double getPercRecommended() {
        return percRecommended;
    }
    /**
     * Change the percentage recommendation of this course
     * @param percRecommended new double percentage recommendation of this course
     */
    public void setPercRecommended(double percRecommended) {
        this.percRecommended = percRecommended;
    }
    /**
     * Get the total reviews of this course
     * @return number of total reviews of this course
     */
    public int getTotalReviews() {
        return totalReviews;
    }
    /**
     * Change the number of total reviews for this course
     * @param totalReviews new total reviews for this course
     */
    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }
    /**
     * Delete a review from the array list of review of this course
     * @param rev review to be deleted from this course
     */
    public void deleteReview(Review rev) {
        for (int i = 0 ; i < this.reviews.size(); i ++){
            if(this.reviews.get(i).getReviewID().equals(rev.getReviewID())){
                this.reviews.remove(i);
                double positive = this.percRecommended * this.totalReviews;
                if (rev.isRecommended()) {positive = positive - 1;}
                this.totalReviews = this.totalReviews - 1;
                this.percRecommended = positive/(double)this.totalReviews;
                System.out.println("Review successfully removed");
                break;
            }
        }
    }
    /**
     * Get the array list of reviews of this course
     * @return array list of reviews of this course
     */
    public ArrayList<Review> getReviews() {
        return reviews;
    }
    /**
     * Change the array list of reviews of this course
     * @param reviews new array list of reviews of this course
     */
    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
    /**
     * Add a new review to the array list of reviews of this course
     * @param r new review to be added to the array list of reviews of this course
     */
    public void addReview(Review r) {
        this.reviews.add(r);
        double positive = this.percRecommended * this.totalReviews;
        if (r.isRecommended()) {positive = positive + 1;}
        this.totalReviews = this.totalReviews + 1;
        this.percRecommended = positive/(double)this.totalReviews;
    }



}