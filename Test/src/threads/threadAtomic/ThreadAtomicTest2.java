package threads.threadAtomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程-原子量
 * 
 * @author 林计钦
 * @version 1.0 2013-7-26 下午04:13:45
 * 这里使用了一个对象锁，来控制对并发代码的访问。不管运行多少次，执行次序如何，最终余额均为21000，这个结果是正确的。

有关原子量的用法很简单，关键是对原子量的认识，原子仅仅是保证变量操作的原子性，但整个程序还需要考虑线程安全的。
 */
public class ThreadAtomicTest2 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Lock lock = new ReentrantLock(false);
        Runnable t1 = new AtomicRunnable2("张三", 2000, lock);
        Runnable t2 = new AtomicRunnable2("李四", 3600, lock);
        Runnable t3 = new AtomicRunnable2("王五", 2700, lock);
        Runnable t4 = new AtomicRunnable2("老张", 600, lock);
        Runnable t5 = new AtomicRunnable2("老牛", 1300, lock);
        Runnable t6 = new AtomicRunnable2("胖子", 800, lock);
        // 执行各个线程
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);
        pool.execute(t6);
        // 关闭线程池
        pool.shutdown();
    }
}
