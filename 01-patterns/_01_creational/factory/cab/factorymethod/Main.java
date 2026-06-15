package cab.factorymethod;

import cab.common.*;

/**
 * Factory Method — same bookRide() flow, city rules decide what gets created.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Cab — Factory Method ===");
        System.out.println("Same bookRide() — Mumbai vs Bangalore fleet rules\n");

        RideBookingService mumbai = new MumbaiBookingService(true);
        RideBookingService bangalore = new BangaloreBookingService();

        System.out.println("1. Mumbai user taps BIKE (monsoon):");
        mumbai.bookRide(RideType.BIKE, "Andheri West", "Bandra Kurla Complex");
        System.out.println();

        System.out.println("2. Bangalore user taps BIKE:");
        bangalore.bookRide(RideType.BIKE, "Indiranagar Metro", "Koramangala 5th Block");
        System.out.println();

        System.out.println("3. Mumbai user taps PREMIUM:");
        mumbai.bookRide(RideType.PREMIUM, "CSMIA T2", "Nariman Point");
    }
}
