package cab.common;

public class BikeRide implements Ride {
    @Override
    public String getVehicleType() {
        return "Bike";
    }

    @Override
    public double estimateFare(double distanceKm) {
        return 25 + distanceKm * 8;
    }

    @Override
    public void book(String pickup, String drop) {
        System.out.printf("[Bike] %s → %s | ₹%.0f%n", pickup, drop, estimateFare(8.5));
    }
}
