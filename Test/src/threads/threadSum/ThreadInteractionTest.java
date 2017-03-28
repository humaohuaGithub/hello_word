package threads.threadSum;

/**
 * 线程的交互
 * 
 * @author 林计钦
 * @version 1.0 2013-7-23 上午10:04:11
 */
public class ThreadInteractionTest {
    public static void main(String[] args) {
        
        ThreadSum sum = new ThreadSum();
        // 启动计算线程
        sum.start();
        // 线程ThreadInteractionTest拥有sum对象上的锁。
        // 线程为了调用wait()或notify()方法，该线程ThreadInteractionTest必须是那个对象锁的拥有者
        synchronized (sum) {
            try {
                System.out.println("等待对象sum完成计算。。。");
                // 当前线程ThreadInteractionTest等待
                sum.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sum对象计算的总和是：" + sum.total);
        }
        /**
         * 等待对象sum完成计算。。。
sum对象计算的总和是：5050
         */
    }
}
