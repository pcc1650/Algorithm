package com.company;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    public int findTargetSumWays(int[] nums, int s) {
       int sum = 0;
       for(int i: nums)
           sum += i;
       return s > sum || (s + sum) % 2 == 1 ? 0 : subsetSum(nums, (sum + s) / 2);
    }
    public int subsetSum(int[] nums, int s) {
        int[] dp = new int[s + 1];
        dp[0] = 1;
        for(int i: nums)
            for(int j = s; j >= i; j--)
                dp[j] += dp[j - i];
        return dp[s];
    }
    public boolean checkSubarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if(k != 0)
                sum %= k;
            Integer temp = map.get(sum);
            if(temp != null) {
                if(i - temp > 1)
                    return true;
            }
            else
                map.put(sum, i);
        }
        return false;
    }
    public static void main(String[] args){
        Main m = new Main();
        int[] nums = new int[]{23, 2, 4, 6, 7};
        int s = 6;
        System.out.println(m.checkSubarraySum(nums, s));
    }
}