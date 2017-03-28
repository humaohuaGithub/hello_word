package util.observer;

import java.util.Observable;
import java.util.Observer;

public class Watcher implements Observer {
	public void update(Observable obj,Object arg){
			System.out.println("update() called, count is "+ ((Integer)arg).intValue());
	}
}
