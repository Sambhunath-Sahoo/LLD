package notification.factorymethod;

import notification.common.*;

/** OTP is always SMS — the service decides, not the caller. */
public class OtpService extends NotificationService {

    @Override
    public Notification createNotification() {
        return new SmsNotification();
    }

    @Override
    protected String getServiceName() {
        return "OTP Service";
    }
}
