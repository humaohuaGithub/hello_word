package threads.Q;
/**
 *	内部get(),wait()被调用。这使执行挂起直到Producer 告知数据已经准备好，
	 * 这时内部get()被恢复执行。获取数据后，get()调用notify()。这告诉Producer
	 * 可以向序列中输入更多数据。在put()内，wait()挂起执行直到
	 * Consumer取走了序列中项目。当执行再继续下一个数据项目被放入序列，
	 * notify()被调用，这通知Consumer它应该移走该数据。
 * @author humaohua
 *
 */
public class Q {
	/*int n;
	synchronized int get(){
		System.out.println("Got: "+ n);
		return n ;
	}
	synchronized void put(int n){
		this.n = n;
		System.out.println("Put: "+ n);
	}*/
	int n;
	boolean valueSet = false;
	synchronized int get(){
		if(!valueSet){
			try {
				wait();//等侍生产者，put进来一个数
			} catch (InterruptedException e) {
				System.out.println("InterruptedException caught");
				//e.printStackTrace();
			}
		}
		System.out.println("Got: "+ n);
		valueSet = false;
		notify();//通知一个等侍生产者线程去生产一个数
		return n ;
	}
	synchronized void put(int n){
		if(valueSet){
			try {
				wait();//等待消费者完成才可以去生产新的数字
			} catch (InterruptedException e) {
				System.out.println("InterruptedException caught");
				//e.printStackTrace();
			}
		}
		this.n = n;
		valueSet = true;
		System.out.println("Put: "+ n);
		notify();//通知一个消费者线程去消费
	}
}
