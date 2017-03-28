package threads.myRunnable;
/**
 * Java多线程-线程的同步与锁

一、同步问题提出

线程的同步是为了防止多个线程访问一个数据对象时，对数据造成的破坏。
例如：两个线程ThreadA、ThreadB都操作同一个对象Foo对象，并修改Foo对象上的数据。
 * @author humaohua
 *
 */
public class MyRunnable implements Runnable {
    private Foo foo = new Foo();

    public static void main(String[] args) {
        MyRunnable run = new MyRunnable();
        Thread ta = new Thread(run, "Thread-A");
        Thread tb = new Thread(run, "Thread-B");
        ta.start();
        tb.start();
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            this.fix(30);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " : 当前foo对象的x值= " + foo.getX());
        }
    }

    public int fix(int y) {
        return foo.fix(y);
    }
/**
 * 从结果发现，这样的输出值明显是不合理的。原因是两个线程不加控制的访问Foo对象并修改其数据所致。

如果要保持结果的合理性，只需要达到一个目的，就是将对Foo的访问加以限制，每次只能有一个线程在访问。这样就能保证Foo对象中数据的合理性了。

在具体的Java代码中需要完成一下两个操作：
把竞争访问的资源类Foo变量x标识为private；
同步哪些修改变量的代码，使用synchronized关键字同步方法或代码。
 */
}