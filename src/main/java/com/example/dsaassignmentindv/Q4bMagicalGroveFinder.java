package com.example.dsaassignmentindv;

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Solution {
        int maxSum = 0;

        public int largestMagicalGrove(TreeNode root) {
            findLargestGrove(root);
            return maxSum;
        }

        private int[] findLargestGrove(TreeNode node) {
            if (node == null) {
                return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
            }

            int[] left = findLargestGrove(node.left);
            int[] right = findLargestGrove(node.right);

            if (node.val > left[1] && node.val < right[0]) {
                int sum = node.val + left[2] + right[2];
                maxSum = Math.max(maxSum, sum);
                return new int[]{Math.min(node.val, left[0]), Math.max(node.val, right[1]), sum};
            } else {
                return new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 0};
            }
        }
    }

    public class Q4bMagicalGroveFinder {
        public static void main(String[] args) {
            TreeNode root = new TreeNode(1);
            root.left = new TreeNode(4);
            root.right = new TreeNode(3);
            root.left.left = new TreeNode(2);
            root.left.right = new TreeNode(4);
            root.right.left = new TreeNode(2);
            root.right.right = new TreeNode(5);
            root.right.right.right = new TreeNode(6);

            Solution solution = new Solution();
            int largestSum = solution.largestMagicalGrove(root);
            System.out.println("Largest Magical Grove: " + largestSum);
        }
    }


