package stars;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail implements Notifications{
    /**
     *
     * default constructor
     */
    public SendEmail() {}

    /**
     * Send email notification to student to notify him/her of having gotten a position in a particular index for a course.
     * @param recipient
     * @param name
     * @param indexNum
     * @param courseCode
     */
    public void sendNotification(String recipient, String name, int indexNum, String courseCode) {

        final String username = "cz2002ntustars@gmail.com"; // to be added
        final String password = "ntustars1234"; // to be added

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("cz2002ntustars@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient)); // to be added an email addr
            message.setSubject("Successful Registration for " + courseCode);
            message.setText("Dear "+ name + ","
                    + "\n\n You have successfully registered for " + indexNum + " for " + courseCode);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}