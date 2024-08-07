package com.example.dsaassignmentindv;

import java.util.*;

public class Q3aFriendRequests {
        public List<String> checkRequests(int n, int[][] restrictions, int[][] requests) {
            List<String> result = new ArrayList<>();
            Map<Integer, Set<Integer>> graph = new HashMap<>();

            for (int[] restriction : restrictions) {
                graph.computeIfAbsent(restriction[0], k -> new HashSet<>()).add(restriction[1]);
                graph.computeIfAbsent(restriction[1], k -> new HashSet<>()).add(restriction[0]);
            }

            for (int[] request : requests) {
                if (canBeFriends(request[0], request[1], graph)) {
                    result.add("approved");
                } else {
                    result.add("denied");
                }
            }

            return result;
        }

        private boolean canBeFriends(int house1, int house2, Map<Integer, Set<Integer>> graph) {
            Set<Integer> visited = new HashSet<>();
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(house1);

            while (!queue.isEmpty()) {
                int current = queue.poll();
                if (current == house2) {
                    return false;
                }
                visited.add(current);
                for (int neighbor : graph.getOrDefault(current, new HashSet<>())) {
                    if (!visited.contains(neighbor)) {
                        queue.offer(neighbor);
                    }
                }
            }

            return true;
        }

        public static void main(String[] args) {
            Q3aFriendRequests fr = new Q3aFriendRequests();
            int[][] restrictions = {{0, 1}, {1, 2}, {2, 3}};
            int[][] requests = {{0, 4}, {1, 2}, {3, 1}, {3, 4}};
            List<String> result = fr.checkRequests(5, restrictions, requests);
            System.out.println(result); // Output: [approved, denied, approved, denied]
        }
    }



