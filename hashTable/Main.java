// 242
public boolean isAnagram(String s, String t) {
    char[] sCharArray = s.toCharArray();
    char[] tCharArray = t.toCharArray();
    Arrays.sort(sCharArray);
    Arrays.sort(tCharArray);
    String sString = new String(sCharArray);
    String tString = new String(tCharArray);
    return sString.equals(tString);
}
// 274
public int hIndex(int[] citations) {
    if(citations.length == 0)
        return 0;
    int[] count = new int[citations.length + 1];
    for(int i = 0; i < citations.length; i++) {
        if(citations[i] > citations.length)
            count[citations.length] += 1;
        else
            count[citations[i]] += 1;
    }
    int total = 0;
    for(int i = citations.length; i >= 0; i--) {
        total += count[i];
        if(total >= i)
            return i;
    }
    return 0;
}
