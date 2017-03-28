package threads.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义连接池
 * 
 * @author 林计钦
 * @version 1.0 2013-7-25 上午10:09:17
 * 五、自定义线程池--ThreadPoolExecutor

public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue)

参数：
corePoolSize
核心线程数，核心线程会一直存活，即使没有任务需要处理。当线程数小于核心线程数时，即使现有的线程空闲，线程池也会优先创建新线程来处理任务，而不是直接交给现有的线程处理。
核心线程在allowCoreThreadTimeout被设置为true时会超时退出，默认情况下不会退出。

maximumPoolSize
当线程数大于或等于核心线程，且任务队列已满时，线程池会创建新的线程，直到线程数量达到maxPoolSize。如果线程数已等于maxPoolSize，且任务队列已满，则已超出线程池的处理能力，线程池会拒绝处理任务而抛出异常。

keepAliveTime
当线程空闲时间达到keepAliveTime，该线程会退出，直到线程数量等于corePoolSize。如果allowCoreThreadTimeout设置为true，则所有线程均会退出直到线程数量为0。

unit
keepAliveTime 参数的时间单位。

workQueue
执行前用于保持任务的队列。此队列仅保持由 execute 方法提交的 Runnable 任务。

抛出：
IllegalArgumentException - 如果corePoolSize或keepAliveTime小于零，或者maximumPoolSize小于或等于零，或者corePoolSize 大于maximumPoolSize。
NullPointerException - 如果workQueue为null

eg、
//创建等待队列
BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(20);
//创建一个单线程执行程序，它可安排在给定延迟后运行命令或者定期地执行。
ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 2, TimeUnit.MILLISECONDS, queue);
//创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
Thread t1 = new MyThread();
Thread t2 = new MyThread();
//将线程放入池中进行执行
pool.execute(t1);
pool.execute(t2);
//关闭线程池
pool.shutdown(); 
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        ThreadPoolTest test = new ThreadPoolTest();

        // 创建等待队列
        BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(20);
        // 创建一个单线程执行程序，它可安排在给定延迟后运行命令或者定期地执行。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 2, TimeUnit.MILLISECONDS, bqueue);
        // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t1 = test.new MyThread();
        Thread t2 = test.new MyThread();
        Thread t3 = test.new MyThread();
        Thread t4 = test.new MyThread();
        Thread t5 = test.new MyThread();
        Thread t6 = test.new MyThread();
        Thread t7 = test.new MyThread();
        // 将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);
        pool.execute(t6);
        pool.execute(t7);
        // 关闭线程池
        pool.shutdown();
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "正在执行。");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
