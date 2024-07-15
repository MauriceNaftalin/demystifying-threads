package threadsafety.monitorvehicletracker;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    private static final int MOVE_INTERVAL_MS = 1250;
    private static final int REFRESH_INTERVAL_MS = 500;
    private static TrackerGui gui;

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        List<Vehicle> vehicles = List.of(new Vehicle("Bus1", Color.RED), new Vehicle("Bus2", Color.BLUE));
        Map<Vehicle, MutablePoint> vehicleLocations = vehicles.stream().collect(Collectors.toMap(v -> v, v -> new MutablePoint()));
        MonitorVehicleTracker vehicleTracker = new MonitorVehicleTracker(vehicleLocations);
        VehicleDespatcher vehicleDespatcher = new VehicleDespatcher(vehicleTracker);
        SwingUtilities.invokeAndWait(() -> {gui = new TrackerGui(vehicleTracker);});

        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(vehicles.size() + 1);
        try {
            for (Vehicle vehicle : vehicles) {
                threadPool.scheduleAtFixedRate(() -> vehicleDespatcher.updateVehiclePosition(vehicle), 0, MOVE_INTERVAL_MS, TimeUnit.MILLISECONDS);
            }
            threadPool.scheduleAtFixedRate(gui::refresh, 0, REFRESH_INTERVAL_MS, TimeUnit.MILLISECONDS);
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(threadPool::shutdown));
        }
    }
}