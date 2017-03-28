/**
 * 
 */
package threads.Q;

/**
 * 线程间通信 wait(),notify(),notifyAll()
 * 下面的例子程序执行了一个简单的生产者、消费者的问题，
 * 它由四个类组成：Q,设法获得同步的序列：Producer,
 * 产生排队的线程对象：Consumer,
 * 消费序列的线程对象：PC,
 * 创建单个Q，Producer,和Consumer的小类.
 * @author humaohua
 *
 */
public class PC {
	public static void main(String args[]){
		Q q = new Q();
		new Producer(q);
		new Consumer(q);		
		System.out.println(" press Control-C to stop.");
	}
	/**
	 * 内部get(),wait()被调用。这使执行挂起直到Producer 告知数据已经准备好，
	 * 这时内部get()被恢复执行。获取数据后，get()调用notify()。这告诉Producer
	 * 可以向序列中输入更多数据。在put()内，wait()挂起执行直到
	 * Consumer取走了序列中项目。当执行再继续下一个数据项目被放入序列，
	 * notify()被调用，这通知Consumer它应该移走该数据。
	 */
}
