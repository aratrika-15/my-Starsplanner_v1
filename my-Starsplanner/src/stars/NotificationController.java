package stars;

/**
 * controls the logic to create a relavant object based on students notification preferance and send the notification accordingly
 */
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
        NotificationFactory notifType = new NotificationFactory();
        Notifications preferredNotifMethod = notifType.getNotifObj(keyMethod);
        preferredNotifMethod.sendNotification(recipient, name, indexNum, courseCode);
    }
}