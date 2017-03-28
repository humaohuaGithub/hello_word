package threads.lock1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 
 * @author 林计钦
 * @version 1.0 2013-7-25 上午10:33:37
 * Java多线程-新特征-锁(上)

在Java5中，专门提供了锁对象，利用锁可以方便的实现资源的封锁，用来控制对竞争资源并发访问的控制，这些内容主要集中在java.util.concurrent.locks 包下面，里面有三个重要的接口Condition、Lock、ReadWriteLock。

Condition:
Condition 将 Object 监视器方法（wait、notify 和 notifyAll）分解成截然不同的对象，以便通过将这些对象与任意 Lock 实现组合使用，为每个对象提供多个等待 set （wait-set）。

Lock:
Lock 实现提供了比使用 synchronized 方法和语句可获得的更广泛的锁定操作。

ReadWriteLock:
ReadWriteLock 维护了一对相关的锁定，一个用于只读操作，另一个用于写入操作。


有关锁的介绍,API文档解说很多，看得很烦，还是看个例子再看文档比较容易理解。
 */
public class LockTest {
    public static void main(String[] args) {
        LockTest test = new LockTest();

        // 创建并发访问的账户
        MyCount myCount = test.new MyCount("95599200901215522", 10000);
        // 创建一个锁对象
        Lock lock = new ReentrantLock();
        // 创建一个线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        // 创建一些并发访问用户，一个信用卡，存的存，取的取，好热闹啊
        User u1 = test.new User("张三", myCount, -4000, lock);
        User u2 = test.new User("张三他爹", myCount, 6000, lock);
        User u3 = test.new User("张三他弟", myCount, -8000, lock);
        User u4 = test.new User("张三", myCount, 800, lock);
        // 在线程池中执行各个用户的操作
        pool.execute(u1);
        pool.execute(u2);
        pool.execute(u3);
        pool.execute(u4);
        // 关闭线程池
        pool.shutdown();
    }

    /**
     * 信用卡的用户
     */
    class User implements Runnable {
        private String name; // 用户名
        private MyCount myCount; // 所要操作的账户
        private int iocash; // 操作的金额，当然有正负之分了
        private Lock myLock; // 执行操作所需的锁对象

        User(String name, MyCount myCount, int iocash, Lock myLock) {
            this.name = name;
            this.myCount = myCount;
            this.iocash = iocash;
            this.myLock = myLock;
        }

        public void run() {
            String string;
            if(iocash>0){
                string="存款";
            }else{
                string="取款";
            }
            
            // 获取锁
            myLock.lock();
            // 执行现金业务
            System.out.println(name + "正在操作" + myCount + "账户，" + 
                    string+"金额为" + iocash + "，当前金额为" + myCount.getCash());
            myCount.setCash(myCount.getCash() + iocash);
            System.out.println(name + "操作" + myCount + "账户成功，"+ 
                    string + "金额为" + iocash + "，当前金额为" + myCount.getCash());
            System.out.println("============");
            // 释放锁，否则别的线程没有机会执行了
            myLock.unlock();
        }
    }

    /**
     * 信用卡账户，可随意透支
     */
    class MyCount {
        private String oid; // 账号
        private int cash; // 账户余额

        MyCount(String oid, int cash) {
            this.oid = oid;
            this.cash = cash;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public int getCash() {
            return cash;
        }

        public void setCash(int cash) {
            this.cash = cash;
        }

        @Override
        public String toString() {
            return "MyCount{" + "账号='" + oid + '\'' + ", 余额=" + cash + '}';
        }
    }
}