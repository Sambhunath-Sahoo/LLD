package cab.simplefactory;

import cab.common.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/** User taps Mini / Bike / Premium → factory returns the right ride. */
public class RideFactory {

    private static final Map<RideType, Supplier<Ride>> registry = new EnumMap<>(RideType.class);

    static {
        registry.put(RideType.MINI, MiniCabRide::new);
        registry.put(RideType.BIKE, BikeRide::new);
        registry.put(RideType.PREMIUM, PremiumSuvRide::new);
    }

    public static Ride create(RideType type) {
        Supplier<Ride> supplier = registry.get(type);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown ride type: " + type);
        }
        return supplier.get();
    }
}
