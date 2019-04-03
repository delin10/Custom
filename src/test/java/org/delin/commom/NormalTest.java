package org.delin.commom;

import org.delin.mem.UnsafeAccess;
import org.junit.Test;
import sun.misc.Unsafe;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadLocalRandom;

public class NormalTest {
    private Unsafe unsafe=UnsafeAccess.getUnsafe();
    @Test
    public void test(){
        int mask=(1<<1);
        System.out.println(Integer.toBinaryString(mask));
        try {
            long probe=unsafe.objectFieldOffset(Thread.class.getDeclaredField("threadLocalRandomProbe"));
            System.out.println(probe);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        int a=1;
        System.out.println(a>>>1);
        System.out.println(a);

    }
}
