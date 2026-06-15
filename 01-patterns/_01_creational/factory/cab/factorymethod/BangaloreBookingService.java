package cab.factorymethod;

import cab.common.*;
import cab.simplefactory.RideFactory;

/** Bangalore: all ride types available. */
public class BangaloreBookingService extends RideBookingService {

    @Override
    public Ride createRide(RideType type) {
        return RideFactory.create(type);
    }

    @Override
    protected String getCityName() {
        return "Bangalore";
    }
}
