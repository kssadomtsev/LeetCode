package easyTasks;

// https://leetcode.com/problems/longest-continuous-increasing-subsequence/
public class LongestContinuousIncreasingSubsequence_674 {
    public int findLengthOfLCIS(int[] nums) {
        int longest = 1;
        int leftBorder = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] >= nums[i]) {
                leftBorder = i;
            }
            longest = Math.max(longest, i - leftBorder + 1);
        }

        return longest;

    }
}
