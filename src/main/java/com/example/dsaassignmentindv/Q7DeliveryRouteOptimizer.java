package com.example.dsaassignmentindv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Q7DeliveryRouteOptimizer extends JFrame {
    private JTextArea deliveryListArea;
    private JComboBox<String> algorithmComboBox;
    private JTextField vehicleCapacityField;
    private JTextField distanceConstraintField;
    private JButton optimizeButton;
    private JPanel routePanel;

    public Q7DeliveryRouteOptimizer() {
        setTitle("Delivery Route Optimizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);  // Center the window

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Delivery List:"), gbc);

        deliveryListArea = new JTextArea(8, 30);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(new JScrollPane(deliveryListArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        inputPanel.add(new JLabel("Select Algorithm:"), gbc);

        algorithmComboBox = new JComboBox<>(new String[]{"Dijkstra", "A*"});
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(algorithmComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Vehicle Capacity:"), gbc);

        vehicleCapacityField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(vehicleCapacityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Distance Constraint:"), gbc);

        distanceConstraintField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(distanceConstraintField, gbc);

        optimizeButton = new JButton("Optimize Route");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(optimizeButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // Route Visualization Panel
        routePanel = new JPanel();
        routePanel.setLayout(new BoxLayout(routePanel, BoxLayout.Y_AXIS));
        routePanel.setBorder(BorderFactory.createTitledBorder("Optimized Route"));
        add(new JScrollPane(routePanel), BorderLayout.CENTER);

        // Action Listener for Optimize Button
        optimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optimizeRoute();
            }
        });
    }

    private void optimizeRoute() {
        // Multithreading for optimization algorithm
        new Thread(() -> {
            // Placeholder for route optimization logic
            List<String> optimizedRoute = calculateOptimalRoute();
            SwingUtilities.invokeLater(() -> visualizeRoute(optimizedRoute));
        }).start();
    }

    private List<String> calculateOptimalRoute() {
        // Implement chosen optimization algorithm here
        return List.of("Stop 1", "Stop 2", "Stop 3", "Stop 4"); // Example output
    }

    private void visualizeRoute(List<String> route) {
        // Clear previous route visualization
        routePanel.removeAll();
        for (String stop : route) {
            JLabel stopLabel = new JLabel(stop);
            stopLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            stopLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            stopLabel.setBackground(Color.LIGHT_GRAY);
            stopLabel.setOpaque(true);
            stopLabel.setHorizontalAlignment(SwingConstants.CENTER);
            routePanel.add(stopLabel);
        }
        routePanel.revalidate();
        routePanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Q7DeliveryRouteOptimizer optimizer = new Q7DeliveryRouteOptimizer();
            optimizer.setVisible(true);
        });
    }
}
