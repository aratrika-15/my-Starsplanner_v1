package stars;
import java.io.File;
import java.io.Serializable;
public class StudyGroup implements Serializable{

    private String venue;
    private int startTime;
    private int endTime;
    private int dayOfWeek;
    private String weekType;
    private int index;
    private LessonType lessonType;
    public StudyGroup()
    {

    }
    public StudyGroup(String venue, int startTime, int endTime, int dayOfWeek, String weekType, LessonType lessonType, int index) {
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.weekType = weekType;
        this.lessonType=lessonType;
        this.index = index;
    }
    public String getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }
    public int getStartTime() {
        return startTime;
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public int getEndTime() {
        return endTime;
    }
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    public int getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public String getWeekType() {
        return weekType;
    }
    public void setWeekType(String weekType) {
        this.weekType = weekType;
    }
    public LessonType getLessonType()
    {
        return lessonType;
    }
    public void setLessonType(LessonType lessonType)
    {
        this.lessonType=lessonType;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }


}