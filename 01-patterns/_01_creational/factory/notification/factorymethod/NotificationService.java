package notification.factorymethod;

import notification.common.*;

/**
 * Factory Method — each service subclass picks its own channel.
 * Same notifyUser() steps; only createNotification() differs.
 */
public abstract class NotificationService {

    public abstract Notification createNotification();

    public void notifyUser(String to, String message) {
        Notification notification = createNotification();
        System.out.print(getServiceName() + " → ");
        notification.send(to, message);
    }

    protected abstract String getServiceName();
}
