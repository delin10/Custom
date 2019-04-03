package org.delin.concurrent.util;

import java.util.concurrent.TimeUnit;

public class SealedUtil {
    public static void sleep(long timeout){
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
        }
    }
}
