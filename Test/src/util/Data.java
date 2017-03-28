package util;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Data {
/*
    public int num = 0;
    public synchronized int getEven() {
        ++num;
        Thread.yield();//让另外线程先执行,加大测试效果几率
        ++num;
        return num;
    }
*/
    public int num = 0;
    Lock lock = new ReentrantLock();
    public int getEven() {
        lock.lock();
        try {
            ++num;
            Thread.yield();// 让另外线程先执行,加大测试几率
            ++num;
            return num;// 一定要在unlock之前return，否则数据不同步
        } finally {
            lock.unlock();
        }
        // return num;//不能在unlock之后return，否则数据不同步
    }
}
