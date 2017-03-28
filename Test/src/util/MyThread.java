package util;

public class MyThread implements Runnable {

    private Data data;

    public MyThread(Data d) {
        data = d;
    }

    @Override
    public void run() {
        while (true)
        {
            int val = data.getEven();
            System.out.println(val + " ");
            if (val % 2 != 0) {
                System.out.println(val + " not even!");
            }
        }
    }

}