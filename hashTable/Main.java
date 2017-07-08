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
