public class Solution {
    // 169 wrong version [3, 3, 3, 4, 5, 6]
    public int majorityElement(int[] nums){
        int major = nums[0];
        int count = 1;
        for(int i = 1; i < nums.length; i++) {
            if(major == nums[i]){
                count += 1;
            }
            else {
                count -= 1;
                if(count == 0) {
                    count = 1;
                    major = nums[i];
                }
            }
        }
        return major;
    }
    // 169 
    public int majorityElement(int[] nums) {
        int major = nums[0];
        int count = 1;
        for(int i = 1; i < nums.length; i++) {
            if(count == 0) {
                    count = 1;
                    major = nums[i];
                }
            else if(major == nums[i]){
                count += 1;
            }
            else {
                count -= 1;
            }
        }
        return major;
    }
}
