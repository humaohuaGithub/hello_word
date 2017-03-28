package threads.LifeCycle;
/**
 * 二、挂起和唤醒线程

一但线程开始执行run方法，就会一直到这个run方法执行完成这个线程才退出。但在线程执行的过程中，可以通过两个方法使线程暂时停止执行。这两个方法是suspend和sleep。在使用suspend挂起线程后，可以通过resume方法唤醒线程。而使用sleep使线程休眠后，只能在设定的时间后使线程处于就绪状态（在线程休眠结束后，线程不一定会马上执行，只是进入了就绪状态，等待着系统进行调度）。

虽然suspend和resume可以很方便地使线程挂起和唤醒，但由于使用这两个方法可能会造成一些不可预料的事情发生，因此，这两个方法被标识为deprecated(抗议)标记，这表明在以后的jdk版本中这两个方法可能被删除，所以尽量不要使用这两个方法来操作线程。下面的代码演示了sleep、suspend和resume三个方法的使用。
 * @author humaohua
 *
 */
public class MyThread extends Thread {
    class SleepThread extends Thread {
        public void run() {
            try {
                sleep(2000);
                System.out.println("sleep 2s");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        while (true)
            System.out.println(new java.util.Date().getTime());
    }

    public static void main(String[] args) throws Exception {
        MyThread thread = new MyThread();
        SleepThread sleepThread = thread.new SleepThread();
        sleepThread.start(); // 开始运行线程sleepThread
        sleepThread.join(); // 使线程sleepThread延迟2秒
        thread.start();
        boolean flag = false;
        while (true) {
            sleep(5000); // 使主线程延迟5秒
            System.out.println("sleep 5s");
            flag = !flag;
            if (flag)
                thread.suspend(); //挂起线程
            else
                thread.resume(); //唤醒线程
        }
    }
}