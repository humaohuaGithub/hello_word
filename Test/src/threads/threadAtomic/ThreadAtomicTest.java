package threads.threadAtomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程-原子量
 * 
 * @author 林计钦
 * @version 1.0 2013-7-26 下午04:13:45
 * Java多线程-新特征-原子量

所谓的原子量即操作变量的操作是“原子的”，该操作不可再分，因此是线程安全的。

为何要使用原子变量呢，原因是多个线程对单个变量操作也会引起一些问题。在Java5之前，可以通过volatile、synchronized关键字来解决并发访问的安全问题，但这样太麻烦。
Java5之后，专门提供了用来进行单变量多线程并发安全访问的工具包java.util.concurrent.atomic，其中的类也很简单。
 */
public class ThreadAtomicTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Runnable t1 = new AtomicRunnable("张三", 2000);
        Runnable t2 = new AtomicRunnable("李四", 3600);
        Runnable t3 = new AtomicRunnable("王五", 2700);
        Runnable t4 = new AtomicRunnable("老张", 600);
        Runnable t5 = new AtomicRunnable("老牛", 1300);
        Runnable t6 = new AtomicRunnable("胖子", 800);
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