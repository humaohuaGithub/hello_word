package threads.synchronizedStack;

/**
 * 同步堆栈类
 * 
 * @author 林计钦
 * @version 1.0 2013-7-24 下午02:03:21
 */
public class SynchronizedStack {
    private int index = 0;
    private int size = 100;
    // 内存共享区
    private char[] data;

    public SynchronizedStack(int size) {
        System.out.println("栈被创建");
        this.size = size;
        data = new char[size];
    }

    /**
     * 生产数据
     * 
     * @param c
     */
    public synchronized void push(char c) {
        while (index == size) {
            try {
                System.err.println("栈满了");
                this.wait();// 等待，直到有数据出栈
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        data[index] = c;
        index++;
        this.notify();// 通知其它线程把数据出栈
    }

    /**
     * 消费数据
     * 
     * @return
     */
    public synchronized char pop() {
        while (index == 0) {
            try {
                System.err.println("栈空了");
                this.wait();// 等待，直到有数据出栈
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        index--; // 指针向下移动
        char ch = data[index];
        this.notify(); // 通知其它线程把数据入栈
        return ch;
    }

    // 显示堆栈内容
    public synchronized void print() {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i]);
        }
        System.out.println();
        this.notify(); // 通知其它线程显示堆栈内容
    }
}