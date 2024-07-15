package threadsafety.delegatingvehicletracker;

import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.IntUnaryOperator;

@ThreadSafe
public class DelegatingVehicleTracker {

    private final ConcurrentMap<Vehicle, Point> vehicleLocations;
    private final Map<Vehicle, Point> unmodifiableVehicleLocations;

    public DelegatingVehicleTracker(ConcurrentMap<Vehicle, Point> vehicleLocations) {
        this.vehicleLocations = vehicleLocations;
        unmodifiableVehicleLocations = Collections.unmodifiableMap(vehicleLocations);
    }

    public Map<Vehicle, Point> getVehicleLocations() {
        return Collections.unmodifiableMap(vehicleLocations);
    }

    public Point getLocation(Vehicle v) {
        return vehicleLocations.get(v);
    }

    public void setLocation (Vehicle v, int x, int y) {
        vehicleLocations.replace(v, new Point(x, y));
    }

    public void updateCoordinateFromExisting(Vehicle vehicle, IntUnaryOperator calculateNew, boolean changeX) {
        Point point = vehicleLocations.get(vehicle);
        if (changeX) {
            int newX = calculateNew.applyAsInt(point.x());
            vehicleLocations.replace(vehicle, new Point(newX, point.y()));
        } else {
            int newY = calculateNew.applyAsInt(point.y());
            vehicleLocations.replace(vehicle, new Point(point.x(), newY));
        }
    }
}