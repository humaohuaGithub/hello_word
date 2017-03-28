package threads.threadSum;

/**
 * 计算1+2+3 ... +100的和
 * 
 * @author 林计钦
 * @version 1.0 2013-7-23 上午10:06:04
 * Java多线程-线程的交互

一、线程交互的基础知识
void notify()：唤醒在此对象监视器上等待的单个线程。
void notifyAll()：唤醒在此对象监视器上等待的所有线程。
void wait()：导致当前的线程等待，直到其他线程调用此对象的 notify()方法或 notifyAll()方法。

当然，wait()还有另外两个重载方法：
void wait(long timeout)：导致当前的线程等待，直到其他线程调用此对象的 notify()方法或 notifyAll()方法，或者超过指定的时间量。
void wait(long timeout, int nanos)：导致当前的线程等待，直到其他线程调用此对象的 notify()方法或 notifyAll()方法，或者其他某个线程中断当前线程，或者已超过某个实际时间量。

关于等待/通知，要记住的关键点是：
必须从同步代码块内调用wait()、notify()、notifyAll()方法。线程不能调用对象上等待或通知的方法，除非它拥有那个对象的锁。

wait()、notify()、notifyAll()都是Object的实例方法。与每个对象具有锁一样，每个对象可以有一个线程列表，他们等待来自该信号（通知）。线程通过执行对象上的wait()方法获得这个等待列表。从那时候起，它不再执行任何其他指令，直到调用对象的notify()方法为止。如果多个线程在同一个对象上等待，则将只选择一个线程（不保证以何种顺序）继续执行。如果没有线程等待，则不采取任何特殊操作。

例子
 */
public class ThreadSum extends Thread {
    int total = 0;

    @Override
    public void run() {

        synchronized (this) {
            for (int i = 0; i < 101; i++) {
                total += i;
            }
            //(完成计算了)唤醒在此对象监视器上等待的单个线程，在本例中线程ThreadInteractionTest被唤醒
            notify();
        }

    }
}