package stars;
import java.io.File;
import java.io.Serializable;

/**
 * Contains the lesson information of a particular lesson belonging to an index
 * can be of the types specified in enum LessonType
 * @see LessonType
 */
public class StudyGroup implements Serializable{

    /**
     * Venue where this study group is conducted
     */
    private String venue;
    /**
     * Start time of this study group
     */
    private int startTime;
    /**
     * End time of this study group
     */
    private int endTime;
    /**
     * Day of the week this study group is being conducted
     */
    private int dayOfWeek;
    /**
     * Week type of this study group
     */
    private String weekType;
    /**
     * Index this study group belongs to
     */
    private int index;
    /**
     * Lesson type of this study group
     */
    private LessonType lessonType;

    /**
     * default constructor
     */
    public StudyGroup()
    {

    }

    /**
     * Create a study group object
     * @param venue Venue where this study group is conducted
     * @param startTime Start time of this study group
     * @param endTime End time of this study group
     * @param dayOfWeek Day of the week this study group is being conducted
     * @param weekType Week type of this study group
     * @param lessonType Lesson type of this study group
     * @param index Index this study group belongs to
     */
    public StudyGroup(String venue, int startTime, int endTime, int dayOfWeek, String weekType, LessonType lessonType, int index) {
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.weekType = weekType;
        this.lessonType=lessonType;
        this.index = index;
    }

    /**
     * Get the venue where this study group is conducted
     * @return venue of this study group
     */
    public String getVenue() {
        return venue;
    }

    /**
     * Change the venue of where this study group is conducted
     * @param venue new venue of this study group
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     * Get the start time of this study group
     * @return start time of this study group
     */
    public int getStartTime() {
        return startTime;
    }
    /**
     * Change the start time of this study group
     * @param startTime new start time of this study group
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    /**
     * Get the end time of this study group
     * @return end time of this study group
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Change the end time of this study group
     * @param endTime new end time of this study group
     */
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    /**
     * Get Day of the week this study group is being conducted
     * @return Day of the week this study group is being conducted
     */
    public int getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Change the day of the week this study group is being conducted
     * @param dayOfWeek new day of the week this study group is being conducted
     */
    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Get the week type of this study group
     * @return week type of this study group
     */
    public String getWeekType() {
        return weekType;
    }

    /**
     * Change the week type of this study group
     * @param weekType new week type of this study group
     */
    public void setWeekType(String weekType) {
        this.weekType = weekType;
    }

    /**
     * Get the lesson type of this study group
     * @return lesson type of this study group
     */
    public LessonType getLessonType()
    {
        return lessonType;
    }

    /**
     * Change the lesson type of this study group
     * @param lessonType new lesson type of this study group
     */
    public void setLessonType(LessonType lessonType)
    {
        this.lessonType=lessonType;
    }

    /**
     * Get the index number this study group belongs to
     * @return index number this study group belongs to
     */
    public int getIndex() {
        return index;
    }

    /**
     * Change the index number this study group belongs to
     * @param index new index number this study group belongs to
     */
    public void setIndex(int index) {
        this.index = index;
    }


}