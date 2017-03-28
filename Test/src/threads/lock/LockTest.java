package threads.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
 
/**
 * 用Lock替代synchronized
 *
 * @author Administrator
 *锁是控制多个线程对共享资源进行访问的工具。通常，锁提供了对共享资源的独占访问。一次只能有一个线程获得锁，对共享资源的所有访问都需要首先获得锁。不过，某些锁可能允许对共享资源并发访问，如 ReadWriteLock(维护了一对相关的锁，一个用于只读操作，另一个用于写入操作) 的读写锁。

1、Lock提供了无条件的、可轮询的、定时的、可中断的锁获取操作，所有加锁和解锁的方法都是显式的。
2、ReentrantLock实现了lock接口，跟synchronized相比，ReentrantLock为处理不可用的锁提供了更多灵活性。
3、使用lock接口的规范形式要求在finally块中释放锁lock.unlock()。如果锁守护的代码在try块之外抛出了异常，它将永远不会被释放。

以下模拟Lock用法：假设有两个线程(A线程、B线程)去调用print(String name)方法，A线程负责打印'zhangsan'字符串，B线程负责打印'lisi'字符串。
1、当没有为print(String name)方法加上锁时，则会产生A线程还没有执行完毕，B线程已开始执行，那么打印出来的name就会出现如下问题。
当为print(String name)方法加上锁时，则会产生A执行完毕后，B线程才执行print(String name)方法，达到互斥或者说同步效果。
 */
public class LockTest {
 
    public static void main(String[] args) {
        new LockTest().init();
    }
 
    private void init() {
        final Outputer outputer = new Outputer();
        //A线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output("zhangsan");
                }
 
            }
        }).start();
 
        //B线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output("lisi");
                }
 
            }
        }).start();
 
    }
 
    static class Outputer {
        Lock lock = new ReentrantLock();
 
        /**
         * 打印字符
         *
         * @param name
         */
        public void output(String name) {
            int len = name.length();
            lock.lock();
            try {
                for (int i = 0; i < len; i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            } finally {
                lock.unlock();
            }
        }
    }
}
