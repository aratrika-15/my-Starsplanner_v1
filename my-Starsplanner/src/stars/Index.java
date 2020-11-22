package stars;
import java.util.*;
import java.io.Serializable;
public class Index implements Serializable {
    private String course;
    private int indexNum;
    private int vacancies;
    private int totalSlots;
    private String groupNum;
    private Queue<Student> waitList;
    private ArrayList<RegisteredCourse> regList;
    private ArrayList<StudyGroup> studyGroup;
    FileController fc = new FileController();
    public Index()
    {

    }
    public Index(int indexNum, String groupNum, int totalSlots, String course)
    {	this.course=course;
        this.indexNum=indexNum;
        this.groupNum=groupNum;
        this.vacancies=totalSlots;
        this.totalSlots=totalSlots;

    }
    public void setIndexNum(int indexNum)
    {
        this.indexNum=indexNum;
    }
    public int getIndexNum()
    {
        return this.indexNum;
    }
    public void setCourse(String course)
    {
        this.course=course;
    }
    public String getCourse()
    {
        return this.course;
    }
    public void setVacancies(int vacancies)
    {
        int diff = vacancies-this.vacancies;
        Course course = fc.getCourseByCode(this.course);
        course.editVacancies(diff);//changing the course vacancies accordingly
        this.vacancies=vacancies;
    }
    public int getVacancies()
    {
        return this.vacancies;
    }
    public void setGroupNum(String groupNum)
    {
        this.groupNum=groupNum;
    }
    public String getGroupNum()
    {
        return this.groupNum;
    }
    public Queue<Student> getWaitList()
    {
        return this.waitList;
    }

    /**
     * function to add student into a waitlist
     * @param student
     */
    public void addToWaitList(Student student)
    {
        waitList.add(student);
    }
    public void setRegisteredCourses(ArrayList<RegisteredCourse> regList)
    {
        this.regList=regList;
    }
    public ArrayList<RegisteredCourse> getRegisteredCourses()
    {
        return regList;
    }
    public void setStudyGroup(ArrayList<StudyGroup> studyGroup)
    {
     this.studyGroup=studyGroup;
    }
    public ArrayList<StudyGroup> getStudyGroup()
    {
        return studyGroup;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    public void setWaitList(Queue<Student> waitList) {
        this.waitList = waitList;
    }

    public ArrayList<RegisteredCourse> getRegList() {
        return regList;
    }

    public void setRegList(ArrayList<RegisteredCourse> regList) {
        this.regList = regList;
    }


    public void addStudyGroup(String venue, int startTime, int endTime, int dayOfWeek, String weekType,LessonType lessonType) {
        StudyGroup sg = new StudyGroup(venue,startTime,endTime,dayOfWeek,weekType,lessonType, indexNum);
        this.studyGroup.add(sg);

    }

    public void deleteStudyGroup(LessonType l) {
        for (StudyGroup sg : this.studyGroup) {
            if (sg.getLessonType().equals(l)) {
                this.studyGroup.remove(sg);
            }
        }
    }


    public void allocateVacancies(Course course, Index index) {

        Student student;
        Queue<Student> waitList = index.getWaitList();
        int vacancy;

        for (vacancy = index.getVacancies(); vacancy > 0; vacancy--) {
            student = waitList.poll();

            if (student != null) {

                for(RegisteredCourse registeredCourse : student.getRegCourses()) {
                    Index idx = fc.getIndexByID(registeredCourse.getRegIndex());
                    if(idx.getCourse().equals(course)) {
                        if (student.getNumberOfAUs() + course.getTotalAUs() > 21) {
                            student.removeRegCourses(registeredCourse);
                            index.regList.remove(registeredCourse);
                            vacancy++;
                        } else {
                            student.setNumberOfAUs(student.getNumberOfAUs() + course.getTotalAUs());
                            registeredCourse.setRegStatus("Registered");
                            // set timetable
                            //
                        }
                    }
                    //set new timetable schedule
                }
            } else {
                setVacancies(vacancy);
                break;
            }
        }
        setVacancies(vacancy);
    }

}