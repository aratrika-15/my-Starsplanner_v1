package stars;
import java.lang.*;
import java.lang.reflect.*;

/**
 * generates a Notifications object dynamically
 */
public class NotificationFactory {
    /**
     * default constructor
     *
     */
    public NotificationFactory() {}

    /**
     * Dynamically loading selected class
     * @param className method of notification (eg. email, whatsapp, telegram)
     * @return Notification respective Notification object based on notification method
     */
    public Notifications getNotifObj(String className) {
        Notifications notification = null;

        try {
            className = "stars."+ className;

            Class<?> notif = ClassLoader.getSystemClassLoader().loadClass(className);

            notification = (Notifications) notif.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return notification;
    }
}

