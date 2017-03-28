package threads.queue;

public class LinkedBlockingQueue {
/**
 * 阻塞队列--LinkedBlockingQueue

什么叫线程安全？
线程安全就是每次运行结果和单线程运行的结果是一样的，而且其他的变量的值也和预期的是一样的。
线程安全就是说多线程访问同一代码，不会产生不确定的结果。

并行和并发区别
1、并行是指两者同时执行一件事，比如赛跑，两个人都在不停的往前跑；
2、并发是指资源有限的情况下，两者交替轮流使用资源，比如一段路(单核CPU资源)同时只能过一个人，A走一段后，让给B，B用完继续给A，交替使用，目的是提高效率。

LinkedBlockingQueue是一个线程安全的阻塞队列，实现了先进先出等特性，是作为生产者消费者的首选，可以指定容量，也可以不指定，不指定的话默认最大是Integer.MAX_VALUE，其中主要用到put和take方法，put方法将一个对象放到队列尾部，在队列满的时候会阻塞直到有队列成员被消费，take方法从head取一个对象，在队列为空的时候会阻塞，直到有队列成员被放进来。
 
add(anObject)：
把anObject添加到BlockingQueue里，添加成功返回true，如果BlockingQueue空间已满则抛出异常。
offer(anObject)：
表示如果可能的话，将anObject加到BlockingQueue里，即如果BlockingQueue可以容纳，则返回true，否则返回false。
put(anObject)：
把anObject加到BlockingQueue里，如果BlockingQueue没有空间，则调用此方法的线程被阻断直到BlockingQueue里有空间再继续。
poll(time)：
获取并移除此队列的头，若不能立即取出，则可以等time参数规定的时间，取不到时返回null。
take()：
获取BlockingQueue里排在首位的对象，若BlockingQueue为空，阻断进入等待状态直到BlockingQueue有新的对象被加入为止。
clear()：
从队列彻底移除所有元素。
remove()方法直接删除队头的元素
peek()方法直接取出队头的元素，并不删除
 */
}
