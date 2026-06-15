package notification.simplefactory;

import notification.common.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/** User picks EMAIL / SMS / PUSH → factory returns the right sender. */
public class NotificationFactory {

    private static final Map<Channel, Supplier<Notification>> registry = new EnumMap<>(Channel.class);

    static {
        registry.put(Channel.EMAIL, EmailNotification::new);
        registry.put(Channel.SMS, SmsNotification::new);
        registry.put(Channel.PUSH, PushNotification::new);
    }

    public static Notification create(Channel channel) {
        Supplier<Notification> supplier = registry.get(channel);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown channel: " + channel);
        }
        return supplier.get();
    }
}
