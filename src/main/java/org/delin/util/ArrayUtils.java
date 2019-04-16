package org.delin.util;

import java.lang.reflect.Array;

public class ArrayUtils {
    /**
     * 复制二维数组
     * @param src
     * @return 新的二维数组
     */
    public static boolean[][] copy(boolean[][] src) {
        int srcLen = src.length;
        boolean[][] newOne = new boolean[srcLen][];
        for (int i = 0; i < srcLen; ++i) {
            int curLen = src[i].length;
            newOne[i] = new boolean[curLen];
            System.arraycopy(src[i], 0, newOne[i], 0, curLen);
        }
        return newOne;
    }

    /**
     * 复制二维数组
     * @param src
     * @return 新的二维数组
     */
    public static int[][] copy(int[][] src) {
        int srcLen = src.length;
        int[][] newOne = new int[srcLen][];
        for (int i = 0; i < srcLen; ++i) {
            int curLen = src[i].length;
            newOne[i] = new int[curLen];
            System.arraycopy(src[i], 0, newOne[i], 0, curLen);
        }
        return newOne;
    }
}
