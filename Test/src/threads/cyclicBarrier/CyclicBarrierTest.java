package threads.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 /**
  * java5 CyclicBarrier同步工具

CyclicBarrier是一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点(common barrier point)。在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时CyclicBarrier很有用。因为该barrier 在释放等待线程后可以重用，所以称它为循环的barrier。

CyclicBarrier支持一个可选的Runnable命令，在一组线程中的最后一个线程到达之后（但在释放所有线程之前），该命令只在每个屏障点运行一次。若在继续所有参与线程之前更新共享状态，此屏障操作很有用。

构造方法摘要 
CyclicBarrier(int parties) 
          创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动，但它不会在启动 barrier 时执行预定义的操作。 
CyclicBarrier(int parties, Runnable barrierAction) 
          创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动，并在启动 barrier 时执行给定的屏障操作，该操作由最后一个进入 barrier 的线程执行。 
  方法摘要 
 int await() 
          在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。 
 int await(long timeout, TimeUnit unit) 
          在所有参与者都已经在此屏障上调用 await 方法之前将一直等待,或者超出了指定的等待时间。 
 int getNumberWaiting() 
          返回当前在屏障处等待的参与者数目。 
 int getParties() 
          返回要求启动此 barrier 的参与者数目。 
 boolean isBroken() 
          查询此屏障是否处于损坏状态。 
 void reset() 
          将屏障重置为其初始状态。 

例如：组织人员（线程）郊游，约定一个时间地点（路障），人员陆续到达地点，等所有人员全部到达，开始到公园各玩各的，再到约定时间去食堂吃饭，等所有人到齐开饭……
  * @author humaohua
  *
  */
public class CyclicBarrierTest {
 
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final  CyclicBarrier cb = new CyclicBarrier(3); //约定3个人
        for(int i=0;i<3;i++){ //产生3个人
            Runnable runnable = new Runnable(){
                    public void run(){
                    try {
                        Thread.sleep((long)(Math.random()*10000)); 
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "即将到达集合地点1，当前已有" + (cb.getNumberWaiting()+1) + "个已经到达，" + (cb.getNumberWaiting()==2?"都到齐了，继续走啊":"正在等候"));                      
                        cb.await();
                         
                        Thread.sleep((long)(Math.random()*10000)); 
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "即将到达集合地点2，当前已有" + (cb.getNumberWaiting()+1) + "个已经到达，" + (cb.getNumberWaiting()==2?"都到齐了，继续走啊":"正在等候"));
                        cb.await();
                         
                        Thread.sleep((long)(Math.random()*10000)); 
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "即将到达集合地点3，当前已有" + (cb.getNumberWaiting() + 1) + "个已经到达，" + (cb.getNumberWaiting()==2?"都到齐了，继续走啊":"正在等候"));                    
                        cb.await();                    
                    } catch (Exception e) {
                        e.printStackTrace();
                    }              
                }
            };
            service.execute(runnable);
        }
        service.shutdown();
    }
}