package threads.synchronizedStack;


/**
 * 多线程模拟实现生产者／消费者模型

 题目如下：
      在生产者／消费者模型中，生产者Producer负责生产数据，而消费者Consumer负责使用数据。多个生产者线程会在同一时间运行，生产数据，并放到内存中一个共享的区域。期间，多个消费者线程读取内存共享区，消费里面的数据。
     

分析

在下面Java应用程序中，生产者线程向一个线程安全的堆栈缓冲区中写（PUSH）数据，消费者从该堆栈缓冲区中读（POP）数据，这样，这个程序中同时运行的两个线程共享同一个堆栈缓冲区资源。

类Producer是生产者模型，其中的run方法中定义了生产者线程所做的操作，循环调用push()方法,将生产的100个字母送入堆栈中，每次执行完push操作后，调用sleep方法睡眠一段随机时间。

类Consumer是消费者模型，循环调用pop方法，从堆栈取出一个字母，一共取100次，每次执行完push操作后，调用sleep方法睡眠一段随机时间
 * 
 * @author 林计钦
 * @version 1.0 2013-7-24 下午02:18:36
 */
public class ProductConsumerTest {
    public static void main(String[] args) {
        // 下面的消费者类对象和生产者类对象所操作的是同一个同步堆栈对象
        SynchronizedStack stack = new SynchronizedStack(5);
        Runnable source = new Producer(stack);
        Runnable sink = new Consumer(stack);

        Thread t1 = new Thread(source);
        Thread t2 = new Thread(sink);
        t1.start();
        t2.start();
    }
    
    /**
     * 在本例中，使用了一个生产者线程和一个消费者线程，当生产者线程往堆栈中添加字符时，如果堆栈已满，通过调用this.wait方法，（这里，this就是互斥锁）把自己加入到互斥锁对象（SynchronizedStack）的锁等待队列中，如果该堆栈不满，则该生产者线程加入到互斥锁对象（SynchronizedStack）的锁申请队列中，并且很快就被JVM取出来执行。当生产者线程在执行添加字符操作的时候，消费者是不能从中取出字符的，只能在等待队列中等待，当生产者添加完字符的时候，使用this.notify()（这里，this就是互斥锁）把等待队列中的第一个消费者唤醒，把它加入到锁申请队列中，很快该消费者线程就会获得CPU时间。此时的生产者线程已经无法再次添加字符，因为消费者线程正在synchronized代码块中运行，JVM把生产者线程加入锁等待队列中。当消费者线程从堆栈中取完字符后，再使用this.notify()方法把生产者从等待进程中唤醒，添加字符，如此循环往复，直到生产者线程和消费者线程都运行结束。
     * 
     * 下面是该程序的某次运行结果：
复制代码
栈被创建
栈空了
Produced:W
Consumed:W
栈空了
Produced:C
Consumed:C
栈空了
Produced:R
Consumed:R
栈空了
Produced:R
Consumed:R
Produced:Z
Consumed:Z
Produced:A
Consumed:A
Produced:W
Consumed:W
Produced:P
Consumed:P
Produced:C
Consumed:C
Produced:X
Consumed:X
栈空了
Produced:Q
Consumed:Q
栈空了
Consumed:R
Produced:R
Produced:S

     */
}