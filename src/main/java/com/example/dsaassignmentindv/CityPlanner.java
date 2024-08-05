package com.example.dsaassignmentindv;


import java.util.*;

public class CityPlanner {
    static class Node implements Comparable<Node> {
        int vertex, weight;
        Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
        public int compareTo(Node other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    public static List<int[]> modifyConstructionTimes(int n, int[][] roads, int source, int destination, int target) {
        List<int[]>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        List<int[]> constructionRoads = new ArrayList<>();
        for (int[] road : roads) {
            if (road[2] == -1) {
                constructionRoads.add(road);
            } else {
                graph[road[0]].add(new int[]{road[1], road[2]});
                graph[road[1]].add(new int[]{road[0], road[2]});
            }
        }

        int initialPathLength = dijkstra(graph, n, source, destination);
        if (initialPathLength == target) {
            return Arrays.asList(roads);
        }

        for (int[] road : constructionRoads) {
            graph[road[0]].add(new int[]{road[1], 1});
            graph[road[1]].add(new int[]{road[0], 1});
            road[2] = 1;
        }

        int adjustedPathLength = dijkstra(graph, n, source, destination);
        if (adjustedPathLength == target) {
            return Arrays.asList(roads);
        }

        for (int[] road : constructionRoads) {
            int left = 1, right = 2 * (int) 1e9;
            while (left <= right) {
                int mid = left + (right - left) / 2;

                road[2] = mid;
                for (int i = 0; i < n; i++) {
                    graph[i].clear();
                }
                for (int[] r : roads) {
                    graph[r[0]].add(new int[]{r[1], r[2]});
                    graph[r[1]].add(new int[]{r[0], r[2]});
                }

                int pathLength = dijkstra(graph, n, source, destination);

                if (pathLength == target) {
                    return Arrays.asList(roads);
                } else if (pathLength < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return Arrays.asList(roads);
    }

    private static int dijkstra(List<int[]>[] graph, int n, int source, int destination) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;

            for (int[] neighbor : graph[u]) {
                int v = neighbor[0];
                int w = neighbor[1];

                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }

        return dist[destination];
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] roads = {{4, 1, -1}, {2, 0, -1}, {0, 3, -1}, {4, 3, -1}};
        int source = 0;
        int destination = 1;
        int target = 5;

        List<int[]> result = modifyConstructionTimes(n, roads, source, destination, target);

        for (int[] road : result) {
            System.out.println(Arrays.toString(road));
        }
    }
}
