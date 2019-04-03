package org.delin.mem;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
// Thread Unsafe
public class UnsafeAccess {
    private static Unsafe unsafe;

    public static Unsafe getUnsafe() {
        if (unsafe != null) {
            return unsafe;
        }
        Field theUnsafeInstance = null;
        try {
            theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeInstance.setAccessible(true);
            unsafe = (Unsafe) theUnsafeInstance.get(Unsafe.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            unsafe = null;
            e.printStackTrace();
        }
        return unsafe;
    }
}
