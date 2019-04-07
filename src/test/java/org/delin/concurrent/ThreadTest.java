package org.delin.concurrent;

import org.delin.concurrent.util.SealedUtil;
import org.delin.reflect.IReflector;
import org.delin.reflect.impl.JReflectorImpl;

import java.lang.reflect.Field;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permission;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.FileHandler;
import java.util.stream.Collectors;

public class ThreadTest {
    private static IReflector reflector = new JReflectorImpl();

    public static void main(String[] args) {
        //获得SecurityManager
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            System.out.println(sm.getThreadGroup());
        } else {
            System.out.println("SecurityManager=:" + null);
            System.out.println("main thread's ThreadGroup=:" + Thread.currentThread().getThreadGroup());
        }
        //线程的构造函数
        Thread thread = new Thread(() -> {
            System.out.println("Thread(Runnable).getThreadGroup:" + Thread.currentThread().getThreadGroup());
            int a=1/0;
        });
        //设置异常处理器
        thread.setDefaultUncaughtExceptionHandler((a,b)->{
            System.out.println(a+" --------------UncaughtExceptionHandler excute!!");
            //b.printStackTrace();
        });
        thread.start();
        System.out.println(thread.getState());
        //SealedUtil.sleep(1);
        System.out.println(Thread.currentThread());
        System.out.println(thread);
        //threadQ/eetop
        Field threadQ = reflector.getField(Thread.class, "threadQ");
        Field eetop = reflector.getField(Thread.class, "eetop");
        System.out.println("threadQ=:" + reflector.getValue(threadQ, thread));
        System.out.println("eetop=:" + reflector.getValue(eetop, thread));
        Lock lock = new ReentrantLock(true);
        synchronized (lock) {
            //lock和monitor存在相当大的区别
            System.out.println("holds Lock::" + Thread.holdsLock(lock));
        }

        SealedUtil.sleep(1);
        //Thread.dumpStack();
        //Thread.getAllStackTraces().entrySet().stream().map(e -> e.getKey() + "=[" + Arrays.stream(e.getValue()).map(StackTraceElement::toString).collect(Collectors.joining(",")) + "]").forEach(System.out::println);
        Thread.currentThread().checkAccess();

    }
}
