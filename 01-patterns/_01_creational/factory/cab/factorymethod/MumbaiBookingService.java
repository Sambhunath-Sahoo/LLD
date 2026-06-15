package cab.factorymethod;

import cab.common.*;
import cab.simplefactory.RideFactory;

/** Mumbai monsoon: bike requests upgrade to Mini Cab. */
public class MumbaiBookingService extends RideBookingService {

    private final boolean monsoonMode;

    public MumbaiBookingService(boolean monsoonMode) {
        this.monsoonMode = monsoonMode;
    }

    @Override
    public Ride createRide(RideType type) {
        if (monsoonMode && type == RideType.BIKE) {
            System.out.print("Monsoon — bike unavailable, Mini Cab assigned. ");
            return new MiniCabRide();
        }
        return RideFactory.create(type);
    }

    @Override
    protected String getCityName() {
        return "Mumbai";
    }
}
