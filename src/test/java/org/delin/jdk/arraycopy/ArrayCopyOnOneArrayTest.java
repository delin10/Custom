package org.delin.jdk.arraycopy;

import org.delin.test.array.ArrayPrinter;
import org.delin.util.ArrayUtils;

/**
 * 演示在同一个数组进行数组的复制是否会出错
 */
public class ArrayCopyOnOneArrayTest {
    public static void main(String[] args) {
        int[] arr = new int[11];
        for (int i = 0; i < 10; ++i) {
            arr[i] = i;
        }

        System.arraycopy(arr, 0, arr, 1, 10);

        ArrayPrinter.printIntArray(arr);
    }
}
