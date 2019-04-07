package org.delin.concurrent;

import org.delin.concurrent.util.SealedUtil;

public class ResumeAndSuspendTest {
    private static class SuspendThread implements Runnable{
        @Override
        public void run() {
            SealedUtil.sleep(2);
            Thread.currentThread().suspend();
        }
    }

    public static void main(String[]args){
        Thread t=new Thread(new SuspendThread());
        t.start();
        t.resume();
    }
}
