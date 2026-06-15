package cab.common;

public class PremiumSuvRide implements Ride {
    @Override
    public String getVehicleType() {
        return "Premium SUV";
    }

    @Override
    public double estimateFare(double distanceKm) {
        return 120 + distanceKm * 22;
    }

    @Override
    public void book(String pickup, String drop) {
        System.out.printf("[Premium SUV] %s → %s | ₹%.0f%n", pickup, drop, estimateFare(8.5));
    }
}
