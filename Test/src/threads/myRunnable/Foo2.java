package threads.myRunnable;

public class Foo2 {
    private int x = 100;

    public int getX() {
        return x;
    }

    //同步方法
    public synchronized int fix(int y) {
        x = x - y;
        System.out.println("线程"+Thread.currentThread().getName() + "运行结束，减少“" + y
                + "”，当前值为：" + x);
        return x;
    }
    
//    //同步代码块
//    public int fix(int y) {
//        synchronized (this) {
//            x = x - y;
//            System.out.println("线程"+Thread.currentThread().getName() + "运行结束，减少“" + y
//                    + "”，当前值为：" + x);
//        }
//        
//        return x;
//    }

}