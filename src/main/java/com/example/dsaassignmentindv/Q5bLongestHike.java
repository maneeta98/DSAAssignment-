package com.example.dsaassignmentindv;

public class Q5bLongestHike {
        public static void main(String[] args) {
            int[] trail = {4, 2, 1, 4, 3, 4, 5, 8, 15};
            int k = 3;
            System.out.println(longestHike(trail, k));
        }

        public static int longestHike(int[] nums, int k) {
            int maxLength = 0;
            int currentLength = 0;
            int lastAltitude = Integer.MIN_VALUE;

            for (int i = 0; i < nums.length; i++) {
                if (nums[i] > lastAltitude) {
                    if (lastAltitude == Integer.MIN_VALUE || (nums[i] - lastAltitude) <= k) {
                        currentLength++;
                        lastAltitude = nums[i];
                    } else {
                        maxLength = Math.max(maxLength, currentLength);
                        currentLength = 1;
                        lastAltitude = nums[i];
                    }
                } else {
                    maxLength = Math.max(maxLength, currentLength);
                    currentLength = 0;
                    lastAltitude = Integer.MIN_VALUE;
                }
            }
            return Math.max(maxLength, currentLength);
        }
    }


