package stars;

import java.io.Serializable;

public class Review implements Serializable {
    private String review;
    private boolean recommended;
    private String student;
    private String course;
    FileController fc= new FileController();

    public Review(String review, boolean recommended, String username, String courseCode) {
        this.review = review;
        this.recommended = recommended;
        this.student = username;
        this.course = courseCode;
        Course theCourse= fc.getCourseByCode(courseCode);
        Student stu = fc.getStudentByUsername(username);
        theCourse.addReview(this);
        stu.addMyReview(this);

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

