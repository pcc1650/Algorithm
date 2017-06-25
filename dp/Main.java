package com.company;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
	// 494
    public int findTargetSumWays(int[] nums, int s) {
       int sum = 0;
       for(int i: nums)
           sum += i;
       return s > sum || (s + sum) % 2 == 1 ? 0 : subsetSum(nums, (sum + s) / 2);
    }
	// 494
    public int subsetSum(int[] nums, int s) {
        int[] dp = new int[s + 1];
        dp[0] = 1;
        for(int i: nums)
            for(int j = s; j >= i; j--)
                dp[j] += dp[j - i];
        return dp[s];
    }
	// 523
    public boolean checkSubarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if(k != 0)
                sum %= k;
            Integer temp = map.get(sum);
            if(temp != null) {
                if(i - temp > 1)
                    return true;
            }
            else
                map.put(sum, i);
        }
        return false;
    }
	// 516
    public int longestPalindromeSubseq(String s) {
        int[][] dp = new int[s.length()][s.length()];
        // if s[i] == s[j] dp[j][i] = dp[j+1][i-1] + 2
        // if s[i] != s[j] dp[j][i] = Math.max(dp[j+1][i], dp[j][i-1])
        // Initialization: dp[i][i] = 1
        for(int i = 0; i < s.length(); i++) {
            dp[i][i] = 1;
            for(int j = i - 1; j >= 0; j--){
                if(s.charAt(i) == s.charAt(j)) {
                    dp[j][i] = dp[j + 1][i - 1] + 2;
                }
                else {
                    dp[j][i] = Math.max(dp[j + 1][i], dp[j][i - 1]);
                }
            }
        }
        return dp[0][s.length() - 1];
    }
	// 5
	public String longestPalindrome(String s) {
        if(s == null || s.length() < 2)
            return s;
        int len = s.length();
        int index = -1;
        int maxLen = 0;
        for(int i = 0; i < len - 1; i++) {
            int maxOdd = findLongest(s, i, i);
            int maxEven = findLongest(s, i, i + 1);
            if(maxLen < Math.max(maxOdd, maxEven)){
                index = i;
                maxLen = Math.max(maxOdd, maxEven);
            }
        }
        if(maxLen % 2 == 0) {
            return s.substring(index - (maxLen / 2) + 1, index + (maxLen / 2) + 1);
        }
        else {
            return s.substring(index - (maxLen / 2), index + (maxLen / 2) + 1);
        }
    }
    public int findLongest(String s, int i, int j) {
        while(i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            i -= 1;
            j += 1;
        }
        return j - i - 1;
    }
	// 403
    public boolean canCross(int[] stones) {
        if(stones.length == 0)
            return true;
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
        map.put(0, new HashSet<Integer>());
        map.get(0).add(1);
        for(int i = 1; i < stones.length; i++) {
            map.put(stones[i], new HashSet<Integer>());
        }
        for(int i = 0 ; i < stones.length - 1; i++) {
            int stone = stones[i];
            for(int step : map.get(stone)) {
                int reach = step + stone;
                if(reach == stones[stones.length - 1])
                    return true;
                HashSet<Integer> set = map.get(reach);
                if(set != null){
                    set.add(step);
                    if(step - 1 > 0)
                        set.add(step - 1);
                    set.add(step + 1);
                }
            }
        }
        return false;
    }
    // 467
    public int findSubstringInWraproundString(String p) {
        int[] count = new int[26];
        int maxL = 0;
        for(int i = 0; i < p.length(); i++) {
            if(i > 0 && (p.charAt(i) - p.charAt(i - 1) == 1 || (p.charAt(i) == 'a' && p.charAt(i - 1) == 'z'))){
                maxL += 1;
            }
            else
                maxL = 1;
            int index = p.charAt(i) - 'a';
            count[index] = Math.max(count[index], maxL);
        }
        int sum = 0;
        for(int i = 0; i < 26; i++)
            sum += count[i];
        return sum;
    }
    // 413
    public int numberOfArithmeticSlices(int[] A) {
        int curr = 0, sum = 0;
        for(int i = 2; i < A.length; i++) {
            if(A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
                curr += 1;
                sum += curr;
            }
            else
                curr = 0;
        }
        return sum;
    }
    // 464
    public int numberOfArithmeticSlices(int[] A) {
        int result = 0;
        Map<Integer, Integer>[] map = new Map[A.length];
        for(int i = 0; i < A.length; i++) {
            map[i] = new HashMap<>();
            for(int j = 0; j < i; j++){
                long diff = (long)A[i] - A[j];
                if(diff <= Integer.MIN_VALUE || diff >= Integer.MAX_VALUE){
                    continue;     
                }
                int d = (int)diff;
                int c1 = map[i].getOrDefault(d, 0);
                int c2 = map[j].getOrDefault(d, 0);
                result += c2;
                map[i].put(d, c1 + c2 + 1);
            }
        }
        return result;
    }
    // 472
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        HashMap<String, Integer> map = new HashMap<>();
        List<String> result = new LinkedList<>();
        for(String word: words)
            map.put(word, 1);
        for(String word: words){
            if(checkConcatenated(word, map, 0))
                result.add(word);
        }
        return result;
    }
    public boolean checkConcatenated(String word, Map map, int count) {
        if(word.equals("") && count >= 2) {
            return true;
        }
        for(int i = 1; i <= word.length(); i++) {
            if((int)map.getOrDefault(word.substring(0, i), 0) == 1) {
                if((int)map.getOrDefault(word.substring(i), 0) == 1 || checkConcatenated(word.substring(i), map, count + 1)) {
                    return true;
                }
            }
        }
        return false;
    }
	// 139
    public boolean wordBreak(String s, List<String> wordDict) {
        if(s == null || s.length() == 0)
            return false;
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for(int i = 1; i <= s.length(); i++) {
            for(int j = 0; j < i; j++) {
                if(wordDict.contains(s.substring(j, i))) {
                    dp[i] = dp[i] || dp[j];
                }
            }
        }
        return dp[s.length()];
    }
    // 517
    public int findMinMoves(int[] machines){
        int total = 0;
        for(int machine: machines)
            total += machine;
        if(total % machines.length != 0)
            return -1;
        int avg = total / machines.length, cnt = 0, max = 0;
        for(int load: machines){
            cnt += load - avg;
            max = Math.max(Math.max(max, Math.abs(cnt)), load - avg);
        }
        return max;
    }
    // 514
    public int findRotateSteps(String ring, String key) {
        int rLen = ring.length();
        int kLen = key.length();
        int dp[][] = new int[kLen + 1][rLen];
        
        for(int i = kLen - 1; i >= 0; i--) {
            for(int j = 0; j < rLen; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                for(int k = 0; k < rLen; k++) {
                    if(ring.charAt(k) == key.charAt(i)) {
                        int diff = Math.abs(k - j);
                        int step = Math.min(diff, rLen - diff);
                        dp[i][j] = Math.min(dp[i][j], dp[i + 1][k] + step);
                    }
                }
            }
        }
        return dp[0][0] + kLen;
    }
    // 514
    public int findRotateSteps(String ring, String key) {
        int n = ring.length(), m = key.length();
        int[][] dp = new int[m + 1][n];
        int[][] clock = preproc(ring, 1), anti = preproc(ring, -1);
        for (int i = m - 1; i >= 0; --i) {
            int idx = key.charAt(i) - 'a';
            for (int j = 0; j < n; ++j) { // fill dp[i][j]
                int p = clock[j][idx];
                int q = anti[j][idx];
                dp[i][j] = Math.min(dp[i + 1][p] + (j + n - p) % n, dp[i + 1][q] + (q + n - j) % n);
            }
        }
        return dp[0][0] + m;
    }
    int[][] preproc(String r, int inc) {
        int n = r.length();
        int[][] ans = new int[n][26];
        int[] map = new int[26];
        for (int i = 0, j = 0; j < n * 2 - 1; ++j) {
            map[r.charAt(i) - 'a'] = i;
            System.arraycopy(map, 0, ans[i], 0, 26);
            i = (i + inc + n) % n;
        }
        return ans;
    }

    // 552 
	static final int M = 1000000007;
    
    public int checkRecord(int n) {
        long[] PorL = new long[n + 1]; // end with P or L
        long[] P = new long[n + 1]; // end with P
        PorL[0] = P[0] = 1;
        PorL[1] = 2;
        P[1] = 1;
        
        for(int i = 2; i <= n; i++) {
            P[i] = PorL[i - 1];
            // not use PorL[i-1] * 2 to limit L in 2
            PorL[i] = (P[i] + P[i - 1] + P[i - 2]) % M;
        }
        
        long res = PorL[n];
        for(int i = 0; i < n; i++) {
            // ???
            long s = (PorL[i] * PorL[n - i - 1]) % M;
            res = (res + s) % M;
        }
        
        return (int)res;
    } 
    public static void main(String[] args){
        Main m = new Main();
        int[] nums = new int[]{23, 2, 4, 6, 7};
        int s = 6;
        System.out.println(m.checkSubarraySum(nums, s));
    }
}
