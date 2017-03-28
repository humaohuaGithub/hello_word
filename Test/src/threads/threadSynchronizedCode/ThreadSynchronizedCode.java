package threads.threadSynchronizedCode;

/**
 * 线程同步方法
 * 
 * @author 林计钦
 * @version 1.0 2013-7-24 上午10:12:47
 * Java多线程-线程的同步(同步代码块)

对于同步，除了同步方法外，还可以使用同步代码块，有时候同步代码块会带来比同步方法更好的效果。

追其同步的根本的目的，是控制竞争资源的正确的访问，因此只要在访问竞争资源的时候保证同一时刻只能一个线程访问即可，因此Java引入了同步代码快的策略，以提高性能。

在上个例子的基础上，对oper方法做了改动，由同步方法改为同步代码块模式，程序的执行逻辑并没有问题。
 */
public class ThreadSynchronizedCode {
    public static void main(String[] args) {
        ThreadSynchronizedCode t = new ThreadSynchronizedCode();
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
        /**
         * 
 线程线程B运行结束，增加“-60”，当前用户账户余额为：40
线程线程A运行结束，增加“20”，当前用户账户余额为：60
线程线程C运行结束，增加“-80”，当前用户账户余额为：-20
线程线程D运行结束，增加“-30”，当前用户账户余额为：-50
线程线程F运行结束，增加“21”，当前用户账户余额为：-29
线程线程E运行结束，增加“32”，当前用户账户余额为：3
         */
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
        public void oper(int x) {
            try {
                Thread.sleep(10L);
                synchronized (this) {
                    this.cash += x;
                    System.out.println("线程" + Thread.currentThread().getName() + "运行结束，增加“" + x
                            + "”，当前用户账户余额为：" + cash);
                }
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
