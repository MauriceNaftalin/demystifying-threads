package threadsafety.monitorvehicletracker;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntUnaryOperator;

public class VehicleDespatcher {

    private static final int MAX_X = 5;
    private static final int MAX_Y = 5;

    private final MonitorVehicleTracker monitorVehicleTracker;

    public VehicleDespatcher(MonitorVehicleTracker monitorVehicleTracker) {
        this.monitorVehicleTracker = monitorVehicleTracker;
    }

    public void updateVehiclePosition(Vehicle vehicle) {
        final IntUnaryOperator intUnaryOperator;
        final boolean changeX = ThreadLocalRandom.current().nextBoolean();
        if (changeX) {
            intUnaryOperator = x -> calculateNewPosition(x, MAX_X);
        } else {
            intUnaryOperator = y -> calculateNewPosition(y, MAX_Y);
        }
        monitorVehicleTracker.updateCoordinateFromExisting(vehicle, intUnaryOperator, changeX);
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