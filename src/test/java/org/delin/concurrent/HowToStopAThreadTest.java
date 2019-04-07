package org.delin.concurrent;

import org.delin.concurrent.util.SealedUtil;
import org.omg.IOP.TAG_JAVA_CODEBASE;

import javax.annotation.processing.SupportedSourceVersion;

public class HowToStopAThreadTest {
    private static TestObject to = new TestObject();

    //stop
    static class StopThread implements Runnable {
        @Override
        public void run() {
            synchronized (to) {
                to.setA("new");
                SealedUtil.sleep(3);
                to.setB("new");
            }
        }
    }

    public static void testStop() {
        System.out.println(to);
        Thread t = new Thread(new StopThread());
        t.start();
        SealedUtil.sleep(1);
        t.stop();
        synchronized (to) {
            System.out.println(to);
        }
    }

    static class VarThread implements Runnable {
        private volatile boolean running = true;//保证其它线程的写入改线程可见

        @Override
        public void run() {
            while (running) {
                SealedUtil.sleep(1);
                System.out.println("running=" + running);
            }
        }

        public void stop() {
            running = false;
        }
    }

    public static void testVar() {
        VarThread vt = new VarThread();
        Thread t = new Thread(vt);
        t.start();
        SealedUtil.sleep(3);
        vt.stop();
        long i = 0;
        while (i++ < Long.MAX_VALUE - 1) ;//占用CPU
    }

    static class InterruptThread implements Runnable {
        private Thread cur;
        @Override
        public void run() {
            cur=Thread.currentThread();
            System.out.println(cur.isInterrupted());
            while (!cur.isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    cur.interrupt();
                }
            }
            System.out.println("exit");
        }
    }

    public static void testInterrupt() {
        HowToStopAThreadTest.InterruptThread it = new InterruptThread();
        Thread t = new Thread(it);
        t.start();
        SealedUtil.sleep(3);
        t.interrupt();
    }

    public static void main(String[] args) {
        testInterrupt();
    }
}
