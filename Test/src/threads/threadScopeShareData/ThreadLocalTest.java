package threads.threadScopeShareData;

import java.util.Random;
/**
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
/**
 * ThreadLocal类及应用技巧
 *
 * 将线程范围内共享数据进行封装，封装到一个单独的数据类中，提供设置获取方法
 * 将该类单例化，提供获取实例对象的方法，获取到的实例对象是已经封装好的当前线程范围内的对象
 * 
 */
public class ThreadLocalTest {
 
    private static ThreadLocal<Integer> x = new ThreadLocal<Integer>();
    //private static ThreadLocal<MyThreadScopeData> myThreadScopeData = new ThreadLocal<MyThreadScopeData>();
    public static void main(String[] args) {
        for(int i=0;i<2;i++){
            new Thread(new Runnable(){
                @Override
                public void run() {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + " has put data :" + data);
                    x.set(data);
                     
                    /*                 
                    MyThreadScopeData myData = new MyThreadScopeData();
                    myData.setName("name" + data);
                    myData.setAge(data);
                    myThreadScopeData.set(myData);
                    */
                    MyThreadScopeData.getThreadInstance().setName("name" + data);
                    MyThreadScopeData.getThreadInstance().setAge(data);
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }
     
    //使用获取到的线程范围内的对象实例调用相应方法
    static class A{
        public void get(){
            int data = x.get();
            System.out.println("A from " + Thread.currentThread().getName() + " get data :" + data);
             
            /*         
            MyThreadScopeData myData = myThreadScopeData.get();
            System.out.println("A from " + Thread.currentThread().getName()
                    + " getMyData: " + myData.getName() + "," + myData.getAge());
            */
            MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
            System.out.println("A from " + Thread.currentThread().getName()
                    + " getMyData: " + myData.getName() + "," + myData.getAge());
        }
    }
     
    //使用获取到的线程范围内的对象实例调用相应方法
    static class B{
        public void get(){
            int data = x.get();        
            System.out.println("B from " + Thread.currentThread().getName() + " get data :" + data);
             
            MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
            System.out.println("B from " + Thread.currentThread().getName()
                    + " getMyData: " + myData.getName() + "," + myData.getAge());          
        }      
    }
}
 
 
class MyThreadScopeData {
 
    // 单例
    private MyThreadScopeData() {
    }
 
    // 提供获取实例方法，不加synchronized关键字表示线程各拿各自的数据，互不干扰
    public static/* synchronized */MyThreadScopeData getThreadInstance() {
        // 从当前线程范围内数据集中获取实例对象
        MyThreadScopeData instance = map.get();
        if (instance == null) {
            instance = new MyThreadScopeData();
            map.set(instance);
        }
        return instance;
    }
 
    // 将实例对象存入当前线程范围内数据集中
    private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<MyThreadScopeData>();
 
    private String name;
    private int age;
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public int getAge() {
        return age;
    }
 
    public void setAge(int age) {
        this.age = age;
    }
}
