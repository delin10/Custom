package org.delin.concurrent;

import org.delin.concurrent.util.SealedUtil;

public class CASLockTest {
    static CASLock lock=new CASLock();

    static class TestRunner extends Thread{
        @Override
        public void run() {
            super.run();
            while(true){
                if (lock.tryLock()){
                    System.out.println(this+" get lock!!");
                    SealedUtil.sleep(3);
                    if(lock.unlock()){
                        System.out.println(this+"解锁成功!");
                        SealedUtil.sleep(1);
                    }
                }else{
                    //System.out.println(this+" failed!!");
                    // SealedUtil.sleep(1);
                }

            }
        }
    }

    public static void main(String[]args){
        int count=500;
        for (int i=0;i<count;++i){
            new TestRunner().start();
        }
    }
}
