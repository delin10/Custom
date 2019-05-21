package org.delin.jdk.operation;

public class ShiftOperationTest {
    public static void main(String[] args) {
        //testLong();

    }

    public static void testInt() {
        int x = 0xffffffff;
        int[] bits = {0, 1, 2, 31, 32, 33, -1, -2, -31, -32, -33};
        System.out.println("int test");
        for (int bit : bits) {
            System.out.print("left shift << " + bit + " ==> ");
            String bs = Long.toBinaryString(x << bit);
            System.out.println("[位数:" + bs.length() + " , 二进制序列:" + bs + ", 十进制:" + (x << bit) + "]");
        }
        System.out.println();
        for (int bit : bits) {
            System.out.print("right shift >> " + bit + " ==> ");
            String bs = Long.toBinaryString(x >> bit);
            System.out.println("[位数:" + bs.length() + " , 二进制序列:" + bs + ", 十进制:" + (x >> bit) + "]");
        }
        System.out.println();
        for (int bit : bits) {
            System.out.print("non-flag right shift >>> " + bit + " ==> ");
            String bs = Long.toBinaryString(x >>> bit);
            System.out.println("[位数:" + bs.length() + " , 二进制序列:" + bs + ", 十进制:" + (x >>> bit) + "]");
        }
    }

    public static void testLong() {
        long x = 0xffffffffffffffffL;
        int[] bits = {0, 1, 2, 63, 64, 65, -1, -2, -63, -64, -65};
        System.out.println("Long test");
        for (int bit : bits) {
            System.out.print("left shift << " + bit + " ==> ");
            String bs = Long.toBinaryString(x << bit);
            System.out.println("[位数:" + bs.length() + " , 二进制序列:" + bs + ", 十进制:" + (x << bit) + "]");
        }
        System.out.println();
        for (int bit : bits) {
            System.out.print("right shift >> " + bit + " ==> ");
            String bs = Long.toBinaryString(x >> bit);
            System.out.println("[位数:" + bs.length() + " , 二进制序列:" + bs + ", 十进制:" + (x >> bit) + "]");
        }
        System.out.println();
        for (int bit : bits) {
            System.out.print("non-flag right shift >>> " + bit + " ==> ");
            String bs = Long.toBinaryString(x >>> bit);
            System.out.println("[位数:" + bs.length() + " , 二进制序列:" + bs + ", 十进制:" + (x >>> bit) + "]");
        }
    }
}
