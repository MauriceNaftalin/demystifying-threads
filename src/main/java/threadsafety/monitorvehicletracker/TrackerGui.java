package threadsafety.monitorvehicletracker;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class TrackerGui extends JFrame {
    private final MonitorVehicleTracker vehicles;
    private final int FRAME_SIZE = 400;
    public TrackerGui(MonitorVehicleTracker vehicles) {
        this.vehicles = vehicles;
        this.setTitle("MonitorTracker Vehicle Positions");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create the grid panel
        GraphPaperPanel gridPanel = new GraphPaperPanel();
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        final int lineCount = FRAME_SIZE / gridPanel.lineGap;

        // Create labels for the Y axis
        JPanel yAxisPanel = new JPanel(new GridLayout(0, 1, 0, gridPanel.lineGap - 200));
        yAxisPanel.setPreferredSize(new Dimension(30, FRAME_SIZE)); // Increased width
        yAxisPanel.add(new JLabel(""));
        for (int i = 1; i < lineCount; i++) {
            JLabel label = new JLabel(i + "  ");
            label.setHorizontalAlignment(JLabel.RIGHT);
            yAxisPanel.add(label);
        }
        mainPanel.add(yAxisPanel, BorderLayout.WEST);

        // Create labels for the X axis
        JPanel xAxisPanel = new JPanel(new GridLayout(1, 0, gridPanel.lineGap, 0));
        xAxisPanel.setPreferredSize(new Dimension(FRAME_SIZE, 30)); // Increased height
        for (int i = 0; i < FRAME_SIZE / gridPanel.lineGap; i++) {
            JLabel label = new JLabel(String.valueOf(i));
            label.setHorizontalAlignment(JLabel.CENTER);
            xAxisPanel.add(label);
        }
        mainPanel.add(xAxisPanel, BorderLayout.SOUTH);

        // Adding main panel to the frame
        this.add(mainPanel);
        this.pack();  // Adjusts size to fit the preferred size of its components
        this.setLocationRelativeTo(null);  // Center the window
        this.setVisible(true);
    }

    public void refresh() {
//        Map<Vehicle, Point> locations = vehicles.getVehicleLocations();
//        for (String key : locations.keySet()){
//            renderVehicle(key,locations.get(key));
//        }
        repaint();
    }

    private class GraphPaperPanel extends JPanel {
        private final int lineGap = 80;

        public GraphPaperPanel() {
            this.setPreferredSize(new Dimension(FRAME_SIZE, FRAME_SIZE));  // Set preferred size
            this.setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();

            // Draw vertical lines
            for (int i = 0; i < width; i += lineGap) {
                g.drawLine(i, 0, i, height);
            }

            // Draw horizontal lines
            for (int i = 0; i < height; i += lineGap) {
                g.drawLine(0, i, width, i);
            }

            final Map<Vehicle, MutablePoint> vehicleLocations = vehicles.getVehicleLocations();
            for (Vehicle v : vehicleLocations.keySet()) {
                MutablePoint mp = vehicleLocations.get(v);
                g.setColor(v.color());
                g.fillOval(80 * mp.x - 11, 80 * mp.y - 11,22, 22);
            }
/*
V            g.setColor(Color.BLACK);
            for (Point mp : vehicles.getPickupPoints().values()) {
                g.drawOval(70 * mp.x - 1, 70 * mp.y - 1 ,24, 24);
            }

            g.setColor(Color.WHITE);
            for (Point mp : vehicles.getPickupPoints().values()) {
                g.fillOval(70 * mp.x, 70 * mp.y,22, 22);
            }
*/
        }
    }
}