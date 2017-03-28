/**
 * 
 */
package event;

/**
 * 再自定义一个监听器
 * @author humaohua
 *
 */
public class MyListener implements java.util.EventListener{
	//这里是当事件发生后的响应过程
    public void EventActivated(MyEvent me)
    {
        System.out.println("事件已经被触发");
    }
}
