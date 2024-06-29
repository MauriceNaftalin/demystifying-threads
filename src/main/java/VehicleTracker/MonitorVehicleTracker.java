package VehicleTracker;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonitorVehicleTracker {

    private final Map<Vehicle, MutablePoint> vehicleLocations;

    public MonitorVehicleTracker (List<Vehicle> vehicles) {
        this.vehicleLocations = vehicles.stream().collect(Collectors.toMap(v -> v, v -> new MutablePoint()));
    }

    public Map<Vehicle, MutablePoint> getVehicleLocations() {
        return vehicleLocations;
    }

    public MutablePoint getLocation(Vehicle v) {
        return vehicleLocations.get(v);
    }

    public void setLocation (Vehicle v, int x, int y) {
//        vehicleLocations.put(v, new MutablePoint(x, y));
        final MutablePoint mutablePoint = vehicleLocations.get(v);
        mutablePoint.setX(x);
        mutablePoint.setY(y);
    }
}