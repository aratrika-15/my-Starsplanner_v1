package stars;

public class SendWhatsapp implements Notifications{
    /**
     *
     * default constructor
     */
    //public SendWhatsapp() {}
    /**
     * Send Whatsapp notification to student to notify him/her of having gotten a position in a particular index for a course.
     * @param recipient
     * @param name
     * @param indexNum
     * @param courseCode
     */
    public void sendNotification(String recipient, String name, int indexNum, String courseCode) {
        System.out.println("Sending whatsapp message to " + recipient);
    }

}