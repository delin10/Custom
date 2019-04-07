package org.delin.mem;

import org.junit.Test;
import sun.misc.Unsafe;

public class UnsafeAccessTest {
    private Unsafe unsafe=UnsafeAccess.getUnsafe();
    //@Test
    public void testArray(){
        int[] arr=new int[100];
        int baseOffset=unsafe.arrayBaseOffset(Object[].class);
        int indexScale=unsafe.arrayIndexScale(Object[].class);
        System.out.printf("baseOffset=%d,indexScale=%d",baseOffset,indexScale);
    }

    @Test
    public void testSize() {
        UnsafeAccess.getUnsafe();
        int a=1;
        A ac=new A();
        System.out.println("A a:"+UnsafeAccess.objectSizeOfRecursively(ac));
        //System.out.println("int a:"+UnsafeAccess.objectSizeOfRecursively(a));
    }

    class A{
        int a;
        Integer x;
        B[] b=new B[10];
    }

    class B{
        String x="aaa";
    }
}
