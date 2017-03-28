package threads.producerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * java多线程模拟生产者消费者问题
 * 
 * ProducerConsumer是主类，Producer生产者，Consumer消费者，Product产品，Storage仓库
 * 
 * @author 林计钦
 * @version 1.0 2013-7-24 下午04:49:02
 * Java多线程-并发协作(生产者消费者模型)

对于多线程程序来说，不管任何编程语言，生产者和消费者模型都是最经典的。就像学习每一门编程语言一样，Hello World！都是最经典的例子。

实际上，准确说应该是“生产者-消费者-仓储”模型，离开了仓储，生产者消费者模型就显得没有说服力了。
对于此模型，应该明确一下几点：
1、生产者仅仅在仓储未满时候生产，仓满则停止生产。
2、消费者仅仅在仓储有产品时候才能消费，仓空则等待。
3、当消费者发现仓储没产品可消费时候会通知生产者生产。
4、生产者在生产出可消费产品时候，应该通知等待的消费者去消费。
 */
public class ProducerConsumer {
    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();

        Storage s = pc.new Storage();

        ExecutorService service = Executors.newCachedThreadPool();
        Producer p = pc.new Producer("张三", s);
        Producer p2 = pc.new Producer("李四", s);
        Consumer c = pc.new Consumer("王五", s);
        Consumer c2 = pc.new Consumer("老刘", s);
        Consumer c3 = pc.new Consumer("老林", s);
        service.submit(p);
        //service.submit(p2);
        service.submit(c);
        service.submit(c2);
        service.submit(c3);
        
    }

    /**
     * 消费者
     * 
     * @author 林计钦
     * @version 1.0 2013-7-24 下午04:53:30
     */
    class Consumer implements Runnable {
        private String name;
        private Storage s = null;

        public Consumer(String name, Storage s) {
            this.name = name;
            this.s = s;
        }

        public void run() {
            try {
                while (true) {
                    System.out.println(name + "准备消费产品.");
                    Product product = s.pop();
                    System.out.println(name + "已消费(" + product.toString() + ").");
                    System.out.println("===============");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 生产者
     * 
     * @author 林计钦
     * @version 1.0 2013-7-24 下午04:53:44
     */
    class Producer implements Runnable {
        private String name;
        private Storage s = null;

        public Producer(String name, Storage s) {
            this.name = name;
            this.s = s;
        }

        public void run() {
            try {
                while (true) {
                    Product product = new Product((int) (Math.random() * 10000)); // 产生0~9999随机整数
                    System.out.println(name + "准备生产(" + product.toString() + ").");
                    s.push(product);
                    System.out.println(name + "已生产(" + product.toString() + ").");
                    System.out.println("===============");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        }
    }

    /**
     * 仓库，用来存放产品
     * 
     * @author 林计钦
     * @version 1.0 2013-7-24 下午04:54:16
     */
    public class Storage {
        BlockingQueue<Product> queues = new LinkedBlockingQueue<Product>(10);

        /**
         * 生产
         * 
         * @param p
         *            产品
         * @throws InterruptedException
         */
        public void push(Product p) throws InterruptedException {
            queues.put(p);
        }

        /**
         * 消费
         * 
         * @return 产品
         * @throws InterruptedException
         */
        public Product pop() throws InterruptedException {
            return queues.take();
        }
    }

    /**
     * 产品
     * 
     * @author 林计钦
     * @version 1.0 2013-7-24 下午04:54:04
     */
    public class Product {
        private int id;

        public Product(int id) {
            this.id = id;
        }

        public String toString() {// 重写toString方法
            return "产品：" + this.id;
        }
    }

}
