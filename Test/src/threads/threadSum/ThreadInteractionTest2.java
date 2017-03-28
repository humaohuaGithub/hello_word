package threads.threadSum;

/**
 * 线程的交互
 * 
 * @author 林计钦
 * @version 1.0 2013-7-23 上午10:04:11
 */
public class ThreadInteractionTest2 extends Thread{
    ThreadSum2 sum;
    
    public ThreadInteractionTest2(ThreadSum2 sum){
        this.sum=sum;
    }
    
    @Override
    public void run() {
        synchronized (sum) {
            try {
                System.out.println("等待对象sum完成计算。。。");
                // 当前线程ThreadInteractionTest等待
                sum.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sum对象计算的总和是：" + sum.total);
        }
    }
    
    public static void main(String[] args) {
        ThreadSum2 sum = new ThreadSum2();
        
        //启动三个线程，分别获取计算结果 
        new ThreadInteractionTest2(sum).start();
        new ThreadInteractionTest2(sum).start();
        new ThreadInteractionTest2(sum).start();
        
        // 启动计算线程
        sum.start();
        
        /**
         * 等待对象sum完成计算。。。
等待对象sum完成计算。。。
等待对象sum完成计算。。。
sum对象计算的总和是：5050
sum对象计算的总和是：5050
sum对象计算的总和是：5050

         * 谈一下synchronized和wait()、notify()等的关系:
1、有synchronized的地方不一定有wait,notify
2、有wait,notify的地方必有synchronized.这是因为wait和notify不是属于线程类，而是每一个对象都具有的方法，而且，这两个方法都和对象锁有关，有锁的地方，必有synchronized。
另外，注意一点：如果要把notify和wait方法放在一起用的话，必须先调用notify后调用wait，因为如果调用完wait，该线程就已经不是currentthread了。
         */
    }
}