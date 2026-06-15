package cab.simplefactory;

import cab.common.*;

/**
 * Simple Factory — user picks ride type on the home screen.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Cab — Simple Factory ===");
        System.out.println("User taps Mini / Bike / Premium → factory creates the ride\n");

        String pickup = "Indiranagar Metro";
        String drop = "Koramangala 5th Block";

        for (RideType type : RideType.values()) {
            Ride ride = RideFactory.create(type);
            System.out.printf("Selected: %s | ", ride.getVehicleType());
            ride.book(pickup, drop);
        }
    }
}
