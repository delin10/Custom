package org.delin.util;

public class StackTraceUtils {
    public static String getCallMethod(int distance) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        return String.format("**********%s[%s]***********", elements[distance].getClassName(), elements[distance].getMethodName());
    }
}
