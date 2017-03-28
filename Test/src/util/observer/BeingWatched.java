package util.observer;

import java.util.Observable;
/**
 *  被监控的类－该类扩展了Observable.
 *  counter()方法，该方法仅是从一个指定的值开始递减计数。
 *  它使用sleep()方法在两次计数中间等侍十分一秒。每次计数改变时，
 *  notifyObservers()方法被调用，而当前的计数被作为参数传递给 notifyObservers()方法
 *  这导致了Watcher中的update()方法被调用，显示当前的计数值。
 * @author humaohua
 *
 */
public class BeingWatched extends Observable{
	void counter(int period){
		for(; period >=0; period--){
			setChanged();
			notifyObservers(new Integer(period));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Sleep interrupted");
			}
		}
	}
}
