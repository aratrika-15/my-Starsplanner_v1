package stars;
import java.util.*;
import java.io.Serializable;
public class Course implements Serializable {
    private String name;
    private String courseCode;
    private String school;
    private ArrayList<Index> index = new ArrayList<Index>();
    private CourseType courseType;
    private int vacancy;
    private int totalAUs;
    private int nLectures;
    private double percRecommended;//new
    private int totalReviews;//new
    private ArrayList<Review> reviews = new ArrayList<Review>();//new
    FileController fc = new FileController();
    public Course()
    {

    }
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
    public void setName(String name)
    {
        this.name=name;
    }
    public String getName()
    {
        return this.name;
    }
    public void setCourseCode(String courseCode)
    {
        this.courseCode=courseCode;
    }
    public String getCourseCode()
    {
        return this.courseCode;
    }
    public void setCourseType(CourseType courseType)
    {
        this.courseType=courseType;
    }
    public CourseType getCourseType()
    {
        return this.courseType;
    }
    public int getnLectures() {
        return nLectures;
    }
    public void setnLectures(int nLectures) {
        this.nLectures = nLectures;
    }
    public void setVacancy(int vacancy)
    {
        this.vacancy=vacancy;
    }
    public int getVacancy()
    {
        return this.vacancy;
    }
    public void setTotalAUs(int totalAUs)
    {
        this.totalAUs=totalAUs;
    }
    public int getTotalAUs()
    {
        return this.totalAUs;
    }
    public void setSchool(String school)
    {
        this.school=school;
    }
    public String getSchool()
    {
        return this.school;
    }

    public Index setIndex(int indexNum, String groupNum, int vacancies)
    {
        Index i = new Index(indexNum, groupNum, vacancies, this.courseCode);
        this.index.add(i);
        this.editVacancies(vacancies);
        return i;
    }
    public void setIndexList(ArrayList<Index> index) {
        this.index = index;
    }
    public void deleteIndex(Index indID) {
        for (int i = 0 ; i < this.index.size(); i ++){

            if(this.index.get(i).getIndexNum() == indID.getIndexNum()){
                this.index.remove(i);
                System.out.println("Index successfully removed");
                break;
            }
        }
    }
    //allows increase or decrease of number of vacancies by n
    public void editVacancies(int n) {
        this.vacancy = this.vacancy + n;
    }
    public ArrayList<Index> getIndex()
    {
        return this.index;
    }
    public double getPercRecommended() {
        return percRecommended;
    }
    public void setPercRecommended(double percRecommended) {
        this.percRecommended = percRecommended;
    }
    public int getTotalReviews() {
        return totalReviews;
    }
    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }
    public void deleteReview(Review rev) {
        for (int i = 0 ; i < this.reviews.size(); i ++){

            if(this.reviews.get(i).equals(rev)){
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
    public ArrayList<Review> getReviews() {
        return reviews;
    }
    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
    public void addReview(Review r) {
        this.reviews.add(r);
        double positive = this.percRecommended * this.totalReviews;
        if (r.isRecommended()) {positive = positive + 1;}
        this.totalReviews = this.totalReviews + 1;
        this.percRecommended = positive/(double)this.totalReviews;
    }

}