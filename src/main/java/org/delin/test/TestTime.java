package org.delin.test;

public class TestTime {
    public static void testNanos(Test t)throws Exception{
        long start=System.nanoTime();
        t.test();
        long end=System.nanoTime();
        System.out.println(">>>>>>>>>>>>>>>>>>:"+(end-start)+"ns");
    }
}
