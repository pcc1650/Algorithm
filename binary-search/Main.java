package com.company;

import java.util.*;

public class Main {
    // 410
    public int splitArray(int[] nums, int m) {
        int max = 0, sum = 0;
        for(int num : nums) {
            if(num > max)
                max = num;
            sum += num;
        }
        if(m == 1)
            return sum;
        int l = max, r = sum;
        while(l <= r) {
            int mid = (l + r) / 2;
            if(validate(mid, nums, m))
                r = mid - 1;
            else
                l = mid + 1;
        }
        return l;
    }
    public boolean validate(int target, int[] nums, int m) {
        int count = 1;
        int total = 0;
        for(int num : nums) {
            total += num;
            if(total > target){
                total = num;
                count += 1;
                if(count > m)
                    return false;
            }
        }
        return true;
    }
    public static void main(String[] args){
        Main m = new Main();
        int[] nums = new int[]{7,2,5,10,8};
        int n = 2;
        System.out.println(m.splitArray(nums, n));
    }
}
