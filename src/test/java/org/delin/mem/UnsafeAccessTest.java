package org.delin.mem;

import org.junit.Test;
import sun.misc.Unsafe;

public class UnsafeAccessTest {
    private Unsafe unsafe=UnsafeAccess.getUnsafe();
    @Test
    public void testArray(){
        int[] arr=new int[100];
        int baseOffset=unsafe.arrayBaseOffset(Object[].class);
        int indexScale=unsafe.arrayIndexScale(Object[].class);
        System.out.printf("baseOffset=%d,indexScale=%d",baseOffset,indexScale);
    }
}
