package stars;
import java.util.*;
import java.io.Serializable;
public class Index implements Serializable {
    /**
     * Course this index belongs to
     */
    private String course;
    /**
     * index number of this index
     */
    private int indexNum;
    /**
     * Vacancies in this index
     */
    private int vacancies;
    /**
     * Total number of slots in this index
     */
    private int totalSlots;
    /**
     * Group number of this index
     */
    private String groupNum;
    /**
     * Queue of students in wait list of this index
     */
    private Queue<Student> waitList;
    /**
     * Array list of registred course of this index
     */
    private ArrayList<RegisteredCourse> regList;
    /**
     * Array list of study groups of this index
     */
    private ArrayList<StudyGroup> studyGroup;
    public Index()
    {

    }

    /**
     * Create a new index object
     * @param indexNum index number of new index
     * @param groupNum group number of new index
     * @param totalSlots total slots of new index
     * @param course course this new index belongs to
     */
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

    /**
     * Change the index number of this index
     * @param indexNum new index number
     */
    public void setIndexNum(int indexNum)
    {
        this.indexNum=indexNum;
    }

    /**
     * Get the index number of this index
     * @return index number of this index
     */
    public int getIndexNum()
    {
        return this.indexNum;
    }

    /**
     * Change the course this index belongs to
     * @param course new course this index belongs to
     */
    public void setCourse(String course)
    {
        this.course=course;
    }

    /**
     * Get the course this index belongs to
     * @return course this index belongs to
     */
    public String getCourse()
    {
        return this.course;
    }

    /**
     * Change the number of vacancies of this index
     * @param vacancies new number of vacancies of this index
     */
    public void setVacancies(int vacancies)
    {
        int diff = vacancies-this.vacancies;
        this.vacancies=vacancies;
    }

    /**
     * Get the number of vacancies for this index
     * @return number of vacancies for this index
     */
    public int getVacancies()
    {
        return this.vacancies;
    }

    /**
     * Change the group number of this index
     * @param groupNum new group number of this index
     */
    public void setGroupNum(String groupNum)
    {
        this.groupNum=groupNum;
    }

    /**
     * get the group number of this index
     * @return group number of this index
     */
    public String getGroupNum()
    {
        return this.groupNum;
    }

    /**
     * Get the queue list of students in the wait list
     * @return queue list of students in the wait list
     */
    public Queue<Student> getWaitList()
    {
        return this.waitList;
    }

    /**
     * function to add student into the queue list of students in the wait list
     * @param student student to be added into the queue list
     */
    public void addToWaitList(Student student)
    {
        waitList.add(student);
    }

    /**
     * Change the array list of registered course of this index
     * @param regList new array list of registered course of this index
     */
    public void setRegisteredCourses(ArrayList<RegisteredCourse> regList)
    {
        this.regList=regList;
    }

    /**
     * Get the array list of registered course of this index
     * @return array list of registered course of this index
     */
    public ArrayList<RegisteredCourse> getRegisteredCourses()
    {
        return regList;
    }

    /**
     * Change the array list of study groups for this index
     * @param studyGroup new array list of study groups for this index
     */
    public void setStudyGroup(ArrayList<StudyGroup> studyGroup)
    {
     this.studyGroup=studyGroup;
    }

    /**
     * Get array list of study groups for this index
     * @return array list of study groups for this index
     */
    public ArrayList<StudyGroup> getStudyGroup()
    {
        return studyGroup;
    }

    /**
     * Get the total number of slots for this index
     * @return total number of slots for this index
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * Change the total number of slots for this index
     * @param totalSlots new total number of slots for this index
     */
    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    /**
     * Change the queue list of students in the wait list
     * @param waitList new queue list of students in the wait list
     */
    public void setWaitList(Queue<Student> waitList) {
        this.waitList = waitList;
    }

    /**
     * Method to add a registered course into the array list of registered course
     * @param regCourse registred course to be added into the array list
     */
    public void addToRegList(RegisteredCourse regCourse)
    {
        this.regList.add(regCourse);
    }

    /**
     * Remove a registered course from the array list of registered course
     * @param regCourse registered course to be removed the array list of registered course
     */
    public void removeFromRegList(RegisteredCourse regCourse){
        this.regList.remove(regCourse);
    }

    /**
     * Add a new study group to this index
     * @param venue venue of new study group
     * @param startTime start time of new study group
     * @param endTime end time of new study group
     * @param dayOfWeek day of week of new study group
     * @param weekType week type of new study group
     * @param lessonType lesson type of new study group
     */
    public void addStudyGroup(String venue, int startTime, int endTime, int dayOfWeek, String weekType,LessonType lessonType) {
        StudyGroup sg = new StudyGroup(venue,startTime,endTime,dayOfWeek,weekType,lessonType, indexNum);
        this.studyGroup.add(sg);

    }

    /**
     * Remove a study group from this index
     * @param l lesson type of study group to be removed
     */
    public void deleteStudyGroup(LessonType l) {
        Iterator<StudyGroup> iter = this.studyGroup.iterator();

        while (iter.hasNext()) {
            StudyGroup sg = iter.next();

            if (sg.getLessonType().equals(l))
                iter.remove();
        }
    }





}