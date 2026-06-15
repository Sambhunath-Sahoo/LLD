package notification.common;

public class PushNotification implements Notification {
    @Override
    public void send(String to, String message) {
        System.out.println("[PUSH] to=" + to + " | " + message);
    }
}
