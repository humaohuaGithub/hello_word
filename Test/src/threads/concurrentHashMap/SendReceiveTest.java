package threads.concurrentHashMap;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 
ConcurrentHashMap--锁的分段技术
ConcurrentHashMap是Java 5中支持高并发、高吞吐量的线程安全HashMap实现。
HashTable容器在竞争激烈的并发环境下表现出效率低下的原因，是因为所有访问HashTable的线程都必须竞争同一把锁，那假如容器里有多把锁，每一把锁用于锁容器其中一部分数据，那么当多线程访问容器里不同数据段的数据时，线程间就不会存在锁竞争，从而可以有效的提高并发访问效率，这就是ConcurrentHashMap所使用的锁分段技术，首先将数据分成一段一段的存储，然后给每一段数据配一把锁，当一个线程占用锁访问其中一个段数据的时候，其他段的数据也能被其他线程访问。
模拟信息的发送和接收，场景是这样的：
服务器向客户端发送信息，要保证信息100%的发送给客户端，那么发给客户端之后，客户端返回一个消息告诉服务器，已经收到。当服务器一直没有收到客户端返回的消息，那么服务器会一直发送这个信息，直到客户端接收并确认该信息，这时候再删除重复发送的这个信息。
为了模拟该场景，这里写两个线程，一个是发送线程，一个是接收线程，把要发送的信息保存到线程安全的对象里面，防止发生线程安全问题，这里采用ConcurrentHashMap。
 * @author humaohua
 *
 */
public class SendReceiveTest {
    public static ConcurrentHashMap<Integer, String> pushMessage = new ConcurrentHashMap<Integer, String>();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            pushMessage.put(i, "该消息是id为" + i + "的消息");
        }
        Thread sendThread = new SendThread();
        Thread receiveThread = new ReceiveThread();
        sendThread.start();
        receiveThread.start();
        for (int i = 5; i < 10; i++) {
            pushMessage.put(i, "该消息是id为" + i + "的消息");
        }
    }
}
