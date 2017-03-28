package threads.LifeCycle;
/**
 * 在上面代码中定义了一个退出标志exit，当exit为true时，while循环退出，exit的默认值为false。在定义exit时，使用了一个Java关键字volatile，这个关键字的目的是使exit同步，也就是说在同一时刻只能由一个线程来修改exit的值.

2. 使用stop方法终止线程
使用stop方法可以强行终止正在运行或挂起的线程。我们可以使用如下的代码来终止线程：
thread.stop();
虽然使用上面的代码可以终止线程，但使用stop方法是很危险的，就象突然关闭计算机电源，而不是按正常程序关机一样，可能会产生不可预料的结果，因此，并不推荐使用stop方法来终止线程。

3. 使用interrupt方法终止线程

使用interrupt方法来终端线程可分为两种情况：

（1）线程处于阻塞状态，如使用了sleep方法。

（2）使用while(!isInterrupted()){...}来判断线程是否被中断。

在第一种情况下使用interrupt方法，sleep方法将抛出一个InterruptedException例外，而在第二种情况下线程将直接退出。下面的代码演示了在第一种情况下使用interrupt方法。
 * @author humaohua
 *
 */
public class ThreadInterrupt extends Thread {
    public void run() {
        try {
            sleep(10000); // 延迟10秒
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        Thread thread = new ThreadInterrupt();
        thread.start();
        System.out.println("在10秒之内按任意键中断线程!");
        System.in.read();
        thread.interrupt();
        thread.join();
        System.out.println("线程已经退出!");
    }
    /**
     * 在调用interrupt方法后， sleep方法抛出异常，然后输出错误信息：sleep interrupted。

注意：在Thread类中有两个方法可以判断线程是否通过interrupt方法被终止。一个是静态的方法interrupted()，一个是非静态的方法isInterrupted()，这两个方法的区别是interrupted用来判断当前线是否被中断，而isInterrupted可以用来判断其他线程是否被中断。因此，while (!isInterrupted())也可以换成while (!Thread.interrupted())。

以上就是线程的生命周期。要进一步学习Java多线程，务必要对Java线程生命周期有着足够的认识。
     */
}
