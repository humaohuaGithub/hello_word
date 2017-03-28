package threads.threadScopeShareData;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 线程范围内的共享变量
 * 
 * 三个模块共享数据，主线程模块和AB模块
 * 
 * @author Administrator
 * ThreadLocal的作用和目的：
用于实现线程内的数据共享，即对于相同的程序代码，多个模块在同一个线程中运行时要共享一份数据，而在另外线程中运行时又共享另外一份数据。

每个线程调用全局ThreadLocal对象的set方法，就相当于往其内部的map中增加一条记录，key分别是各自的线程，value是各自的set方法传进去的值。在线程结束时可以调用ThreadLocal.clear()方法，这样会更快释放内存，不调用也可以，因为线程结束后也可以自动释放相关的ThreadLocal变量。

ThreadLocal的应用场景：
订单处理包含一系列操作：减少库存量、增加一条流水台账、修改总账，这几个操作要在同一个事务中完成，通常也即同一个线程中进行处理，如果累加公司应收款的操作失败了，则应该把前面的操作回滚，否则，提交所有操作，这要求这些操作使用相同的数据库连接对象，而这些操作的代码分别位于不同的模块类中。

银行转账包含一系列操作： 把转出帐户的余额减少，把转入帐户的余额增加，这两个操作要在同一个事务中完成，它们必须使用相同的数据库连接对象，转入和转出操作的代码分别是两个不同的帐户对象的方法。

例如Strut2的ActionContext，同一段代码被不同的线程调用运行时，该代码操作的数据是每个线程各自的状态和数据，对于不同的线程来说，getContext方法拿到的对象都不相同，对同一个线程来说，不管调用getContext方法多少次和在哪个模块中getContext方法，拿到的都是同一个。

实验案例：定义一个全局共享的ThreadLocal变量，然后启动多个线程向该ThreadLocal变量中存储一个随机值，接着各个线程调用另外其他多个类的方法，这多个类的方法中读取这个ThreadLocal变量的值，就可以看到多个类在同一个线程中共享同一份数据。

实现对ThreadLocal变量的封装，让外界不要直接操作ThreadLocal变量。
对基本类型的数据的封装，这种应用相对很少见。
对对象类型的数据的封装，比较常见，即让某个类针对不同线程分别创建一个独立的实例对象。
 */
public class ThreadScopeShareData {
    // 准备共享的数据
    private static int data = 0;
    // 存放各个线程对应的数据
    private static Map<Thread, Integer> threadData = new HashMap<Thread, Integer>();

    public static void main(String[] args) {
        // 启动两个线程
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 现在当前线程中修改一下数据,给出修改信息
                    int data = new Random().nextInt();
                    // 将线程信息和对应数据存储起来
                    threadData.put(Thread.currentThread(), data);
                    System.out.println(Thread.currentThread().getName() + " has put data :" + data);
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A {
        public void get() {
            int data = threadData.get(Thread.currentThread());
            System.out.println("A from " + Thread.currentThread().getName()
                    + " get data :" + data);
        }
    }

    static class B {
        public void get() {
            int data = threadData.get(Thread.currentThread());
            System.out.println("B from " + Thread.currentThread().getName()
                    + " get data :" + data);
        }
    }
}


