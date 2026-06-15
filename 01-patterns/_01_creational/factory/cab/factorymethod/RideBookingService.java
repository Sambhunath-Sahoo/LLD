package cab.factorymethod;

import cab.common.*;

/** Factory Method — city subclass decides which ride to create. */
public abstract class RideBookingService {

    public abstract Ride createRide(RideType type);

    public void bookRide(RideType type, String pickup, String drop) {
        Ride ride = createRide(type);
        System.out.printf("(%s) ", getCityName());
        ride.book(pickup, drop);
    }

    protected abstract String getCityName();
}
