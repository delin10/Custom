package org.delin.concurrent;

public interface ILock {
    public boolean tryLock();
    public boolean unlock();
}
