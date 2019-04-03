package org.delin.commom;

import org.junit.Test;

public class TestConcurrentAndSerial {
    static final long count = 80000;

    @Test
    public void testConcurrent() throws Exception {
        Thread t = new Thread(() -> {
            count();
        });

        count();
        t.join();
    }

    @Test
    public void testSerial() {
        count();
        count();
    }

    public long count() {
        long b = 0;
        for (long i = 0; i < count; ++i) {
            b += 5;
        }
        return b;
    }
}