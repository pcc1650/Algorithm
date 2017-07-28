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

// 134
public int canCompleteCircuit(int[] gas, int[] cost) {
    int sumGas = 0;
    int sumCost = 0;
    int start = 0;
    int tank = 0;
    for(int i = 0; i < gas.length; i++) {
        sumGas += gas[i];
        sumCost += cost[i];
        tank += gas[i] - cost[i];
        if(tank < 0){
            start = i + 1;
            tank = 0;
        }
    }
    if(sumGas < sumCost)
        return -1;
    return start;
}

// 406
// pay attention to comparator in priority queue.
public int[][] reconstructQueue(int[][] people) {
    if(people == null || people.length == 0)
        return people;
    PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> {
            if(b[0] == a[0])
                return a[1] - b[1];
            else
                return b[0] - a[0];
        }
    );
    LinkedList<int[]> resList = new LinkedList<>();
    for(int i = 0; i < people.length; i++) {
        queue.add(people[i]);
    }

    for(int i = 0; i < people.length; i++) {
        int[] temp = queue.poll();
        if(temp[1] > resList.size()){
            resList.add(temp);
            continue;
        }
        resList.add(temp[1], temp);
    }
    
    int[][] res = new int[people.length][2];
    for(int i = 0; i < people.length; i++) {
        res[i][0] = resList.get(i)[0];
        res[i][1] = resList.get(i)[1];
    }
    return res;
}
// 135
// Brute force. TLE. O(n2)
// Two array method. Accepted. O(n)
public int candy(int[] ratings) {
    int[] candies = new int[ratings.length];
    int[] LTRcandies = new int[ratings.length];
    int[] RTLcandies = new int[ratings.length];
    Arrays.fill(LTRcandies, 1);
    Arrays.fill(RTLcandies, 1);
    int sum = 0;
    for (int i = 1; i < ratings.length; i++) {
        if (ratings[i] > ratings[i - 1] && LTRcandies[i] <= LTRcandies[i - 1]) {
            LTRcandies[i] = LTRcandies[i - 1] + 1;
        }
    }
    for (int i = ratings.length - 2; i >= 0; i--) {
        if (ratings[i] > ratings[i + 1] && RTLcandies[i] <= RTLcandies[i + 1]) {
            RTLcandies[i] = RTLcandies[i + 1] + 1;
        }
    }
    for(int i = 0; i < ratings.length; i++)
        candies[i] = Math.max(LTRcandies[i], RTLcandies[i]);
    for(int candy: candies)
        sum += candy;
    return sum;
}
// 455
public int findContentChildren(int[] g, int[] s) {
    Arrays.sort(g);
    Arrays.sort(s);
    int count = 0;
    for(int j = 0; count < g.length && j < s.length; j++) {
        if(s[j] >= g[count])
            count += 1;
    }
    return count;
}
public String removeDuplicateLetters(String s) {
    int[] count = new int[26];
    int pos = 0;
    for(int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a'] += 1;
    }
    for(int i = 0; i < s.length(); i++) {
        if(s.charAt(i) < s.charAt(pos))
            pos = i;
        if(--count[s.charAt(i) - 'a'] <= 0 )
            break;
    }
    return s.length() == 0 ? "" : s.charAt(pos) + removeDuplicateLetters(s.substring(pos + 1).replaceAll("" + s.charAt(pos), ""));
}
// 452
public int findMinArrowShots(int[][] points) {
    if(points == null || points.length == 0) {
        return 0;
    }
    Arrays.sort(points, (a, b) -> (a[1] - b[1]));
    int arrPos = points[0][1];
    int count = 1;
    for(int i = 0 ; i < points.length; i++) {
        if(arrPos >= points[i][0] && arrPos <= points[i][1]) {
            continue;
        }
        arrPos = points[i][1];
        count += 1;
    }
    return count;
}
// 402
public String removeKdigits(String num, int k) {
    int digits = num.length() - k;
    char[] resChar = new char[num.length()];
    int top = 0;
    
    for(int i = 0; i < num.length(); i++) {
        char c = num.charAt(i);
        while(top > 0 && resChar[top - 1] > c && k > 0){
            top--;
            k--;
        }
        resChar[top++] = c;
    }
    
    int idx = 0;
    while (idx < digits && resChar[idx] == '0') idx++;
    return idx == digits? "0": new String(resChar, idx, digits - idx);
}
// 630
public int scheduleCourse(int[][] courses) {
    if(courses == null || courses.length == 0)
        return 0;
    int ini = 0;
    int last = 0;
    Arrays.sort(courses, (a, b) -> (a[1] - b[1]));
    PriorityQueue<int []> queue  = new PriorityQueue<>((a, b) -> (b[0] - a[0]));
    for(int i = 0; i < courses.length; i++) {
        if(ini + courses[i][0] <= courses[i][1]){
            ini += courses[i][0];
            queue.add(courses[i]);
        }
        else {
            if(!queue.isEmpty() && courses[i][0] <= queue.peek()[0]){
                ini += courses[i][0] - queue.poll()[0];
                queue.add(courses[i]);
            }
        }
    }
    return queue.size();
}
// 330
public int minPatches(int[] nums, int n) {
    long miss = 1;
    int i = 0, count = 0;
    while(miss <= n) {
        if(i < nums.length && nums[i] <= miss) {
            miss += nums[i];
            i++;
        }
        else {
            count += 1;
            miss += miss;
        }
    }
    return count;
}
// 435
public int eraseOverlapIntervals(Interval[] intervals) {
    if(intervals == null || intervals.length == 0)
        return 0;
    int count = 0;
    Arrays.sort(intervals, (a, b) -> (a.end - b.end));
    int endPos = intervals[0].end;
    for(int i = 1; i < intervals.length; i++) {
        if(intervals[i].start < endPos) {
            count += 1;
            continue;
        }
        endPos = intervals[i].end;
    }
    return count;
}
