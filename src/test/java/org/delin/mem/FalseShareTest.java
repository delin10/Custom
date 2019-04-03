package org.delin.mem;

import org.delin.test.TestTime;
import org.junit.Test;
import sun.misc.Contended;

public class FalseShareTest {
    private static final long COUNT = 500L * 1000L * 1000L;;
    private static final int THREAD_NUM = 2;
    private static NormalVolatileShare[] normals = new NormalVolatileShare[THREAD_NUM];
    private static ContentedVolatileShare[] contendeds = new ContentedVolatileShare[THREAD_NUM];
    private static PaddingVolatileShare[] paddings = new PaddingVolatileShare[THREAD_NUM];

    static {
        for (int i = 0; i < THREAD_NUM; ++i) {
            normals[i] = new NormalVolatileShare();
            contendeds[i] = new ContentedVolatileShare();
            paddings[i] = new PaddingVolatileShare();
        }
    }

    static class NormalVolatileShare {
        volatile long v;
    }

    @Contended
    static class ContentedVolatileShare {
        volatile long v;
    }

    static class PaddingVolatileShare {
        volatile long v;
        volatile long p1, p2, p3, p4, p5, p6; // comment out
    }

    static interface Call{
        public void call(int k);
    }
    public static void runFrame(Call run) throws Exception{
        Thread[] threads = new Thread[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; ++i) {
            final int k = i;
            threads[i] = new Thread(() -> {
              run.call(k);
            });
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    public static void testNormal() throws Exception {
       runFrame(k->{
           for (long j = 0; j < COUNT; ++j) {
               normals[k].v = j;
           }});
    }

    public static void testContented() throws Exception {
        runFrame(k->{
            for (long j = 0; j < COUNT; ++j) {
                contendeds[k].v = j;
            }});
    }

    public static void testPadding() throws Exception {
        runFrame(k->{
            for (long j = 0; j < COUNT; ++j) {
                paddings[k].v = j;
            }});
    }

    public static void main(String[] args) throws Exception{
        TestTime.testNanos(() -> {
            //testNormal();//       >>>>>>>>>>>>>>>>>>:7866919413ns >>>>>>>>>>>>>>>>>>:7490023010ns    2>>>>>>>>>>>>>>>>>>:6512206177ns
            //testContented();//  >>>>>>>>>>>>>>>>>>:7220062457ns   >>>>>>>>>>>>>>>>>>:7316504056ns    2>>>>>>>>>>>>>>>>>>:6571317938ns
            testPadding();//    >>>>>>>>>>>>>>>>>>:7418338314ns     >>>>>>>>>>>>>>>>>>:7286343389ns    2>>>>>>>>>>>>>>>>>>:6479520871ns
        });
        System.out.println("main over");
    }
}
