package threads.multiThreadShareData;

/**
 * 多个线程之间共享数据的方式
 *
 * 设计四个线程，其中两个线程每次对j增加1，另外两个线程每次对j减少1。循环100次。
 *
 * @author Administrator
 *多个线程访问共享对象和数据的方式

多个线程访问共享对象和数据的方式有两种情况：
1、每个线程执行的代码相同，例如，卖票：多个窗口同时卖这100张票，这100张票需要多个线程共享。
2、每个线程执行的代码不同，例如：设计四个线程，其中两个线程每次对j增加1，另外两个线程每次对j减少1。

a、如果每个线程执行的代码相同，可以使用同一个Runnable对象，这个对象中有共享数据。卖票就可以这样做，每个窗口都在做卖票任务，卖的票都是同一个数据(点击查看具体案例)。

b、如果每个线程执行的代码不同，就需要使用不同的Runnable对象，有两种方式实现Runnable对象之间的数据共享：
1）、将共享数据单独封装到一个对象中，同时在对象中提供操作这些共享数据的方法，可以方便实现对共享数据各项操作的互斥和通信。
2、）将各个Runnable对象作为某个类的内部类，共享数据作为外部类的成员变量，对共享数据的操作方法也在外部类中提供，以便实现互斥和通信，内部类的Runnable对象调用外部类中操作共享数据的方法即可。

注意：要同步互斥的几段代码最好分别放在几个独立的方法中，这些方法再放在同一个类中，这样比较容易实现它们之间的同步互斥和通信。
 */
public class MultiThreadShareData {
    private static ShareData data1 = new ShareData();
 
    public static void main(String[] args) {
        ShareData data2 = new ShareData();
        new Thread(new DecrementRunnable(data2)).start();
        new Thread(new IncrementRunnable(data2)).start();
 
        final ShareData data1 = new ShareData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                data1.decrement();
 
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                data1.increment();
 
            }
        }).start();
 
    }
 
}
 
/**
 * 创建线程类，负责对j减少1
 *
 * @author Administrator
 *
 */
class DecrementRunnable implements Runnable {
    private ShareData data;
 
    public DecrementRunnable(ShareData data) {
        this.data = data;
    }
 
    public void run() {
        for(int i=0; i<100; i++){
            data.decrement();
        }
         
    }
}
 
/**
 * 创建线程类，负责对j增加1
 *
 * @author Administrator
 *
 */
class IncrementRunnable implements Runnable {
    private ShareData data;
 
    public IncrementRunnable(ShareData data) {
        this.data = data;
    }
 
    public void run() {
        for(int i=0; i<100; i++){
            data.increment();
        }
         
    }
}
 
/**
 * 封装共享数据
 *
 * @author Administrator
 *
 */
class ShareData {
    private int j = 0;
 
    /**
     * 每次对j增加1
     */
    public synchronized void increment() {
        j++;
        System.out.println("j++="+j);
    }
 
    /**
     * 每次对j减少1
     */
    public synchronized void decrement() {
        j--;
        System.out.println("j--="+j);
    }
     
     
}