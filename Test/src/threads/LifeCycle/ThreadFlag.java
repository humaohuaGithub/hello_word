package threads.LifeCycle;

/**
 * 
 * 
 * @author 林计钦
 * @version 1.0 2013-7-23 下午02:14:00
 * 从表面上看，使用sleep和suspend所产生的效果类似，但sleep方法并不等同于suspend。它们之间最大的一个区别是可以在一个线程中通过suspend方法来挂起另外一个线程，如上面代码中在主线程中挂起了thread线程。而sleep只对当前正在执行的线程起作用。在上面代码中分别使sleepThread和主线程休眠了2秒和5秒。在使用sleep时要注意，不能在一个线程中来休眠另一个线程。如main方法中使用thread.sleep(2000)方法是无法使thread线程休眠2秒的，而只能使主线程休眠2秒。

在使用sleep方法时有两点需要注意：

1. sleep方法有两个重载形式，其中一个重载形式不仅可以设毫秒，而且还可以设纳秒(1,000,000纳秒等于1毫秒)。但大多数操作系统平台上的Java虚拟机都无法精确到纳秒，因此，如果对sleep设置了纳秒，Java虚拟机将取最接近这个值的毫秒。

2. 在使用sleep方法时必须使用throws或try{...}catch{...}。因为run方法无法使用throws，所以只能使用try{...}catch{...}。当在线程休眠的过程中，使用interrupt方法（这个方法将在2.3.3中讨论）中断线程时sleep会抛出一个InterruptedException异常。sleep方法的定义如下：
public static void sleep(long millis) throws InterruptedException
public static void sleep(long millis, int nanos) throws InterruptedException

三、终止线程的三种方法
有三种方法可以使终止线程。
1. 使用退出标志，使线程正常退出，也就是当run方法完成后线程终止。
2. 使用stop方法强行终止线程（这个方法不推荐使用，因为stop和suspend、resume一样，也可能发生不可预料的结果）。
3. 使用interrupt方法中断线程。

1. 使用退出标志终止线程
当run方法执行完后，线程就会退出。但有时run方法是永远不会结束的。如在服务端程序中使用线程进行监听客户端请求，或是其他的需要循环处理的任务。在这种情况下，一般是将这些任务放在一个循环中，如while循环。如果想让循环永远运行下去，可以使用while(true){...}来处理。但要想使while循环在某一特定条件下退出，最直接的方法就是设一个boolean类型的标志，并通过设置这个标志为true或false来控制while循环是否退出。下面给出了一个利用退出标志终止线程的例子。
 */
public class ThreadFlag extends Thread{
    public volatile boolean exit = false;

    public void run() {
        while (!exit)
            ;
    }

    public static void main(String[] args) throws Exception {
        ThreadFlag thread = new ThreadFlag();
        thread.start();
        sleep(5000); // 主线程延迟5秒
        thread.exit = true; // 终止线程thread
        thread.join();
        System.out.println("线程退出!");
    }
}
