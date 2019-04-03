package org.delin.commom;

import org.delin.concurrent.util.SealedUtil;
import org.junit.Test;

public class DeadLockTest {
    Object a=new Object();
    Object b=new Object();
    class ThreadA implements Runnable{
        @Override
        public void run() {
            synchronized (a){
                System.out.println("Thread A 获得a锁");
                SealedUtil.sleep(3);
                synchronized (b){
                    System.out.println("Thread A 获得b锁");
                    System.out.println("A thread!");
                }
            }
        }
    }    class ThreadB implements Runnable{
        @Override
        public void run() {
            synchronized (b){
                System.out.println("Thread B 获得b锁");
                SealedUtil.sleep(3);
                synchronized (a){
                    System.out.println("Thread B 获得a锁");
                    System.out.println("B thread!");
                }
            }
        }
    }

    @Test
    public void test() throws Exception{
        Thread ta=new Thread(new ThreadA());
        ta.start();
        Thread tb=new Thread(new ThreadB());
        tb.start();
        ta.join();
        tb.join();
    }
}
