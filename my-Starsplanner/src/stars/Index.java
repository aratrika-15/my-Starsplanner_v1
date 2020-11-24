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
    public Index()
    {

    }
    public Index(int indexNum, String groupNum, int totalSlots, String course)
    {	this.course=course;
        this.indexNum=indexNum;
        this.groupNum=groupNum;
        this.vacancies=totalSlots;
        this.totalSlots=totalSlots;
        this.waitList=new ArrayDeque<>();
        this.studyGroup=new ArrayList<StudyGroup>();
        this.regList=new ArrayList<RegisteredCourse>();
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

    public void addToRegList(RegisteredCourse regCourse)
    {
        this.regList.add(regCourse);
    }
    public void removeFromRegList(RegisteredCourse regCourse){
        this.regList.remove(regCourse);
    }
    public void addStudyGroup(String venue, int startTime, int endTime, int dayOfWeek, String weekType,LessonType lessonType) {
        StudyGroup sg = new StudyGroup(venue,startTime,endTime,dayOfWeek,weekType,lessonType, indexNum);
        this.studyGroup.add(sg);

    }

    public void deleteStudyGroup(LessonType l) {
        Iterator<StudyGroup> iter = this.studyGroup.iterator();

        while (iter.hasNext()) {
            StudyGroup sg = iter.next();

            if (sg.getLessonType().equals(l))
                iter.remove();
        }
    }





}