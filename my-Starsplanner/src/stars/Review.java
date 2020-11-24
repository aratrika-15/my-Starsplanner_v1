package stars;

import java.io.Serializable;

public class Review implements Serializable {
    private String review;
    private boolean recommended;
    private String student;
    private String course;
    private String reviewID;
    private Course theCourse;
    private Student stu;


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
    public Review(String review, boolean recommended, String courseCode) {
        FileController fc= new FileController();
        this.review = review;
        this.recommended = recommended;
        this.course = courseCode;
        this.reviewID = "NA";
        theCourse= fc.getCourseByCode(courseCode);
        theCourse.addReview(this);

    }
    public String getReviewID() {
        return reviewID;
    }
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        FileController fc= new FileController();
        Course theCourse = fc.getCourseByCode(this.course);
        if (recommended  && !this.recommended) {
            theCourse.setPercRecommended((theCourse.getPercRecommended() * theCourse.getTotalReviews() + 1)/(double) theCourse.getTotalReviews());
        }
        this.recommended = recommended;

    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

}

