package threads.concurrentHashMap;

import java.util.Map.Entry;

/**
 * 重新发送客户端未收到的消息，直到客户端接收并确认该信息
 * 
 * @author Administrator
 *
 */
public class SendThread extends Thread {
    @Override
    public void run() {
        try {
            sleep(6000);
            while (SendReceiveTest.pushMessage.size() > 0) {
                for (Entry<Integer, String> hashMap : SendReceiveTest.pushMessage.entrySet()) {
                    System.out.println("消息id:" + hashMap.getKey()+ "未发送成功，在此重发:" + hashMap.getValue());
                }
                sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}