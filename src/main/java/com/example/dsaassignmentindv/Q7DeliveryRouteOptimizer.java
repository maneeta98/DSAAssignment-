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
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        deliveryListArea = new JTextArea(10, 30);
        algorithmComboBox = new JComboBox<>(new String[]{"Dijkstra", "A*"});
        vehicleCapacityField = new JTextField(10);
        distanceConstraintField = new JTextField(10);
        optimizeButton = new JButton("Optimize Route");
        routePanel = new JPanel();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2)); // Set layout to GridLayout for better alignment
        inputPanel.add(new JLabel("Delivery List:"));
        inputPanel.add(new JScrollPane(deliveryListArea));
        inputPanel.add(new JLabel("Select Algorithm:"));
        inputPanel.add(algorithmComboBox);
        inputPanel.add(new JLabel("Vehicle Capacity:"));
        inputPanel.add(vehicleCapacityField);
        inputPanel.add(new JLabel("Distance Constraint:"));
        inputPanel.add(distanceConstraintField);
        inputPanel.add(optimizeButton);

        optimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optimizeRoute();
            }
        });

        add(inputPanel, BorderLayout.NORTH);
        add(routePanel, BorderLayout.CENTER);
    }

    private void optimizeRoute() {
        // Multithreading for optimization algorithm
        new Thread(() -> {
            // Placeholder for route optimization logic
            List<String> optimizedRoute = calculateOptimalRoute();
            visualizeRoute(optimizedRoute);
        }).start();
    }

    private List<String> calculateOptimalRoute() {
        // Implement chosen optimization algorithm here
        return List.of("Stop 1", "Stop 2", "Stop 3"); // Example output
    }

    private void visualizeRoute(List<String> route) {
        // Clear previous route visualization
        routePanel.removeAll();
        for (String stop : route) {
            routePanel.add(new JLabel(stop));
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
