package threadsafety.naivevehicletracker;

import java.util.Map;

public class NaiveVehicleTracker {

    private final Map<Vehicle, MutablePoint> vehicleLocations;

    public NaiveVehicleTracker(Map<Vehicle, MutablePoint> vehicleLocations) {
        this.vehicleLocations = vehicleLocations;
    }

    public Map<Vehicle, MutablePoint> getVehicleLocations() {
        return vehicleLocations;
    }

    public MutablePoint getVehicleLocation(Vehicle v) {
        return vehicleLocations.get(v);
    }

    public void setVehicleLocation(Vehicle v, int x, int y) {
        MutablePoint location = vehicleLocations.get(v);
        location.setX(x);
        location.setY(y);
    }
}