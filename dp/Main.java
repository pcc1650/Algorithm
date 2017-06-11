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
    public static void main(String[] args){
        Main m = new Main();
        int[] nums = new int[]{23, 2, 4, 6, 7};
        int s = 6;
        System.out.println(m.checkSubarraySum(nums, s));
    }
}
