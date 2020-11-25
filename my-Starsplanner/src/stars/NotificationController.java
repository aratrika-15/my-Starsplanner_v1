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
     *
     * @param recipient the device/account identification to send notification to (eg phone number or email address)
     * @param name Name of the Student to send notification to
     * @param indexNum Index Number that the student has been added to from the waitlist
     * @param courseCode Course Code index that the student has been added to
     * @param keyMethod method of notification (eg. email, whatsapp, telegram)
     */
    public void notify(String recipient, String name, int indexNum, String courseCode, String keyMethod) {
        NotificationFactory notifType = new NotificationFactory();
        Notifications preferredNotifMethod = notifType.getNotifObj(keyMethod);
        preferredNotifMethod.sendNotification(recipient, name, indexNum, courseCode);
    }
}