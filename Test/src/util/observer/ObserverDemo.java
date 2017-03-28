package util.observer;
/**
 * 在min()内,分别调用observing和observed的Watcher和BegingWatherd对
 * 象被创。然后observing被增加到对observed的观测程序列表。
 * 这意味着每次counter()调用notifyObservers()方法时，
 * observing.update()方法将被调用.
 * @author humaohua
 *
 */
public class ObserverDemo {

	public static void main(String[] args) {
		BeingWatched observed = new BeingWatched();
		Watcher observing = new Watcher();
		//Watcher2 observing2 = new Watcher2();
		/*Add the observing to the list of observers for observed object.*/
		observed.addObserver(observing);
		//observed.addObserver(observing2);
		observed.counter(10);
	}
/**
 * 输出如下：
update() called, count is 10
update() called, count is 9
update() called, count is 8
update() called, count is 7
update() called, count is 6
update() called, count is 5
update() called, count is 4
update() called, count is 3
update() called, count is 2
update() called, count is 1
update() called, count is 0
 */
}
