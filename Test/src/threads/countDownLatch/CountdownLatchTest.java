package threads.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 /**
  * http://www.cnblogs.com/linjiqin/p/4509882.html
  * java5 CountDownLatch同步工具

好像倒计时计数器，调用CountDownLatch对象的countDown方法就将计数器减1，当到达0时，所有等待者就开始执行。

java.util.concurrent.CountDownLatch
一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。用给定的计数初始化CountDownLatch。由于调用了countDown()方法，所以在当前计数到达零之前，await方法会一直受阻塞。之后，会释放所有等待的线程，await的所有后续调用都将立即返回。这种现象只出现一次——计数无法被重置。如果需要重置计数，请考虑使用CyclicBarrier。

CountDownLatch是一个通用同步工具，它有很多用途。将计数1初始化的CountDownLatch用作一个简单的开/关锁存器，或入口：在通过调用countDown()的线程打开入口前，所有调用await的线程都一直在入口处等待。用N初始化的 CountDownLatch可以使一个线程在N个线程完成某项操作之前一直等待，或者使其在某项操作完成N次之前一直等待。

CountDownLatch的一个有用特性是，它不要求调用countDown方法的线程等到计数到达零时才继续，而在所有线程都能通过之前，它只是阻止任何线程继续通过一个await。

举例：多个运动员等待裁判命令：裁判等所有运动员到齐后发布结果
  * @author humaohua
  *
  */
public class CountdownLatchTest {
 
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        //裁判发布命令的计数器，计数器为0，运动员就跑
        final CountDownLatch cdOrder = new CountDownLatch(1);
        //运动员跑到终点的计数器，为0裁判宣布结果
        final CountDownLatch cdAnswer = new CountDownLatch(3); 
         
        //产生3个运动员
        for(int i=0;i<3;i++){
            Runnable runnable = new Runnable(){
                    public void run(){
                    try {
                        System.out.println("线程" + Thread.currentThread().getName() + "正准备接受命令");                       
                        cdOrder.await();
                        System.out.println("线程" + Thread.currentThread().getName() + "已接受命令");                             
                        Thread.sleep((long)(Math.random()*10000)); 
                        System.out.println("线程" + Thread.currentThread().getName() + "回应命令处理结果");                      
                        cdAnswer.countDown();                      
                    } catch (Exception e) {
                        e.printStackTrace();
                    }              
                }
            };
            service.execute(runnable); //运动员开始任务
        }      
         
         
        try {
            //裁判休息一会 再发布命令
            Thread.sleep((long)(Math.random()*10000));
         
            System.out.println("线程" + Thread.currentThread().getName() + "即将发布命令");                    
            cdOrder.countDown(); //命令计数器置为0，发布命令
            System.out.println("线程" + Thread.currentThread().getName() + "已发送命令，正在等待结果");  
            cdAnswer.await(); //等待所有运动员，计数器为0 所有运动员到位
            System.out.println("线程" + Thread.currentThread().getName() + "已收到所有响应结果"); 
        } catch (Exception e) {
            e.printStackTrace();
        }              
        service.shutdown();
 
    }
}
