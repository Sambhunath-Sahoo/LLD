package notification.factorymethod;

import notification.common.*;

/** App alerts (order shipped, rider nearby) are always Push. */
public class OrderAlertService extends NotificationService {

    @Override
    public Notification createNotification() {
        return new PushNotification();
    }

    @Override
    protected String getServiceName() {
        return "Order Alert Service";
    }
}
