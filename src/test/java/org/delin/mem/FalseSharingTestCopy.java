package org.delin.mem;
import sun.misc.Contended;

public final class FalseSharingTestCopy implements Runnable {
    public final static int NUM_THREADS = 4; // change
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private final int arrayIndex;
    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLong();
        }
    }


    public FalseSharingTestCopy(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }


    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = i;
        }
    }
    public static void main(final String[] args) throws Exception {
        final long start = System.nanoTime();
        runTest();
        System.out.println("duration = " + (System.nanoTime() - start));
    }

    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharingTestCopy(i));
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }


    //@Contended //4 duration = 30838066217 3 duration = 29063509937
    public final static class VolatileLong {
        //@Contended("vl")        //4 duration = 7141336882  3 duration = 6447165500
        public volatile long value = 0L;        // 4 duration = 33330646307 duration = 29605652342 3 duration = 30371311159 2 duration = 14996924784
        //public long p1, p2, p3, p4, p5, p6;       // 4 duration = 7254435143 //duration = 19428746938 3 duration = 16788533710 2 duration = 12784424266

    }

}
