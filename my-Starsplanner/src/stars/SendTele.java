package stars;

public class SendTele implements Notifications{
    /**
     *
     * default constructor
     */
    //public SendTele() {}

    /**
     * Send Telegram notification to student to notify him/her of having gotten a position in a particular index for a course.
     * @param recipient phone number to receive notification
     * @param name name of recipient
     * @param indexNum index number of index to notify recipient
     * @param courseCode course code of course to notify recipient
     */
    public void sendNotification(String recipient, String name, int indexNum, String courseCode){
        System.out.println("Sending telegram message to " + recipient);
    }
}