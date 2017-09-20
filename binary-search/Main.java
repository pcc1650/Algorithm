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
// add 167
public int[] twoSum(int[] numbers, int target) {
    int[] res = new int[2];
    int left = 0;
    int right = numbers.length - 1;
    while(left <= right) {
        int total = numbers[left] + numbers[right];
        if(total == target)
            return new int[]{left + 1, right + 1};
        else if(total > target){
            right -= 1;
        }
        else 
            left += 1;
    }
    return res;
}
// 35
public int searchInsert(int[] nums, int target) {
    if(nums.length == 0)
        return 0;
    int left = 0;
    int right = nums.length - 1;
    while(left <= right){
        int mid = left + (right - left) / 2;
        if(nums[mid] == target)
            return mid;
        else if(nums[mid] > target){
            right = mid - 1;
        }
        else
            left = mid + 1;
    }
    return right + 1;
}
