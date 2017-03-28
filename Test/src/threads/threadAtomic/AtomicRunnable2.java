package threads.threadAtomic;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
/**
 * 这里要注意的一点是，原子量虽然可以保证单个变量在某一个操作过程的安全，但无法保证你整个代码块，或者整个程序的安全性。因此，通常还应该使用锁等同步机制来控制整个程序的安全性。

下面是对这个错误修正：
 * @author humaohua
 *
 */
public class AtomicRunnable2 implements Runnable {
    private static AtomicLong aLong = new AtomicLong(10000); // 原子量，每个线程都可以自由操作
    private String name; // 操作人
    private int x; // 操作数额
    private Lock lock;

    AtomicRunnable2(String name, int x, Lock lock) {
        this.name = name;
        this.x = x;
        this.lock=lock;
    }

    public void run() {
        lock.lock();
        System.out.println(name + "执行了" + x + "，当前余额：" + aLong.addAndGet(x));
        lock.unlock();
    }

}