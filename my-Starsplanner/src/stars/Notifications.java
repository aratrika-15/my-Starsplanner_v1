package stars;

public interface Notifications {
    /**
     * interface for defining a function to send notification
     * @param recipient
     * @param name
     * @param indexNum
     * @param courseCode
     */
        public void sendNotification(String recipient, String name, int indexNum, String courseCode);

}