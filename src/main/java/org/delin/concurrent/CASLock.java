package org.delin.concurrent;

import org.delin.mem.UnsafeAccess;
import sun.misc.Unsafe;

public class CASLock implements ILock {
    // 设置 cur和sync 复合操作的锁
    private volatile int compoundSync = 0;
    private volatile int sync = 0;
    private volatile Thread cur;
    private static final Unsafe unsafe;
    private static long SYNC_OFFSET;
    private static long COMPOUNDSYNC_OFFSET;

    static {
        unsafe = UnsafeAccess.getUnsafe();
        try {
            // 一定getDeclaredField
            SYNC_OFFSET = unsafe.objectFieldOffset(CASLock.class.getDeclaredField("sync"));
            COMPOUNDSYNC_OFFSET = unsafe.objectFieldOffset(CASLock.class.getDeclaredField("compoundSync"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public CASLock() {
    }

    public boolean tryLock() {
        Thread thread = Thread.currentThread();
        if(cur!=null){
            return false;
        }
        // alredy have lock
//        if (cur != null) {
//            for (; ; ) {
//                // System.out.println("cur!=null");
//                if (compoundSync == 0 && unsafe.compareAndSwapInt(this, COMPOUNDSYNC_OFFSET, 0, 1)) {
//                    try {
//                        if (thread.equals(cur) && sync == 1) {
//                            System.out.println("the lock have been got by this thread!");
//                            return true;
//                        } else if (sync == 1) {
//                            return false;
//                        }
//                    } finally {
//                        compoundSync = 0;
//                    }
//                }
//            }
//        }
        // try to lock
        // System.out.println(this + ",sync=" + 1 + ",thread=" + thread + ",cur=" + cur);

        if (sync == 0 && unsafe.compareAndSwapInt(this, SYNC_OFFSET, 0, 1)) {
            cur = thread;
            // System.out.println("cur is set to "+cur);
            return true;
        }
        return false;
    }

    public boolean unlock() {
        if (cur == null) {
            System.out.println("No lock!");
            return false;
        }
        Thread thread = Thread.currentThread();
        //进行组合加锁
        for (; ; ) {
            if (compoundSync == 0 && unsafe.compareAndSwapInt(this, COMPOUNDSYNC_OFFSET, 0, 1)) {
                try {
                    if (thread.equals(cur) && sync == 1 && unsafe.compareAndSwapInt(this, SYNC_OFFSET, 1, 0)) {
                        // System.out.println("cur="+cur+",thread="+thread);
                        cur = null;
                        return true;
                    }else if (!thread.equals(cur)){
                        System.out.println("超过两个线程获得锁，解锁失败！");
                        return false;
                    }
                } finally {
                    compoundSync = 0;
                }
            }
            // 超过一定的并发度不断循环尝试 效率极低 20
            System.out.println("解锁失败!下一次尝试");
        }
    }
}
