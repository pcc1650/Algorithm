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
// add 475
public int findRadius(int[] houses, int[] heaters) {
    int res = 0;
    Arrays.sort(heaters);
    for(int house: houses) {
        int index = Arrays.binarySearch(heaters, house);
        if(index < 0) {
            index = -(index + 1);
        }
        int dist1 = index - 1 >= 0 ? house - heaters[index - 1] : Integer.MAX_VALUE;
        int dist2 = index < heaters.length ? heaters[index] - house: Integer.MAX_VALUE;
        res = Math.max(res, Math.min(dist1, dist2));
    }
    return res;
}
// 441
public int arrangeCoins(int n) {
    int left = 0;
    int right = n;
    while(left <= right){
        long mid = left + (right - left) / 2;
        if((1 + mid) * mid >>> 1 <= n && (2 + mid) * (mid + 1) >>> 1 > n)
            return (int)mid;
        else if((1 + mid) * mid >>> 1 <= n)
            left = (int)mid + 1;
        else
            right = (int)mid - 1;
    }
    return -1;
}
// 69
public int mySqrt(int x) {
    if(x == 0)
        return 0;
    if(x == 1)
        return 1;
    int left = 1;
    int right = x / 2;
    while(left <= right) {
        long mid = left + (right - left) / 2;
        if(mid * mid <= x && (mid + 1) * (mid + 1) > x)
            return (int)mid;
        else if(mid * mid < x) {
            left = (int)mid + 1;
        }
        else
            right = (int)mid - 1;
    }
    return -1;
}
// 367
public boolean isPerfectSquare(int num) {
    if(num == 1)
        return true;
    int left = 1;
    int right = num / 2;
    while(left <= right) {
        long mid = left + (right - left) / 2;
        if(mid * mid == num)
            return true;
        else if(mid * mid > num) {
            right = (int)mid - 1;
        }
        else
            left = (int)mid + 1;
    }
    return false;
}
// 15
public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> res = new LinkedList<>();
    for(int i = 0; i < nums.length - 2; i++) {
        if(i == 0 || nums[i] != nums[i - 1]){
            int sum = 0 - nums[i];
            int low = i + 1;
            int high = nums.length - 1;
            while(low < high) {
                if(nums[low] + nums[high] == sum){
                    res.add(Arrays.asList(nums[i], nums[low], nums[high]));
                    while(low < high && nums[low] == nums[low + 1]) low += 1;
                    while(low < high && nums[high] == nums[high - 1]) high -= 1;
                    low += 1;
                    high -= 1;
                }
                else if(nums[low] + nums[high] < sum)
                    low += 1;
                else
                    high -= 1;
            }
        }
    }
    return res;
}
// 18
public List<List<Integer>> fourSum(int[] nums, int target) {
    List<List<Integer>> res = new LinkedList<>();
    Arrays.sort(nums);
    for(int i = 0; i < nums.length - 3; i++) {
        if(i == 0 || nums[i] != nums[i - 1]) {
            threeSum(nums, target, i + 1, res, nums[i]);
        }
    }
    return res;
}
private void threeSum(int[] nums, int target, int index, List<List<Integer>> res, int first) {
    for(int i = index; i < nums.length - 2; i++) {
        if(i == index || nums[i] != nums[i - 1]){
            int low = i + 1, high = nums.length - 1, sum = target - nums[i] - first;
            while(low < high) {
                if(nums[low] + nums[high] == sum) {
                    res.add(Arrays.asList(first, nums[i], nums[low], nums[high]));
                    while(low < high && nums[low] == nums[low + 1])
                        low += 1;
                    while(low < high && nums[high] == nums[high - 1])
                        high -= 1;
                    low += 1;
                    high -= 1;
                }
                else if(nums[low] + nums[high] < sum)
                    low += 1;
                else
                    high -= 1;
            }
        }
    }
}
// 454
public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
    HashMap<Integer, Integer> map = new HashMap<>();
    for(int i = 0; i < A.length; i++) {
        for(int j = 0; j < B.length; j++) {
            int sum = A[i] + B[j];
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
    }
    int res = 0;
    for(int i = 0; i < C.length; i++) {
        for(int j = 0; j < D.length; j++) {
            res += map.getOrDefault(-(C[i] + D[j]), 0);
        }
    }
    return res;
}
// 16
public int threeSumClosest(int[] nums, int target) {
    Arrays.sort(nums);
    int result = nums[0] + nums[1] + nums[2];
    for(int i = 0; i < nums.length - 2; i++) {
        int low = i + 1, high = nums.length - 1;
        int sum = nums[low] + nums[high] + nums[i];
        while(low < high){
            sum = nums[low] + nums[high] + nums[i];
            if(sum > target)
                high -= 1;
            else
                low += 1;
        }
        if(Math.abs(sum - target) < Math.abs(result - target)){
                result = sum;
            }
    }
    return result;
}
