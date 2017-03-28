package util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    
    
    public static void main(String[] args) {
        Data data=new Data();//多个线程操作的是同一个对象
        ExecutorService executorService=Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.execute(new MyThread(data));
        }
        executorService.shutdown();//关闭线程池，如果不关闭线程池将一直运行。
    }
}
