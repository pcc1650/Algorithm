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

// 45 
// dp. TLE.
public int jump(int[] nums) {
    int[] dp = new int[nums.length];
    for(int i = 1; i < nums.length; i++)
        dp[i] = Integer.MAX_VALUE;
    for(int i = 0; i < nums.length; i++) {
        for(int j = 1; j <= nums[i]; j++){
            if(i + j < nums.length){
                dp[i + j] = Math.min(dp[i + j], dp[i] + 1);
            }
        }
    }
    return dp[nums.length - 1];
}
// Greedy. Correct
public int jump(int[] nums) {
    int lastStep = 0, furtherStep = 0, count = 0;
    for(int i = 0; i < nums.length - 1; i++) {
        furtherStep = Math.max(furtherStep, i + nums[i]);
        if(i == lastStep) {
            lastStep = furtherStep;
            count += 1;
        }
    }
    return count;
}

// 621
// Method 1
public int leastInterval(char[] tasks, int n) {
    int[] map = new int[26];
    for(char c: tasks)
        map[c - 'A'] += 1;
    Arrays.sort(map);
    int time = 0;
    while(map[25] > 0) {
        int i = 0;
        while(i <= n) {
            if(map[25] == 0)
                break;
            if(i < 26 && map[25 - i] > 0)
                map[25 - i] -= 1;
            time += 1;
            i += 1;
        }
        Arrays.sort(map);
    }
    return time;
}
