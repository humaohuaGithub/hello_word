package threads.synchronizedStack;

/**
 * 消费者
 * 
 * @author 林计钦
 * @version 1.0 2013-7-24 下午02:15:43
 */
public class Consumer implements Runnable {
    private SynchronizedStack stack;

    public Consumer(SynchronizedStack s) {
        stack = s;
    }

    public void run() {
        char ch;
        for (int i = 0; i < 100; i++) {
            ch = stack.pop();
            System.out.println("Consumed:" + ch);
            try {
                // 每产生一个字符线程就睡眠一下
                Thread.sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
            }
        }
    }

}
