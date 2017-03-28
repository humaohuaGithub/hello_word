package threads.threadSynchronizedMethod;

/**
 * 线程同步方法
 * 
 * @author 林计钦
 * @version 1.0 2013-7-24 上午10:12:47
 * Java多线程-线程的同步(同步方法)

线程的同步是保证多线程安全访问竞争资源的一种手段。
线程的同步是Java多线程编程的难点，往往开发者搞不清楚什么是竞争资源、什么时候需要考虑同步，怎么同步等等问题，当然，这些问题没有很明确的答案，但有些原则问题需要考虑，是否有竞争资源被同时改动的问题？

在本文之前，请参阅《Java多线程-线程的同步与锁》，本文是在此基础上所写的。

对于同步，在具体的Java代码中需要完成一下两个操作：
把竞争访问的资源标识为private；
同步哪些修改变量的代码，使用synchronized关键字同步方法或代码。
当然这不是唯一控制并发安全的途径。

synchronized关键字使用说明
synchronized只能标记非抽象的方法，不能标识成员变量。

为了演示同步方法的使用，构建了一个信用卡账户，起初信用额为100w，然后模拟透支、存款等多个操作。显然银行账户User对象是个竞争资源，而多个并发操作的是账户方法oper(int x)，当然应该在此方法上加上同步，并将账户的余额设为私有变量，禁止直接访问。
 */
public class ThreadSynchronizedMethod {
    public static void main(String[] args) {
        ThreadSynchronizedMethod t = new ThreadSynchronizedMethod();
        User u = t.new User("张三", 100);
        MyThread t1 = t.new MyThread("线程A", u, 20);
        MyThread t2 = t.new MyThread("线程B", u, -60);
        MyThread t3 = t.new MyThread("线程C", u, -80);
        MyThread t4 = t.new MyThread("线程D", u, -30);
        MyThread t5 = t.new MyThread("线程E", u, 32);
        MyThread t6 = t.new MyThread("线程F", u, 21);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }

    class MyThread extends Thread {
        private User u;
        /**存款金额*/
        private int y = 0;
        
        MyThread(String name, User u, int y) {
            super(name);
            this.u = u;
            this.y = y;
        }

        public void run() {
            u.oper(y);
        }
    }

    class User {
        /** 账号 */
        private String code;
        /** 余额 */
        private int cash;

        User(String code, int cash) {
            this.code = code;
            this.cash = cash;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 存款
         * 
         * @param x 欲存款金额
         *            
         */
        public synchronized void oper(int x) {
            try {
                Thread.sleep(10L);
                this.cash += x;
                System.out.println("线程"+Thread.currentThread().getName() + "运行结束，增加“" + x
                        + "”，当前用户账户余额为：" + cash);
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "User{" + "code='" + code + '\'' + ", cash=" + cash + '}';
        }
    }
}
