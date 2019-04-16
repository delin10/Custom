package org.delin.algorithm.recursion;

/**
 * 汉诺塔问题
 * Q1：n个汉诺塔问题
 */
public class Hanoi {
    public static void hanoi2(char a, char b, char c, int n) {
        if (n == 1) {
            System.out.println(a + "->" + c);
            return;
        }
        // 上头小的n-1个移动到b上
        hanoi2(a, c, b, n - 1);
        System.out.println(a + "->" + c);
        // 以B作为起始柱
        hanoi2(b, a, c, n - 1);
    }

    public static void main(String[] args) {
        int n = 3;
        hanoi2('A', 'B', 'C', n);
    }
}