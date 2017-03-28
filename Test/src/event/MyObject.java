/**
 * 
 */
package event;

import java.util.Vector;

/**
 * 以下这个类为触发事件的事件源
 * @author humaohua
 *
 */
public class MyObject {
	  private Vector vectorListeners=new Vector();
	    
	    public synchronized void addMyListener(MyListener ml)
	    {
	        vectorListeners.addElement(ml);
	    }
	    
	    public synchronized void removeMyListener(MyListener ml)
	    {
	        vectorListeners.removeElement(ml);
	    }
	    
	    protected void activateMyEvent()
	    {
	        Vector tempVector=null;
	        
	        MyEvent e=new MyEvent(this);
	        
	        synchronized(this)
	        {
	            tempVector=(Vector)vectorListeners.clone();
	            
	            for(int i=0;i<tempVector.size();i++)
	            {
	                MyListener ml=(MyListener)tempVector.elementAt(i);
	                ml.EventActivated(e);
	            }
	        }
	        
	    }
	    
	 //定义一个公用方法用于触发事件
	    public void test()
	    {
	        activateMyEvent();
	    }   
}
