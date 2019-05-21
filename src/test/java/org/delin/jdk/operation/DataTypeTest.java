package org.delin.jdk.operation;

public class DataTypeTest {
    public static void main(String[] args) {
        int target = -0x8000_0000;
        System.out.println(target == -target);
        System.out.println(target == -Integer.MIN_VALUE);
        System.out.println(target - 2 == Integer.MAX_VALUE-1);
        System.out.println(Integer.toBinaryString(-0));
        System.out.println(Integer.toBinaryString(0));
    }

    public static void testCode() {
        short x = 0b111_1111_1111_1111;//0x7fff;
        short _x = 0xffff8000;
        byte y = 0b111_1111;//0x7f;
        byte _y = -0b1000_0000;//0xffffff80;
        System.out.println("byte " + Byte.MIN_VALUE + "==" + _y);
        System.out.println("byte " + Byte.MAX_VALUE + "==" + y);
        System.out.println("short " + Short.MAX_VALUE + "==" + x);
        System.out.println("short " + Short.MIN_VALUE + "==" + _x);
    }
}
