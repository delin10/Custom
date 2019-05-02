package org.delin.algorithm.dp;

import org.delin.test.array.ArrayPrinter;
import org.delin.util.ArrayUtils;

import java.util.Arrays;

public class LCS {
    public static int[][] LCS0(char[] str1, char[] str2) {
        int m = str1.length, n = str2.length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (str1[i - 1] == str2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp;
    }

    public static String LCSStr0(char[] str1, char[] str2) {
        int m = str1.length, n = str2.length;
        StringBuilder str = new StringBuilder();
        StringBuilder[][] dp = new StringBuilder[m + 1][n + 1];
        for (int i = 0; i < n + 1; ++i) {
            dp[0][i] = new StringBuilder();
        }
        for (int i = 0; i < m + 1; ++i) {
            dp[i][0] = new StringBuilder();
        }

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (str1[i - 1] == str2[j - 1]) {
                    dp[i][j] = new StringBuilder(dp[i - 1][j - 1]).append(str1[j - 1]);
                } else {
                    dp[i][j] = dp[i - 1][j].length() > dp[i][j - 1].length() ? dp[i - 1][j] : dp[i][j - 1];
                }
            }
        }
        return dp[m][n].toString();
    }

    public static int[][] LCS1(String str1, String str2) {
        int m = str1.length(), n = str2.length();
        int[][] dp = new int[m + 1][n + 1];
        helpLCS1(str1.toCharArray(), str2.toCharArray(), m, n, dp);
        return dp;
    }

    /**
     * O(m+n) 递归调用版本
     *
     * @param str1
     * @param str2
     * @param i
     * @param j
     * @param dp
     */
    public static void helpLCS1(char[] str1, char[] str2, int i, int j, int[][] dp) {
        if (i == 0 || j == 0) {
            return;
        }
        if (str1[i - 1] == str2[j - 1]) {
            helpLCS1(str1, str2, i - 1, j - 1, dp);
            dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
            helpLCS1(str1, str2, i - 1, j, dp);
            helpLCS1(str1, str2, i, j - 1, dp);
            dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
    }

    /**
     * 获得最长单调子序列
     * @param str
     * @return
     */
    public static int getLongextMonotoneIncrementalSequence(String str) {
        char[] unsorted = str.toCharArray();
        char[] sorted = new char[unsorted.length];
        System.arraycopy(unsorted, 0, sorted, 0, sorted.length);
        Arrays.sort(sorted);
        int[][] dp = LCS0(sorted, unsorted);
        return dp[str.length()][str.length()];
    }

    public static void main(String[] args) {
        char[] str1 = "ADGHBPSE".toCharArray(), str2 = "ASSFGSE".toCharArray();
        int[][] dp0 = LCS0(str1, str2);
        ArrayPrinter.printIntArray(dp0);
        System.out.println(LCSStr0(str1, str2));
        int[][] dp1 = LCS1(new String(str1), new String(str2));
        ArrayPrinter.printIntArray(dp1);
        //acgh
        System.out.println(getLongextMonotoneIncrementalSequence("afscbgshf"));
    }
}
