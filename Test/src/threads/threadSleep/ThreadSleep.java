package threads.threadSleep;

/**
 * 线程的调度-休眠
 * 
 * @author 林计钦
 * @version 1.0 2013-7-24 上午09:12:14
 * Java多线程-线程的调度(休眠)

Java线程调度是Java多线程的核心，只有良好的调度，才能充分发挥系统的性能，提高程序的执行效率。

这里要明确的一点，不管程序员怎么编写调度，只能最大限度的影响线程执行的次序，而不能做到精准控制。

线程休眠的目的是使线程让出CPU的最简单的做法之一，线程休眠时候，会将CPU资源交给其他线程，以便能轮换执行，当休眠一定时间后，线程会苏醒，进入准备状态等待执行。

线程休眠的方法是Thread.sleep(long millis)和Thread.sleep(long millis, int nanos)，均为静态方法，那调用sleep休眠的哪个线程呢？简单说，哪个线程调用sleep，就休眠哪个线程。
 */
public class ThreadSleep {
    public static void main(String[] args) {
        ThreadSleep sleep = new ThreadSleep();
        Thread t1 = sleep.new MyThread1();
        Thread t2 = new Thread(sleep.new MyRunnable());
        t1.start();
        t2.start();
    }

    class MyThread1 extends Thread {
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println("线程1第" + i + "次执行！");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyRunnable implements Runnable {
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println("线程2第" + i + "次执行！");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}