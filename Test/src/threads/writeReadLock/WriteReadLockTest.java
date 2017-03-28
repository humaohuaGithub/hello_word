package threads.writeReadLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 
 * @author 林计钦
 * @version 1.0 2013-7-25 上午10:33:37
 * Java多线程-新特征-锁(下)

在上文中提到了Lock接口以及对象，使用它，很优雅的控制了竞争资源的安全访问，但是这种锁不区分读写，称这种锁为普通锁。为了提高性能，Java提供了读写锁，在读的地方使用读锁，在写的地方使用写锁，灵活控制，在一定程度上提高了程序的执行效率。

Java中读写锁有个接口java.util.concurrent.locks.ReadWriteLock，也有具体的实现ReentrantReadWriteLock，详细的API可以查看JavaAPI文档。

下面这个例子是在文例子的基础上，将普通锁改为读写锁，并添加账户余额查询的功能，代码如下：
 */
public class WriteReadLockTest {
    public static void main(String[] args) {
        WriteReadLockTest test = new WriteReadLockTest();
        // 创建并发访问的账户
        MyCount myCount = test.new MyCount("95599200901215522", 10000);
        // 创建一个锁对象
        ReadWriteLock lock = new ReentrantReadWriteLock(false);
        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);
        // 创建一些并发访问用户，一个信用卡，存的存，取的取，好热闹啊
        User u1 = test.new User("张三", myCount, -4000, lock, false);
        User u2 = test.new User("张三他爹", myCount, 6000, lock, false);
        User u3 = test.new User("张三他弟", myCount, -8000, lock, false);
        User u4 = test.new User("张三", myCount, 800, lock, false);
        User u5 = test.new User("张三他爹", myCount, 0, lock, true);
        // 在线程池中执行各个用户的操作
        pool.execute(u1);
        pool.execute(u2);
        pool.execute(u3);
        pool.execute(u4);
        pool.execute(u5);
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
        private ReadWriteLock myLock; // 执行操作所需的锁对象
        private boolean ischeck; // 是否查询

        User(String name, MyCount myCount, int iocash, ReadWriteLock myLock, boolean ischeck) {
            this.name = name;
            this.myCount = myCount;
            this.iocash = iocash;
            this.myLock = myLock;
            this.ischeck = ischeck;
        }

        public void run() {
            if (ischeck) {
                // 获取读锁
                myLock.readLock().lock();
                System.out.println("读：" + name + "正在查询" + myCount + "账户，当前金额为" + myCount.getCash());
                // 释放读锁
                myLock.readLock().unlock();
            } else {
                // 获取写锁
                myLock.writeLock().lock();
                // 执行现金业务
                System.out.println("写：" + name + "正在操作" + myCount + "账户，金额为" + iocash + "，当前金额为"
                        + myCount.getCash());
                myCount.setCash(myCount.getCash() + iocash);
                System.out.println("写：" + name + "操作" + myCount + "账户成功，金额为" + iocash + "，当前金额为"
                        + myCount.getCash());
                // 释放写锁
                myLock.writeLock().unlock();
            }
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
            return "MyCount{" + "oid='" + oid + '\'' + ", cash=" + cash + '}';
        }
    }
    /**
     * 写：张三正在操作MyCount{oid='95599200901215522', cash=10000}账户，金额为-4000，当前金额为10000
写：张三操作MyCount{oid='95599200901215522', cash=6000}账户成功，金额为-4000，当前金额为6000
写：张三他爹正在操作MyCount{oid='95599200901215522', cash=6000}账户，金额为6000，当前金额为6000
写：张三他爹操作MyCount{oid='95599200901215522', cash=12000}账户成功，金额为6000，当前金额为12000
写：张三正在操作MyCount{oid='95599200901215522', cash=12000}账户，金额为800，当前金额为12000
写：张三操作MyCount{oid='95599200901215522', cash=12800}账户成功，金额为800，当前金额为12800
写：张三他弟正在操作MyCount{oid='95599200901215522', cash=12800}账户，金额为-8000，当前金额为12800
写：张三他弟操作MyCount{oid='95599200901215522', cash=4800}账户成功，金额为-8000，当前金额为4800
读：张三他爹正在查询MyCount{oid='95599200901215522', cash=4800}账户，当前金额为4800
     */
}
