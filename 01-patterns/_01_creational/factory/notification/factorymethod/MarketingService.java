package notification.factorymethod;

import notification.common.*;

/** Marketing newsletters are always Email. */
public class MarketingService extends NotificationService {

    @Override
    public Notification createNotification() {
        return new EmailNotification();
    }

    @Override
    protected String getServiceName() {
        return "Marketing Service";
    }
}
