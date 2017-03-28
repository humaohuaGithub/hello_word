package threads.condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
 /**
  * 
  * @author humaohua
  *java5 Condition用法--实现线程间的通信

Condition的功能类似在传统线程技术中的Object.wait()和Object.natify()的功能，传统线程技术实现的互斥只能一个线程单独干，不能说这个线程干完了通知另一个线程来干，Condition就是解决这个问题的，实现线程间的通信。比如CPU让小弟做事，小弟说我先歇着并通知大哥，大哥就开始做事。

Condition 将 Object 监视器方法（wait、notify 和 notifyAll）分解成截然不同的对象，以便通过将这些对象与任意 Lock 实现组合使用，为每个对象提供多个等待 set（wait-set）。其中，Lock 替代了 synchronized 方法和语句的使用，Condition 替代了 Object 监视器方法的使用。

Condition实例实质上被绑定到一个锁上。要为特定 Lock 实例获得 Condition 实例，请使用其 newCondition() 方法。

在java5中，一个锁可以有多个条件，每个条件上可以有多个线程等待，通过调用await()方法，可以让线程在该条件下等待。当调用signalAll()方法，又可以唤醒该条件下的等待的线程。

下面以银行存取款案例阐述Condition用法：

假设有一个账户，多个用户（线程）在同时操作这个账户，有的存款有的取款，存款随便存，取款有限制，不能透支，任何试图透支的操作都将等待里面有足够存款才执行操作。
  */
public class ConditionTest {
    public static void main(String[] args) {
        ConditionTest test = new ConditionTest();
 
        // 创建并发访问的账户
        Account myAccount = test.new Account("95599200901215522", 10000);
        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Thread t1 = test.new DrawThread("张三", myAccount, 11000);
        Thread t2 = test.new SaveThread("李四", myAccount, 3600);
        Thread t3 = test.new DrawThread("王五", myAccount, 2700);
        Thread t4 = test.new SaveThread("老张", myAccount, 600);
        Thread t5 = test.new DrawThread("老牛", myAccount, 1300);
        Thread t6 = test.new SaveThread("胖子", myAccount, 2000);
 
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
 
    /**
     * 存款线程类
     */
    public class SaveThread extends Thread {
        private String name; // 操作人
        private Account account; // 账户
        private int x; // 存款金额
 
        SaveThread(String name, Account account, int x) {
            this.name = name;
            this.account = account;
            this.x = x;
        }
 
        public void run() {
            account.saving(x, name);
        }
    }
 
    /**
     * 取款线程类
     */
    public class DrawThread extends Thread {
        private String name; // 操作人
        private Account account; // 账户
        private int x; // 存款金额
 
        DrawThread(String name, Account account, int x) {
            this.name = name;
            this.account = account;
            this.x = x;
        }
 
        public void run() {
            account.drawing(x, name);
        }
    }
 
    /**
     * 银行账户
     */
    public class Account {
        private String id; // 账号
        private int cash; // 账户余额
        private Lock lock = new ReentrantLock(); // 账户锁
        private Condition _save = lock.newCondition(); // 存款条件
        private Condition _draw = lock.newCondition(); // 取款条件
 
        Account(String id, int cash) {
            this.id = id;
            this.cash = cash;
        }
 
        /**
         * 存款
         *
         * @param x 操作金额
         * @param name 操作人
         */
        public void saving(int x, String name) {
            lock.lock(); // 获取锁
            if (x > 0) {
                cash += x; // 存款
                System.out.println(name + "存款" + x + "，当前余额为" + cash);
            }
            _draw.signalAll(); // 唤醒所有等待的取款线程。
            lock.unlock(); // 释放锁
        }
 
        /**
         * 取款
         *
         * @param x
         *            操作金额
         * @param name
         *            操作人
         */
        public void drawing(int x, String name) {
            lock.lock(); // 获取锁
            try {
                if (cash - x < 0) {
                    System.out.println(name + "取款失败[余额不足]，取款" + x + "，当前余额为" + cash);
                    _draw.await(); // 阻塞取款操作
                } else {
                    cash -= x; // 取款
                    System.out.println(name + "取款" + x + "，当前余额为" + cash);
                }
                _save.signalAll(); // 唤醒所有等待的存款操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock(); // 释放锁
            }
        }
    }
     
}