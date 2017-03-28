package threads.readWriteLock;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

 
/**
 * 读写锁
 * java5 ReadWriteLock用法--读写锁实现

读写锁：分为读锁和写锁，多个读锁不互斥，读锁与写锁互斥，这是由jvm自己控制的，你只要上好相应的锁即可。如果你的代码只读数据，可以很多人同时读，但不能同时写，那就上读锁；如果你的代码修改数据，只能有一个人在写，且不能同时读取，那就上写锁。总之，读的时候上读锁，写的时候上写锁！

三个线程读数据，三个线程写数据示例：
可以同时读，读的时候不能写，不能同时写，写的时候不能读。
读的时候上读锁，读完解锁；写的时候上写锁，写完解锁。
注意finally解锁。
 * @author Administrator
 * 
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {
        final ReadWrite rw = new ReadWrite();
        for (int i = 0; i < 3; i++) {
            new Thread() {
                public void run() {
                    while (true) {
                        rw.read();
                    }
                }
 
            }.start();
 
            new Thread() {
                public void run() {
                    while (true) {
                        rw.write(new Random().nextInt(10000));
                    }
                }
 
            }.start();
        }
 
    }
}
 

