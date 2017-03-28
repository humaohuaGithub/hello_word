package threads.threadSum;

/**
 * 计算1+2+3 ... +100的和
 * 
 * @author 林计钦
 * @version 1.0 2013-7-23 上午10:06:04
 * 注意：当在对象上调用wait()方法时，执行该代码的线程立即放弃它在对象上的锁。然而调用notify()时，并不意味着这时线程会放弃其锁。如果线程仍然在完成同步代码，则线程在移出之前不会放弃锁。因此，只要调用notify()并不意味着这时该锁变得可用。

二、多个线程在等待一个对象锁时使用notifyAll()
在多数情况下，最好通知等待某个对象的所有线程。如果这样做，可以在对象上使用notifyAll()让所有在此对象上等待的线程冲出等待区，返回到可运行状态。
 */
public class ThreadSum2 extends Thread {
    int total = 0;

    @Override
    public void run() {

        synchronized (this) {
            for (int i = 0; i < 101; i++) {
                total += i;
            }
            //通知所有在此对象上等待的线程 
            notifyAll();
        }

    }
}