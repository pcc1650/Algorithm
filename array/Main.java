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
