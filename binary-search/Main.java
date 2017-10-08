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
// 29
public int divide(int dividend, int divisor) {
    if(divisor == 0 || (dividend == Integer.MIN_VALUE && divisor == -1))
        return Integer.MAX_VALUE;
    int sign = (dividend < 0 ^ divisor < 0) ? -1 : 1;
    long dvd = Math.abs((long)dividend);
    long dvs = Math.abs((long)divisor);
    int res = 0;
    while(dvd >= dvs) {
        long temp = dvs, multiple = 1;
        while (dvd >= (temp << 1)) {
            temp <<= 1;
            multiple <<= 1;
        }
        dvd -= temp;
        res += multiple;
    }
    return sign == 1 ? res : -res;
}
// 378 also can use PriorityQueue
public int kthSmallest(int[][] matrix, int k) {
    int low = matrix[0][0];
    int high = matrix[matrix.length - 1][matrix[0].length - 1] + 1;
    while(low < high) {
        int mid = low + (high - low) / 2;
        int count = 0, j = matrix[0].length - 1;
        for(int i = 0; i < matrix.length; i++) {
            while(j >= 0 && matrix[i][j] > mid)
                j--;
            count += (j + 1);
        }
        if(count < k)
            low = mid + 1;
        else
            high = mid;
    }
    return low;
}
// 33
public int search(int[] nums, int target) {
    if(nums.length == 0)
        return -1;
    int low = 0, high = nums.length - 1;
    while(low < high) {
        int mid = low + (high - low) / 2;
        if(nums[mid] > nums[high])
            low = mid + 1;
        else
            high = mid;
    }
    int rotation = low;
    low = 0;
    high = nums.length - 1;
    while(low <= high){
        int mid = low + (high - low) / 2;
        int reMid = (mid + rotation) % nums.length;
        if(nums[reMid] == target)
            return reMid;
        else if(nums[reMid] > target)
            high = mid - 1;
        else
            low = mid + 1;
    }
    return -1;
}
// 34
public int[] searchRange(int[] nums, int target) {
    int low = 0, high = nums.length - 1;
    int[] res = new int[]{-1, -1};
    if(nums.length == 0)
        return res;
    while(low < high){
        int mid = low + (high - low) / 2;
        if(nums[mid] < target){
            low = mid + 1;
        }
        else
            high = mid;
    }
    if(nums[low] == target)
        res[0] = low;
    low = 0; high = nums.length - 1;
    while(low < high){
        int mid = low + (high - low + 1) / 2;
        if(nums[mid] > target)
            high = mid - 1;
        else 
            low = mid;
    }
    if(nums[high] == target)
        res[1] = high;
    return res;
}
// 287 two pointers method
public int findDuplicate(int[] nums) {
    if(nums.length > 1){
        int slow = nums[0];
        int fast = nums[nums[0]];
        while(slow != fast){
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        fast = 0;
        while(slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
    return -1;
}
// 287 binary search method
public int findDuplicate(int[] nums) {
    int low = 1, high = nums.length - 1;
    while(low < high){
        int mid = low + (high - low) / 2;
        int count = 0;
        for(int num: nums)
            if(num <= mid)
                count += 1;
        if(count <= mid)
            low = mid + 1;
        else 
            high = mid;
    }
    return low;
}
// 690
public int getImportance(List<Employee> employees, int id) {
    int[] res = new int[]{0};
    countImportance(employees, id, res);
    return res[0];
}
private void countImportance(List<Employee> employees, int id, int[] res) {
    for(int i = 0; i < employees.size(); i++) {
        Employee e = employees.get(i);
        if(e.id == id){
            res[0] += e.importance;
            for(int j = 0; j < e.subordinates.size(); j++) {
                countImportance(employees, e.subordinates.get(j), res);
            }
            break;
        }
    }
}
// 690 hashtable
public int getImportance(List<Employee> employees, int id) {
    int[] res = new int[]{0};
    HashMap<Integer, Employee> map = new HashMap<>();
    for(Employee e: employees){
        map.put(e.id, e);
    }
    countImportance(map, id, res);
    return res[0];
}
private void countImportance(HashMap<Integer, Employee> map, int id, int[] res) {
    Employee e = map.get(id);
    res[0] += e.importance;
    for(int newId : e.subordinates){
        countImportance(map, newId, res);
    }
}
// 658
public List<Integer> findClosestElements(int[] arr, int k, int x) {
    int low = 0, high = arr.length - 1;
    List<Integer> res = new LinkedList<>();
    while(low < high) {
        int mid = low + (high - low) / 2;
        if(arr[mid] < x)
            low = mid + 1;
        else
            high = mid;
    }
    int i = low - 1, j = low;
    while(k-- > 0) {
        if(i < 0 || (j < arr.length && Math.abs(arr[i] - x) > Math.abs(arr[j] - x)))
            j += 1;
        else
            i -= 1;
    }
    i += 1;
    for(; i < j; i++)
        res.add(arr[i]);
    return res;
}
// 74
public boolean searchMatrix(int[][] matrix, int target) {
    if(matrix.length == 0 || matrix[0].length == 0)
        return false;
    int m = matrix.length, n = matrix[0].length;
    int low = 0, high = m - 1;
    while(low < high) {
        int mid = low + (high - low) / 2;
        if(matrix[mid][n - 1] < target)
            low = mid + 1;
        else high = mid;
    }
    int row = low;
    low = 0; high = n - 1;
    while(low <= high) {
        int mid = low + (high - low) / 2;
        if(matrix[row][mid] == target)
            return true;
        else if(matrix[row][mid] < target)
            low = mid + 1;
        else
            high = mid - 1;
    }
    return false;
}
// 240
public boolean searchMatrix(int[][] matrix, int target) {
    if(matrix.length == 0 || matrix[0].length == 0)
        return false;
    int row = 0, col = matrix[0].length - 1;
    while(row < matrix.length && col >= 0) {
        if(matrix[row][col] == target)
            return true;
        else if(matrix[row][col] < target)
            row += 1;
        else if(matrix[row][col] > target)
            col -= 1;
    }
    return false;
}
// 275
public int hIndex(int[] citations) {
    if(citations.length == 0)
        return 0;
    int low = 0, high = citations.length - 1;
    while(low <= high) {
        int mid = low + (high - low) / 2;
        if(citations[mid] == citations.length - mid)   
            return citations.length - mid;
        else if(citations[mid] > citations.length - mid)
            high = mid - 1;
        else
            low = mid + 1;
    }
    return citations.length - high - 1;
}
// 33 another method 
public int search(int[] nums, int target) {
    if(nums.length == 0)
        return -1;
    int low = 0, high = nums.length - 1;
    while(low <= high) {
        int mid = low + (high - low) / 2;
        if(nums[mid] == target) return mid;
        if(nums[low] <= nums[mid]){
            if(target < nums[mid] && target >= nums[low])
                high = mid - 1;
            else
                low = mid + 1;
        }
        else {
            if(target <= nums[high] && nums[mid] < target)
                low = mid + 1;
            else
                high = mid - 1;
        }
    }
    return -1;
}
// 81 
public boolean search(int[] nums, int target) {
    if(nums.length == 0)
        return false;
    int low = 0, high = nums.length - 1;
    while(low <= high) {
        int mid = low + (high - low) / 2;
        if(nums[mid] == target) return true;
        if(nums[low] < nums[mid]){
            if(target < nums[mid] && target >= nums[low])
                high = mid - 1;
            else
                low = mid + 1;
        }
        else if(nums[low] > nums[mid]) {
            if(target <= nums[high] && nums[mid] < target)
                low = mid + 1;
            else
                high = mid - 1;
        }
		// duplicate
        else {
            low += 1;
        }
    }
    return false;
}
