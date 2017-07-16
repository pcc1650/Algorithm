// 55
// backtracking solution. TLE.
public boolean canJump(int[] nums) {
    return canJumpFromPosition(0, nums);
}

public boolean canJumpFromPosition(int position, int[] nums) {
    if(position == nums.length - 1)
        return true;
    int furtherPosition = Math.min(nums.length, position + nums[position]);
    for(int i = position + 1; i <= furtherPosition; i++) {
        if(canJumpFromPosition(i, nums))
            return true;
    }
    return false;
}
// Top-down solution. Stack overflow.
enum Index {
    GOOD, BAD, UNKNOWN
}

Index[] memo;

public boolean canJump(int[] nums) {
    memo = new Index[nums.length];
    for(int i = 0; i < nums.length; i++)
        memo[i] = Index.UNKNOWN;
    memo[nums.length - 1] = Index.GOOD;
    return canJumpFromPosition(0, nums);
}

public boolean canJumpFromPosition(int position, int[] nums) {
    if(memo[position] != Index.UNKNOWN)
        return memo[position] == Index.GOOD ? true : false;
    int furtherPosition = Math.min(nums.length, position + nums[position]);
    for(int i = position + 1; i <= furtherPosition; i++) {
        if(canJumpFromPosition(i, nums)){
            memo[i] = Index.GOOD;
            return true;
        }
    }
    memo[position] = Index.BAD;
    return false;
}
// Bottom-up solution. TLE.
enum Index {
    GOOD, BAD
}

Index[] memo;

public boolean canJump(int[] nums) {
    memo = new Index[nums.length];
    for(int i = 0; i < nums.length; i++)
        memo[i] = Index.BAD;
    memo[nums.length - 1] = Index.GOOD;
    for(int i = nums.length - 2; i >= 0; i--) {
        int furtherPosition = Math.min(i + nums[i], nums.length - 1);
        for(int j = i + 1; j <= furtherPosition; j++) {
            if(memo[j] == Index.GOOD){
                memo[i] = Index.GOOD;
                break;
            }
        }
    }
    return memo[0] == Index.GOOD;
}
// Greedy. Correct.
public boolean canJump(int[] nums) {
    int lastPos = nums.length - 1;
    for(int i = nums.length - 1; i >= 0; i--) {
        if(i + nums[i] >= lastPos){
            lastPos = i;
        }
    }
    return lastPos == 0;
}
