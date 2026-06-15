package cab.common;

public interface Ride {
    String getVehicleType();

    double estimateFare(double distanceKm);

    void book(String pickup, String drop);
}
