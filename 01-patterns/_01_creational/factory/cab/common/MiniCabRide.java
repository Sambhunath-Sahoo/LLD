package cab.common;

public class MiniCabRide implements Ride {
    @Override
    public String getVehicleType() {
        return "Mini Cab";
    }

    @Override
    public double estimateFare(double distanceKm) {
        return 40 + distanceKm * 12;
    }

    @Override
    public void book(String pickup, String drop) {
        System.out.printf("[Mini Cab] %s → %s | ₹%.0f%n", pickup, drop, estimateFare(8.5));
    }
}
