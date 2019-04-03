package org.delin.commom;

import org.delin.mem.UnsafeAccess;
import org.delin.reflect.IReflector;
import org.delin.reflect.impl.JReflectorImpl;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class FinalFieldTest {
    private final int a=0;

    public static void main(String[]args){
        IReflector reflector=new JReflectorImpl();
        FinalFieldTest test=new FinalFieldTest();
        Field field=reflector.getField(FinalFieldTest.class,"a");
        field.setAccessible(true);
        System.out.println("old:"+test.a);
        try {
            field.setInt(test,1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("new:"+test.a);
        Unsafe unsafe=UnsafeAccess.getUnsafe();
        boolean suc=unsafe.compareAndSwapInt(test,unsafe.objectFieldOffset(field),0,1);
        System.out.println("suc="+suc+";new:"+test.a);
    }
}

