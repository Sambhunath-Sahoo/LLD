package notification.simplefactory;

import notification.common.*;

/**
 * PRACTICE: Simple Factory
 *
 * App settings: user chooses Email / SMS / Push.
 * OrderService never does new EmailNotification() — it asks the factory.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Notification — Simple Factory ===");
        System.out.println("User picks channel in settings → factory creates sender\n");

        String user = "user@example.com";
        String message = "Your order #1234 has shipped";

        Notification email = NotificationFactory.create(Channel.EMAIL);
        email.send(user, message);

        Notification sms = NotificationFactory.create(Channel.SMS);
        sms.send("+91-9876543210", message);

        Notification push = NotificationFactory.create(Channel.PUSH);
        push.send("device-token-abc", message);
    }
}
