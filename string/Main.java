// 696 contest 54
public int countBinarySubstrings(String s) {
    int index = 0;
    int res = 0;
    int[] group = new int[s.length()];
    group[0] = 1;
    for(int i = 1; i < s.length(); i++) {
        if(s.charAt(i) == s.charAt(i - 1))
            group[index] += 1;
        else
            group[++index] = 1;
    }
    for(int i = 0; i < index; i++) {
        res += Math.min(group[i], group[i + 1]);
    }
    return res;
}
