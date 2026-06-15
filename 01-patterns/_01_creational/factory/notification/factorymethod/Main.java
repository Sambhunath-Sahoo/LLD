package notification.factorymethod;

/**
 * PRACTICE: Factory Method
 *
 * Caller picks the SERVICE (OTP / Marketing / Order Alert).
 * Each service internally picks Email / SMS / Push — caller never chooses channel.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Notification — Factory Method ===");
        System.out.println("Caller picks service → service picks channel\n");

        NotificationService otp = new OtpService();
        otp.notifyUser("+91-9876543210", "Your OTP is 482910");

        NotificationService marketing = new MarketingService();
        marketing.notifyUser("user@example.com", "50% off this weekend!");

        NotificationService orderAlert = new OrderAlertService();
        orderAlert.notifyUser("device-token-abc", "Your rider is 2 min away");
    }
}
