package com.example.dsaassignmentindv;


import java.util.ArrayList;
import java.util.Collections;

public class Q5aTSPHillClimbing {

    public static ArrayList<Integer> hillClimbingTSP(int[][] graph, int start) {
        ArrayList<Integer> path = new ArrayList<>();
        int n = graph.length;

        for (int i = 0; i < n; i++) {
            if (i != start) {
                path.add(i);
            }
        }

        Collections.shuffle(path);

        int minDistance = Integer.MAX_VALUE;
        boolean improved;
        do {
            int currentDistance = calculatePathDistance(graph, start, path);
            improved = false;

            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    Collections.swap(path, i, j);
                    int newDistance = calculatePathDistance(graph, start, path);
                    if (newDistance < currentDistance) {
                        currentDistance = newDistance;
                        improved = true;
                    } else {
                        Collections.swap(path, i, j);
                    }
                }
            }

            if (currentDistance < minDistance) {
                minDistance = currentDistance;
            }
        } while (improved);

        path.add(0, start);
        return path;
    }

    private static int calculatePathDistance(int[][] graph, int start, ArrayList<Integer> path) {
        int distance = 0;
        int n = path.size();

        for (int i = 0; i < n - 1; i++) {
            distance += graph[path.get(i)][path.get(i + 1)];
        }

        distance += graph[start][path.get(0)];
        distance += graph[path.get(n - 1)][start];

        return distance;
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        int start = 0;
        ArrayList<Integer> path = hillClimbingTSP(graph, start);

        System.out.println("Optimal Path: " + path);
        System.out.println("Optimal Distance: " + calculatePathDistance(graph, start, path));
    }
}

