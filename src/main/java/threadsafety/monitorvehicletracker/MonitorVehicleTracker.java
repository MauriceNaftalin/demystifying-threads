package threadsafety.monitorvehicletracker;

import net.jcip.annotations.GuardedBy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntUnaryOperator;

public class MonitorVehicleTracker {

    @GuardedBy("this")
    private final Map<Vehicle, MutablePoint> vehicleLocations;

    public MonitorVehicleTracker (Map<Vehicle, MutablePoint> vehicleLocations) {
        this.vehicleLocations = defensiveCopy(vehicleLocations);
    }

    public synchronized Map<Vehicle, MutablePoint> getVehicleLocations() {
        return defensiveCopy(vehicleLocations);
    }

    public synchronized MutablePoint getLocation(Vehicle v) {
        return new MutablePoint(vehicleLocations.get(v));
    }

    public synchronized void setLocation (Vehicle v, int x, int y) {
        MutablePoint location = vehicleLocations.get(v);
        location.setX(x);
        location.setY(y);
    }

    public synchronized void updateCoordinateFromExisting(Vehicle vehicle, IntUnaryOperator calculateNew, boolean changeX) {
        MutablePoint point = vehicleLocations.get(vehicle);
        if (changeX) {
            point.setX(calculateNew.applyAsInt(point.x));
        } else {
            point.setY(calculateNew.applyAsInt(point.y));
        }
    }

    public MutablePoint getLocationReversed(Vehicle v) {
        MutablePoint mp = new MutablePoint(vehicleLocations.get(v));
        int tempx = mp.x;
        mp.x = mp.y;
        mp.y = tempx;
        return mp;
    }

    private static Map<Vehicle, MutablePoint> defensiveCopy(Map<Vehicle, MutablePoint> vehicleLocations) {
        final HashMap<Vehicle, MutablePoint> copy = new HashMap<>(vehicleLocations);
        for (Vehicle v : vehicleLocations.keySet()) {
            copy.put(v, new MutablePoint(copy.get(v)));
        }
        return copy;
    }
}