package stars;

/**
 * interface for defining a function to send notification
 */
public interface Notifications {
    /**
     * interface for defining a function to send notification
     * @param recipient the receiving address
     * @param name name of recipient
     * @param indexNum index number to notify recipient
     * @param courseCode course code to notify recipient
     */
        public void sendNotification(String recipient, String name, int indexNum, String courseCode);

}