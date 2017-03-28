package threads.LifeCycle;

/**
 * 生命周期
 * 
 * @author 林计钦
 * @version 1.0 2013-7-23 下午01:53:55
 * Java多线程-线程的生命周期

线程可以分为4个状态：
New(新生)，
Runnable(可运行)：为了方便分析，还可将其分为：Runnable与Running。
blocked(被阻塞)，
Dead(死亡)。

与人有生老病死一样，线程也同样要经历开始(等待)、运行、挂起和停止四种不同的状态。这四种状态都可以通过Thread类中的方法进行控制。下面给出了Thread类中和这四种状态相关的方法。
复制代码

// 开始线程  
public void start( );  
public void run( );  
 
// 挂起和唤醒线程  
public void resume( );     // 不建议使用  
public void suspend( );    // 不建议使用  
public static void sleep(long millis);  
public static void sleep(long millis, int nanos);  
public final native void wait() throws InterruptedException;
public final native void notify();
public final native void notifyAll();
 
// 终止线程  
public void stop( );       // 不建议使用  
public void interrupt( );  
 
// 得到线程状态  
public boolean isAlive( );  
public boolean isInterrupted( );  
public static boolean interrupted( );  
 
// join方法  
public void join( ) throws InterruptedException; 

复制代码

一、创建并运行线程

线程在建立后并不马上执行run方法中的代码，而是处于等待状态。线程处于等待状态时，可以通过Thread类的方法来设置线程各种属性，如线程的优先级（setPriority）、线程名(setName)和线程的类型（setDaemon）等。

当调用start方法后，线程开始执行run方法中的代码。线程进入运行状态。可以通过Thread类的isAlive方法来判断线程是否处于运行状态。当线程处于运行状态时，isAlive返回true，当isAlive返回false时，可能线程处于等待状态，也可能处于停止状态。下面的代码演示了线程的创建、运行和停止三个状态之间的切换，并输出了相应的isAlive返回值。

 */
public class LifeCycle extends Thread {
    public void run() {
        int n = 0;
        while ((++n) < 1000)
            ;
    }

    public static void main(String[] args) throws Exception {
        LifeCycle thread = new LifeCycle();
        System.out.println("isAlive: " + thread.isAlive());
        thread.start();
        System.out.println("isAlive: " + thread.isAlive());
        thread.join(); // 等线程thread结束后再继续执行
        System.out.println("thread已经结束!");
        System.out.println("isAlive: " + thread.isAlive());
    }
    /**
     * 要注意一下，在上面的代码中使用了join方法，这个方法的主要功能是保证线程的run方法完成后程序才继续运行，这个方法将在后面的文章中介绍

上面代码的运行结果：
isAlive: false
isAlive: true
thread已经结束!
isAlive: false
     */
}