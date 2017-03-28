/**
 * 
 */
package event;

/**
 * 先自定义一个事件
 * @author humaohua
 *
 */
public class MyEvent extends java.util.EventObject {

	public MyEvent(Object source) {
		super(source);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
