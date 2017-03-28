package threads.threadAtomic;

import java.util.concurrent.atomic.AtomicLong;
/**
 * 
 * @author humaohua
 *Java多线程-新特征-原子量

所谓的原子量即操作变量的操作是“原子的”，该操作不可再分，因此是线程安全的。

为何要使用原子变量呢，原因是多个线程对单个变量操作也会引起一些问题。在Java5之前，可以通过volatile、synchronized关键字来解决并发访问的安全问题，但这样太麻烦。
Java5之后，专门提供了用来进行单变量多线程并发安全访问的工具包java.util.concurrent.atomic，其中的类也很简单。
 */
public class AtomicRunnable implements Runnable {
    private static AtomicLong aLong = new AtomicLong(10000); // 原子量，每个线程都可以自由操作
    private String name; // 操作人
    private int x; // 操作数额

    AtomicRunnable(String name, int x) {
        this.name = name;
        this.x = x;
    }

    public void run() {
        System.out.println(name + "执行了" + x + "，当前余额：" + aLong.addAndGet(x));
    }

}