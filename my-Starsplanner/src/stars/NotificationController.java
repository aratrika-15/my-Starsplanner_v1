package stars;
import java.util.HashMap;

public class NotificationController {
    /**
     * default constructor
     */
    public NotificationController() {
    }

    /**
     * Send notification to next student in the waitlist based on his/her prefer communication method.
     *
     * @param recipient, name, indexNum, courseCode, keyMethod
     */
    public void notify(String recipient, String name, int indexNum, String courseCode, String keyMethod) {
        //FileController fc = new FileController();
        NotificationFactory notifType = new NotificationFactory();
        //HashMap<String, String> choice = student.getNotificationType();
        //String keyMethod = choice.keySet().stream().findFirst().get();
        //String recipient = choice.get(keyMethod);
        Notifications preferredNotifMethod = notifType.getNotifObj(keyMethod);
        //String name = student.getName();
        //int indexNum = index.getIndexNum();
        //String courseCode = fc.getCourseByCode(index.getCourse()).getCourseCode();
        preferredNotifMethod.sendNotification(recipient, name, indexNum, courseCode);
    }
}