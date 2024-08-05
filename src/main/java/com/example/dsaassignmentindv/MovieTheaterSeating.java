package com.example.dsaassignmentindv;


import java.util.TreeSet;

public class MovieTheaterSeating {

        public static boolean canSitTogether(int[] nums, int indexDiff, int valueDiff) {
            TreeSet<Integer> window = new TreeSet<>();

            for (int i = 0; i < nums.length; i++) {
                // Check for any seat in the current window within valueDiff range
                Integer floor = window.floor(nums[i] + valueDiff);
                Integer ceiling = window.ceiling(nums[i] - valueDiff);

                if ((floor != null && floor >= nums[i]) || (ceiling != null && ceiling <= nums[i])) {
                    return true;
                }

                // Add the current seat to the window
                window.add(nums[i]);

                // Maintain the sliding window size by removing elements out of range
                if (i >= indexDiff) {
                    window.remove(nums[i - indexDiff]);
                }
            }

            return false;
        }

        public static void main(String[] args) {
            int[] nums1 = {2, 3, 5, 4, 9};
            int indexDiff1 = 2;
            int valueDiff1 = 1;
            System.out.println(canSitTogether(nums1, indexDiff1, valueDiff1));  // Output: true

            int[] nums2 = {1, 5, 9, 13, 17};
            int indexDiff2 = 2;
            int valueDiff2 = 2;
            System.out.println(canSitTogether(nums2, indexDiff2, valueDiff2));  // Output: false
        }
    }


