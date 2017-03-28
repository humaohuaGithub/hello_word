package threads.threadYield;

/**
 * 线程的调度(让步)
 * 
 * @author 林计钦
 * @version 1.0 2013-7-24 上午09:42:41
 * Java多线程-线程的调度(让步)

线程的让步含义就是使当前运行着线程让出CPU资源，但是扔给谁不知道，仅仅是让出，线程状态回到可运行状态。

线程的让步使用Thread.yield()方法，yield()为静态方法，功能是暂停当前正在执行的线程对象，并执行其他线程。
 */
public class ThreadYield {
    public static void main(String[] args) {
        ThreadYield threadYield = new ThreadYield();
        Thread t1 = threadYield.new MyThread1();
        Thread t2 = new Thread(threadYield.new MyRunnable());

        t2.start();
        t1.start();
    }

    class MyThread1 extends Thread {
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("线程1第" + i + "次执行！");
            }
        }
    }

    class MyRunnable implements Runnable {
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("线程2第" + i + "次执行！");
                Thread.yield();
            }
        }
    }
}