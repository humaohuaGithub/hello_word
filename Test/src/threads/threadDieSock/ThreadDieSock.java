package threads.threadDieSock;

/**
 * 线程死锁
 * 
 * @author 林计钦
 * @version 1.0 2013-7-24 上午10:42:20
 * 多线程死锁问题

前天俺们谈到了加锁(线程同步)，但是在使用加锁的同时又会带来一个问题，就是死锁。
什么叫死锁？
所谓死锁: 是指两个或两个以上的进程在执行过程中，因争夺资源而造成的一种互相等待的现象，若无外力作用，它们都将无法推进下去。

发生死锁的原因一般是两个对象的锁相互等待造成的。

那么为什么会产生死锁呢？
1.因为系统资源不足。
2.进程运行推进的顺序不合适。   
3.资源分配不当。
            
学过操作系统的朋友都知道：产生死锁的条件有四个：
1.互斥条件：所谓互斥就是进程在某一时间内独占资源。
2.请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放。
3.不剥夺条件:进程已获得资源，在末使用完之前，不能强行剥夺。
4.循环等待条件:若干进程之间形成一种头尾相接的循环等待资源关系。

   

例如：     

死锁是因为多线程访问共享资源，由于访问的顺序不当所造成的，通常是一个线程锁定了一个资源A，而又想去锁定资源B；在另一个线程中，锁定了资源B，而又想去锁定资源A以完成自身的操作，两个线程都想得到对方的资源，而不愿释放自己的资源，造成两个线程都在等待，而无法执行的情况。

分析死锁产生的原因不难看出是由访问共享资源顺序不当所造成的，下面写一个造成线程死锁的例子，希望能对大家理解多线程死锁问题有进一步的理解！如果有人需要编写多线程的系统，当操作共享资源时一定要特别的小心，以防出现死锁的情况！
 */
public class ThreadDieSock implements Runnable {
    private int flag = 1;
    private Object obj1 = new Object(), obj2 = new Object();

    public void run() {
        System.out.println("flag=" + flag);
        if (flag == 1) {
            synchronized (obj1) {
                System.out.println("我已经锁定obj1，休息0.5秒后锁定obj2去！");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj2) {
                    System.out.println("1");
                }
            }
        }
        if (flag == 0) {
            synchronized (obj2) {
                System.out.println("我已经锁定obj2，休息0.5秒后锁定obj1去！");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj1) {
                    System.out.println("0");
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadDieSock run01 = new ThreadDieSock();
        ThreadDieSock run02 = new ThreadDieSock();
        run01.flag = 1;
        run02.flag = 0;
        Thread thread01 = new Thread(run01);
        Thread thread02 = new Thread(run02);
        System.out.println("线程开始喽！");
        thread01.start();
        thread02.start();
    }
    /**
     * 这样就产生了死锁，这是我们过多的使用同步而产生的。我们在java中使用synchonized的时候要考虑这个问题，如何解决死锁，大家可以从死锁的四个条件去解决，只要破坏了一个必要条件，那么我们的死锁就解决了。在java中使用多线程的时候一定要考虑是否有死锁的问题哦。
     */
}
