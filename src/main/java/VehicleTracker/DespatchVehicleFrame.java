package VehicleTracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DespatchVehicleFrame extends JFrame {
        // Define text fields
        private JLabel label1, label2, label3;
        private JComboBox<String> vehicleSelector;
        private JTextField textField2;
        private JTextField textField3;
        private JButton button;
        private MonitorVehicleTracker monitorVehicleTracker;

        public DespatchVehicleFrame(MonitorVehicleTracker monitorVehicleTracker) {
            this.monitorVehicleTracker = monitorVehicleTracker;

            // Frame initialization
            setTitle("Input Form");
            setSize(300, 200);
            setLocationRelativeTo(null); // Center the window
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new FlowLayout()); // Set the layout manager

            // Initialize labels
            label1 = new JLabel("Vehicle:");
            label2 = new JLabel("x:");
            label3 = new JLabel("y:");

            // Initialize text fields
            vehicleSelector = new JComboBox<>(new String[]{"Bus"}); // 10 columns width for Vehicle
            textField2 = new JTextField(5);  // 5 columns width for x
            textField3 = new JTextField(5);  // 5 columns width for y

            // Initialize button
            button = new JButton("Submit");

            // Add action listener to the button
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Action to be performed when button is clicked
                    String vehicle = vehicleSelector.getItemAt(vehicleSelector.getSelectedIndex());
                    String x = textField2.getText();
                    String y = textField3.getText();
//                    monitorVehicleTracker.setLocation(vehicle, Integer.parseInt(x), Integer.parseInt(y));
                }
            });

            // Add components to the frame
            add(label1);
            add(vehicleSelector);
            add(label2);
            add(textField2);
            add(label3);
            add(textField3);
            add(button);

            // Set visible
            setVisible(true);

        }
}
