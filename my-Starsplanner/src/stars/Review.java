package stars;

import java.io.Serializable;

/**
 * Association class to store a review written by Student for a particular Course
 */
public class Review implements Serializable {
    /**
     * String review of this review
     */
    private String review;
    /**
     * Boolean of whether is review recommends or not
     */
    private boolean recommended;
    /**
     * Student who wrote this review
     */
    private String student;
    /**
     * Course of this review
     */
    private String course;
    /**
     * Unique ID to identify this review
     */
    private String reviewID;
    /**
     * Course this review is reviewing
     */
    private Course theCourse;
    /**
     * Student who gave this review
     */
    private Student stu;
    /**
     * Create a review
     * @param review String review of this review
     * @param recommended Boolean of whether is review recommends or not
     * @param username Student who wrote this review
     * @param courseCode Course of this review
     */
    public Review(String review, boolean recommended, String username, String courseCode) {
        FileController fc= new FileController();
        this.review = review;
        this.recommended = recommended;
        this.student = username;
        this.course = courseCode;
        this.reviewID = username+courseCode;
        theCourse= fc.getCourseByCode(courseCode);
        stu = fc.getStudentByUsername(username);
        theCourse.addReview(this);
        stu.addMyReview(this);

    }

    /**
     *
     * @param review String review of this review
     * @param recommended Boolean of whether is review recommends or not
     * @param courseCode Course of this review
     */
    public Review(String review, boolean recommended, String courseCode) {
        FileController fc= new FileController();
        this.review = review;
        this.recommended = recommended;
        this.course = courseCode;
        this.reviewID = "NA";
        theCourse= fc.getCourseByCode(courseCode);
        theCourse.addReview(this);

    }

    /**
     * Get unique ID of this review
     * @return unique ID of this review
     */
    public String getReviewID() {
        return reviewID;
    }
    /**
     * Get the string review of this review
     * @return string review of this review
     */
    public String getReview() {
        return review;
    }

    /**
     * Change the review of this review
     * @param review new string review of this review
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Get the recommendation of this review
     * @return recommendation
     */
    public boolean isRecommended() {
        return recommended;
    }

    /**
     * Change the recommendation of this review
     * @param recommended new recommendation of this review
     */
    public void setRecommended(boolean recommended) {
        FileController fc= new FileController();
        Course theCourse = fc.getCourseByCode(this.course);
        if (recommended  && !this.recommended) {
            theCourse.setPercRecommended((theCourse.getPercRecommended() * theCourse.getTotalReviews() + 1)/(double) theCourse.getTotalReviews());
        }
        this.recommended = recommended;

    }

    /**
     * Get the student who did this review
     * @return student who did this review
     */
    public String getStudent() {
        return student;
    }

    /**
     * Change the student who did this review
     * @param student new student who did this review
     */
    public void setStudent(String student) {
        this.student = student;
    }

    /**
     * Get the course of this reviewed
     * @return course that is reviewed
     */
    public String getCourse() {
        return course;
    }

    /**
     * Change the course of this review
     * @param course new course of this review
     */
    public void setCourse(String course) {
        this.course = course;
    }

}

