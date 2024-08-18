package com.example.dsaassignmentindv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class Q7DeliveryRouteOptimizer extends JFrame {

    private Map<String, Point> cityPositions;
    private Map<String, Integer> cityIndexMap;
    private int[][] distances;

    private String startCity;
    private String endCity;

    private List<Integer> shortestPath;

    public Q7DeliveryRouteOptimizer() {
        setTitle("Route Optimization for Delivery Service");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 500));

        // Initialize cities and distances
        initializeCitiesAndDistances();

        // Main layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Draw panel for graph visualization
        GraphPanel graphPanel = new GraphPanel();
        graphPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        add(graphPanel, gbc);

        // Panel for displaying shortest path
        JPanel pathPanel = new JPanel(new BorderLayout());
        pathPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        pathPanel.setBackground(new Color(245, 245, 245));

        JLabel pathLabel = new JLabel("Shortest Path will be displayed here");
        pathLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pathLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        pathLabel.setForeground(new Color(0, 102, 204));
        pathPanel.add(pathLabel, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.3;
        gbc.weighty = 0.5;
        add(pathPanel, gbc);

        // Input panel for selecting start and end points
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(230, 230, 250));

        GridBagConstraints inputGbc = new GridBagConstraints();
        inputGbc.insets = new Insets(5, 5, 5, 5);
        inputGbc.fill = GridBagConstraints.HORIZONTAL;
        inputGbc.weightx = 1.0;

        JLabel startLabel = new JLabel("Start City:");
        startLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        startLabel.setForeground(new Color(51, 51, 51));
        inputGbc.gridx = 0;
        inputGbc.gridy = 0;
        inputPanel.add(startLabel, inputGbc);

        JComboBox<String> startComboBox = new JComboBox<>(cityIndexMap.keySet().toArray(new String[0]));
        startComboBox.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        startComboBox.addActionListener(e -> {
            startCity = (String) startComboBox.getSelectedItem();
            graphPanel.repaint();
        });
        inputGbc.gridx = 1;
        inputPanel.add(startComboBox, inputGbc);

        JLabel endLabel = new JLabel("End City:");
        endLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        endLabel.setForeground(new Color(51, 51, 51));
        inputGbc.gridx = 0;
        inputGbc.gridy = 1;
        inputPanel.add(endLabel, inputGbc);

        JComboBox<String> endComboBox = new JComboBox<>(cityIndexMap.keySet().toArray(new String[0]));
        endComboBox.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        endComboBox.addActionListener(e -> {
            endCity = (String) endComboBox.getSelectedItem();
            graphPanel.repaint();
        });
        inputGbc.gridx = 1;
        inputPanel.add(endComboBox, inputGbc);

        JButton optimizeButton = new JButton("Optimize Route");
        optimizeButton.setFont(new Font("Sans Serif", Font.BOLD, 14));
        optimizeButton.setBackground(new Color(0, 153, 76));
        optimizeButton.setForeground(Color.WHITE);
        optimizeButton.setFocusPainted(false);
        optimizeButton.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 51)));
        optimizeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        optimizeButton.addActionListener(e -> {
            if (startCity != null && endCity != null && !startCity.equals(endCity)) {
                findShortestPath(startCity, endCity);
                pathLabel.setText("Shortest Path: " + shortestPathToString());
                graphPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Please select different start and end cities.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        optimizeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                optimizeButton.setBackground(new Color(0, 204, 102));  // Lighter green on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                optimizeButton.setBackground(new Color(0, 153, 76));  // Original green
            }
        });
        inputGbc.gridx = 0;
        inputGbc.gridy = 2;
        inputGbc.gridwidth = 2;
        inputPanel.add(optimizeButton, inputGbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 0.5;
        add(inputPanel, gbc);

        setVisible(true);
    }

    private void initializeCitiesAndDistances() {
        // Initialize city positions (for graphical representation)
        cityPositions = new HashMap<>();
        cityPositions.put("CityA", new Point(200, 100));
        cityPositions.put("CityB", new Point(100, 300));
        cityPositions.put("CityC", new Point(400, 150));
        cityPositions.put("CityD", new Point(300, 400));
        cityPositions.put("CityE", new Point(500, 300));
        cityPositions.put("CityF", new Point(600, 200));

        // Initialize cities and their indices
        String[] cities = {"CityA", "CityB", "CityC", "CityD", "CityE", "CityF"};
        cityIndexMap = new HashMap<>();
        for (int i = 0; i < cities.length; i++) {
            cityIndexMap.put(cities[i], i);
        }

        // Initialize distances (adjacency matrix)
        distances = new int[cities.length][cities.length];
        for (int i = 0; i < cities.length; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
            distances[i][i] = 0;
        }

        // Add connections between cities with updated names
        addConnection("CityA", "CityB", 200);
        addConnection("CityB", "CityD", 50);
        addConnection("CityD", "CityF", 150);
        addConnection("CityF", "CityE", 50);
        addConnection("CityC", "CityF", 55);
        addConnection("CityC", "CityB", 200);
    }

    private void addConnection(String city1, String city2, int distance) {
        int index1 = cityIndexMap.get(city1);
        int index2 = cityIndexMap.get(city2);
        distances[index1][index2] = distance;
        distances[index2][index1] = distance;
    }

    private void findShortestPath(String startCity, String endCity) {
        int startIndex = cityIndexMap.get(startCity);
        int endIndex = cityIndexMap.get(endCity);
        shortestPath = dijkstra(startIndex, endIndex);
    }

    private List<Integer> dijkstra(int start, int end) {
        int numCities = distances.length;
        int[] minDistances = new int[numCities];
        boolean[] visited = new boolean[numCities];
        int[] previous = new int[numCities];

        Arrays.fill(minDistances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        minDistances[start] = 0;

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(i -> minDistances[i]));
        queue.add(start);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            visited[u] = true;

            for (int v = 0; v < numCities; v++) {
                if (!visited[v] && distances[u][v] != Integer.MAX_VALUE) {
                    int alt = minDistances[u] + distances[u][v];
                    if (alt < minDistances[v]) {
                        minDistances[v] = alt;
                        previous[v] = u;
                        queue.add(v);
                    }
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = previous[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    private String shortestPathToString() {
        StringBuilder sb = new StringBuilder();
        for (int cityIndex : shortestPath) {
            sb.append(getCityNameByIndex(cityIndex)).append(" -> ");
        }
        return sb.substring(0, sb.length() - 4);
    }

    private String getCityNameByIndex(int index) {
        for (Map.Entry<String, Integer> entry : cityIndexMap.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }

    private class GraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(new Color(0, 102, 204));

            for (String city : cityPositions.keySet()) {
                Point pos = cityPositions.get(city);
                g2d.fillOval(pos.x - 10, pos.y - 10, 20, 20);
                g2d.drawString(city, pos.x - 15, pos.y - 15);
            }

            for (String city1 : cityPositions.keySet()) {
                for (String city2 : cityPositions.keySet()) {
                    if (!city1.equals(city2)) {
                        int index1 = cityIndexMap.get(city1);
                        int index2 = cityIndexMap.get(city2);
                        if (distances[index1][index2] != Integer.MAX_VALUE) {
                            Point pos1 = cityPositions.get(city1);
                            Point pos2 = cityPositions.get(city2);
                            g2d.drawLine(pos1.x, pos1.y, pos2.x, pos2.y);
                        }
                    }
                }
            }

            if (shortestPath != null) {
                g2d.setColor(new Color(255, 69, 0)); // Orange-Red color for shortest path
                g2d.setStroke(new BasicStroke(3));

                for (int i = 0; i < shortestPath.size() - 1; i++) {
                    Point pos1 = cityPositions.get(getCityNameByIndex(shortestPath.get(i)));
                    Point pos2 = cityPositions.get(getCityNameByIndex(shortestPath.get(i + 1)));
                    g2d.drawLine(pos1.x, pos1.y, pos2.x, pos2.y);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Q7DeliveryRouteOptimizer::new);
    }
}
