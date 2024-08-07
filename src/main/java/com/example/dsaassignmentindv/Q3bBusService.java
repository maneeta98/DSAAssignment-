package com.example.dsaassignmentindv;

import java.util.Arrays;

public class Q3bBusService {

        public static void optimizeBoarding(int[] head, int k) {
            for (int i = 0; i < head.length; i += k) {
                int left = i;
                int right = Math.min(i + k - 1, head.length - 1);
                while (left < right) {
                    int temp = head[left];
                    head[left] = head[right];
                    head[right] = temp;
                    left++;
                    right--;
                }
            }
        }

        public static void main(String[] args) {
            int[] passengers = {1, 2, 3, 4, 5};
            int k = 2;
            optimizeBoarding(passengers, k);
            System.out.println(Arrays.toString(passengers));
        }
    }


