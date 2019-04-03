package org.delin.commom;

import org.delin.concurrent.util.SealedUtil;
import org.junit.Test;
import sun.misc.Unsafe;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest {
    volatile int counterPlusPlus0 = 0;
    AtomicInteger counterPlusPlus1 = new AtomicInteger(0);
    int counterPlusPlus2 = 0;
    int counterPlusPlus3=0;

    class Counter implements Runnable {
        @Override
        public void run() {
            counterPlusPlus0++;
        }
    }

    @Test
    public void testPlusPlus() {
        while(true) {
            int count = 1000;
            CyclicBarrier barrier = new CyclicBarrier(count + 1);
            for (int i = 0; i < count; ++i) {
                new Thread(() -> {
                    counterPlusPlus0++;
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            barrier.reset();
            System.out.println("0:" + counterPlusPlus0);
            for (int i = 0; i < count; ++i) {
                new Thread(() -> {
                    counterPlusPlus1.getAndIncrement();
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            barrier.reset();
            System.out.println("1:" + counterPlusPlus1.get());
            for (int i = 0; i < count; ++i) {
                new Thread(() -> {
                    counterPlusPlus2++;
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            barrier.reset();
            System.out.println("2:" + counterPlusPlus2);

            for (int i = 0; i < count; ++i) {
                new Thread(() -> {
                    synchronized (this) {
                        counterPlusPlus3++;
                    }
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("3:" + counterPlusPlus3);
            System.out.println("=================");
            SealedUtil.sleep(1);
        }
    }
}
