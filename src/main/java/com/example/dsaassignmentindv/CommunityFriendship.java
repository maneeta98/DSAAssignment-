package com.example.dsaassignmentindv;

import java.util.*;

public class CommunityFriendship {

    // UnionFind class to manage the connected components of houses
    public static class UnionFind {
        private int[] parent;
        private int[] rank;

        // Constructor to initialize the parent and rank arrays
        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        // Find method with path compression to find the root of a node
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        // Union method to union two sets, returns true if union was successful
        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) return false; // Already in the same set

            // Union by rank
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            return true;
        }

        // Method to check if two nodes are in the same set
        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }
    }

    // Method to process the friend requests
    public static List<String> processFriendRequests(int n, int[][] restrictions, int[][] requests) {
        UnionFind uf = new UnionFind(n);
        List<String> result = new ArrayList<>();

        // Iterate over each request
        for (int[] request : requests) {
            int houseA = request[0];
            int houseB = request[1];

            boolean canBeFriends = true;
            // Check each restriction to ensure no indirect friendship violation
            for (int[] restriction : restrictions) {
                int resA = restriction[0];
                int resB = restriction[1];

                // Check if adding this friendship would violate the restriction
                if ((uf.isConnected(houseA, resA) && uf.isConnected(houseB, resB)) ||
                        (uf.isConnected(houseA, resB) && uf.isConnected(houseB, resA))) {
                    canBeFriends = false;
                    break;
                }
            }

            if (canBeFriends) {
                uf.union(houseA, houseB);
                result.add("approved");
            } else {
                result.add("denied");
            }
        }

        return result;
    }

    // Main method to test the solution
    public static void main(String[] args) {
        int n1 = 3;
        int[][] restrictions1 = { {0, 1} };
        int[][] requests1 = { {0, 2}, {2, 1} };
        System.out.println(processFriendRequests(n1, restrictions1, requests1)); // Output: [approved, denied]

        int n2 = 5;
        int[][] restrictions2 = { {0, 1}, {1, 2}, {2, 3} };
        int[][] requests2 = { {0, 4}, {1, 2}, {3, 1}, {3, 4} };
        System.out.println(processFriendRequests(n2, restrictions2, requests2)); // Output: [approved, denied, approved, denied]
    }
}

