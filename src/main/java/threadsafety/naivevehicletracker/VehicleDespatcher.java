package threadsafety.naivevehicletracker;

import java.util.concurrent.ThreadLocalRandom;

public class VehicleDespatcher {

    private static final int MAX_X = 5;
    private static final int MAX_Y = 5;

    private final NaiveVehicleTracker naiveVehicleTracker;

    public VehicleDespatcher(NaiveVehicleTracker naiveVehicleTracker) {
        this.naiveVehicleTracker = naiveVehicleTracker;
    }

    public void updateVehiclePosition(Vehicle vehicle) {
        MutablePoint location = naiveVehicleTracker.getVehicleLocation(vehicle);
        if (ThreadLocalRandom.current().nextBoolean()) {
            int newX = calculateNewPosition(location.x, MAX_X);
            naiveVehicleTracker.setVehicleLocation(vehicle, newX, location.y);
        } else {
            int newY = calculateNewPosition(location.y, MAX_Y);
            naiveVehicleTracker.setVehicleLocation(vehicle, location.x, newY);
        }
    }

    private int calculateNewPosition(int current, int max) {
        int change = ThreadLocalRandom.current().nextBoolean() ? 1 : -1;
        int newPos = current + change;
        if (newPos < 0 || newPos > max) {
            return current;
        }
        return newPos;
    }
}
