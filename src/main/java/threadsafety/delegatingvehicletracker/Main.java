package threadsafety.delegatingvehicletracker;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    private static final int MOVE_INTERVAL_MS = 1000;
    private static final int REFRESH_INTERVAL_MS = 500;

    public static void main(String[] args) {
        List<Vehicle> vehicles = List.of(new Vehicle("Bus1", Color.RED), new Vehicle("Bus2", Color.BLUE));
        ConcurrentMap<Vehicle, Point> vehicleLocations = vehicles.stream()
                .collect(Collectors.toConcurrentMap(v -> v, v -> new Point()));
        DelegatingVehicleTracker vehicleTracker = new DelegatingVehicleTracker(vehicleLocations);
        VehicleDespatcher vehicleDespatcher = new VehicleDespatcher(vehicleTracker);
        final TrackerGui trackerGui = new TrackerGui(vehicleTracker);

        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(vehicles.size() + 1);
        try {
            for (Vehicle vehicle : vehicles) {
                threadPool.scheduleAtFixedRate(() -> {vehicleDespatcher.updateVehiclePosition(vehicle);
                }, 0, MOVE_INTERVAL_MS, TimeUnit.MILLISECONDS);
            }
            threadPool.scheduleAtFixedRate(trackerGui::refresh, 0, REFRESH_INTERVAL_MS, TimeUnit.MILLISECONDS);
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(threadPool::shutdown));
        }
    }
}