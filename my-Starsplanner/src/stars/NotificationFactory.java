package stars;
import java.lang.*;
import java.lang.reflect.*;

public class NotificationFactory {
    /**
     * default constructor
     *
     */
    public NotificationFactory() {}

    /**
     * Dynamically loading selected class
     * @param className
     * @return Notification
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

