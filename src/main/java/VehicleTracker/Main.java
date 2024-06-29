package VehicleTracker;

import java.awt.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int MAX_X = 5;
    private static final int MAX_Y = 4;
    private static final int MOVE_INTERVAL_MS = 1250;
    private static final int REFRESH_INTERVAL_MS = 500;

    public static void main(String[] args) {
        List<Vehicle> vehicles = List.of(new Vehicle("Bus1", Color.RED), new Vehicle("Bus2", Color.BLUE));
        MonitorVehicleTracker vehicleTracker = new MonitorVehicleTracker(vehicles);
        TrackerGui trackerGui = new TrackerGui(vehicleTracker);

        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(vehicles.size() + 1);
        try {
            for (Vehicle vehicle : vehicles) {
                threadPool.scheduleAtFixedRate(() -> updateVehiclePosition(vehicle, vehicleTracker), 0, MOVE_INTERVAL_MS, TimeUnit.MILLISECONDS);
            }
            threadPool.scheduleAtFixedRate(trackerGui::refresh, 0, REFRESH_INTERVAL_MS, TimeUnit.MILLISECONDS);
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(threadPool::shutdown));
        }
    }

    private static void updateVehiclePosition(Vehicle vehicle, MonitorVehicleTracker vehicleTracker) {
        MutablePoint location = vehicleTracker.getLocation(vehicle);
        int newX = location.x;
        if (ThreadLocalRandom.current().nextBoolean()) {
            newX = calculateNewPosition(location.x, MAX_X);
            vehicleTracker.setLocation(vehicle, newX, location.y);
        }
        if (newX == location.x) {
            int newY = calculateNewPosition(location.y, MAX_Y);
            vehicleTracker.setLocation(vehicle, location.x, newY);
        }
    }

    private static int calculateNewPosition(int current, int max) {
        int change = ThreadLocalRandom.current().nextBoolean() ? 1 : -1;
        int newPos = current + change;
        if (newPos < 0 || newPos > max) {
            return current;
        }
        return newPos;
    }
}