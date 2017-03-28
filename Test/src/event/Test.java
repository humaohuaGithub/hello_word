package event;
/**
 * 我自定义了一个类，并希望这个类在某些条件下能触发属于它自己的一个事件，而这个事件能被任何对这个事件感兴趣的其他类捕获并处理。
       这在C#里非常容易做到，就是构造一个Event，在条件达到的时候就用Event名来引发它，再在其他类里注册监听器，但在JAVA里却不知如何实现？
 * @author humaohua
 *
 */
public class Test {
	public static void main(String[] args)
	   {
	       MyObject mo=new MyObject();

	       //注册该事件
	       mo.addMyListener(new MyListener());
	       
	       //触发该事件
	       mo.test();
	    }
}
