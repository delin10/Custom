package org.delin.concurrent;

import org.delin.concurrent.util.SealedUtil;

public class InterruptTest {
    private static class InterruptThread implements Runnable {
        private Thread cur;

        @Override
        public void run() {
            cur = Thread.currentThread();
            System.out.println(cur.isInterrupted());
            try {
                cur.interrupt();
                System.out.println("try里测试1:" + cur.isInterrupted());
                System.out.println("try里测试2:" + cur.isInterrupted());
                cur.interrupted();
                cur.interrupt();
                System.out.println("try里测试3:" + Thread.interrupted());
                System.out.println("try里测试4:" + Thread.interrupted());
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("caach里测试1:" + cur.isInterrupted());
                System.out.println("caach里测试2:" + cur.isInterrupted());
                System.out.println(Thread.interrupted());
                cur.interrupt();
                e.printStackTrace();
            }
        }
    }

    public static void testInterrupt() {
        InterruptThread it = new InterruptThread();
        Thread t = new Thread(it);
        t.start();
        SealedUtil.sleep(3);
        t.interrupt();
    }

    public static void main(String[] args) {
        testInterrupt();
    }
}
