package threads.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public interface Lock{
    void lock(); //加锁
    //优先考虑响应中断，而不是响应锁定的普通获取或重入获取 
    void lockInterruptibly() throws  InterruptedException; 
    boolean tryLock(); //可定时和可轮询的锁获取模式
    boolean tryLock(long timeout,TimeUnit unit) throws InterruptedException;
    void unlock(); //解锁
    Condition newCondition();
}