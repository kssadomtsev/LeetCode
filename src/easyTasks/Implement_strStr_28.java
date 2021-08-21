package easyTasks;

// https://leetcode.com/problems/implement-strstr/

public class Implement_strStr_28 {
    public static int strStr(String haystack, String needle) {
        if(needle.length() == 0) {
            return 0;
        }
        int[] d = new int[256];
        for (int i = 0; i < d.length; i++) {
            d[i] = needle.length();
        }
        for (int i = needle.length()-2; i >= 0; i--){
            if (d[(int) needle.charAt(i)] == needle.length()){
                d[(int) needle.charAt(i)] = needle.length() - i - 1;
            }
        }

        int i = needle.length() - 1;
        while (i < haystack.length()) {
            int j = needle.length() - 1;
            int k = i;
            while ((j >= 0) && (needle.charAt(j) == haystack.charAt(k))){
                j--;
                k--;
            }
            if (j == -1){
                return i - needle.length() + 1;
            } else if (k==i) {
                i = i + d[(int) haystack.charAt(i)];
            } else {
                i = i + d[(int) needle.charAt(needle.length() - 1)];
            }
        }


        return -1;
    }


    public static void main(String[] args) {
        System.out.println(strStr("mississippi", "issi"));

    }
}
