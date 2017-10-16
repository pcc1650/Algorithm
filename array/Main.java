public class Solution {
	// Java initialization
    // 384
    private int[] nums;
    private Random random;
    public Solution(int[] nums) {
		// this.nums = nums;
        this.nums = nums;
        random = new Random();
    }
    
    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        return nums;
    }
    
    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        if(nums == null) return nums;
		// array.clone()
        int[] numsClone = nums.clone();
        int len = this.nums.length;
        for(int i = len - 1; i >= 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = numsClone[index];
            numsClone[index] = numsClone[i];
            numsClone[i] = temp;
        }
        return numsClone;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */
// 66 
public int[] plusOne(int[] digits) {
    if(digits == null) return null;
    int len = digits.length;
    for(int i = len - 1; i >= 0; i--) {
        if(digits[i] < 9){
            digits[i] += 1;
            return digits;
        }
        else {
            digits[i] = 0;
        }
    }
    int[] res = new int[len + 1];
    res[0] = 1;
    for(int j = 1; j < len + 1; j++)
        res[j] = digits[j - 1];
    return res;
}
// 400
public int findNthDigit(int n) {
    int len = 1;
    // long
    long count = 9;
    int start = 1;
    while(n > len * count) {
        n -= len * count;
        len += 1;
        count *= 10;
        start *= 10;
    }
    start += (n - 1) / len;
    String s = Integer.toString(start);
    // Character.getNumericValue
    return Character.getNumericValue(s.charAt((n - 1) % len));
}
// 532
public int findPairs(int[] nums, int k) {
    if(k < 0 || nums == null || nums.length == 0)
        return 0;
    Map<Integer, Integer> hs = new HashMap<>();
    int count = 0;
    for(int num : nums){
        hs.put(num, hs.getOrDefault(num, 0) + 1);
    }
    for (Map.Entry<Integer, Integer> entry : hs.entrySet()) {
        if(k == 0) {
            if(entry.getValue() >= 2)
                count += 1;
        }
        else {
            if(hs.containsKey(entry.getKey() + k))
                count += 1;
        }
    }
    return count;
}
// 697 contest 55
public int findShortestSubArray(int[] nums) {
    int res = Integer.MAX_VALUE;
    HashMap<Integer, Integer[]> map = calculateDegree(nums);
    int degree = map.get(-1)[0];
    for(Map.Entry<Integer, Integer[]> entry: map.entrySet()) {
        if(entry.getValue()[0] == degree){
            if(entry.getValue()[2] - entry.getValue()[1] + 1< res)
                res = entry.getValue()[2] - entry.getValue()[1] + 1;
        }
    }
    return res;
}
private HashMap<Integer, Integer[]> calculateDegree(int[] nums){
    HashMap<Integer, Integer[]> map = new HashMap<>();
    int degree = 0;
    for(int i = 0; i < nums.length; i++) {
        if(map.containsKey(nums[i])) {
            Integer[] arr = map.get(nums[i]);
            arr[0] += 1;
            arr[2] = i;
            map.put(nums[i], arr);
        }
        else {
            map.put(nums[i], new Integer[]{1, i, i});
        }
    }
    for(Map.Entry<Integer, Integer[]> entry: map.entrySet()) {
        if(entry.getValue()[0] > degree){
            degree = entry.getValue()[0];
        }
    }
    map.put(-1, new Integer[]{degree, 1, 50001});
    return map;
}
