/**
 * 
 */
package util;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author humaohua
 *
 */
public class B {

	public static void aamain(String arg[]){
		AtomicInteger atomicInt = new AtomicInteger(1);
		String ab="abcdef";
		int code = ab.hashCode();
		System.out.println("CODE:"+code);//CODE:-1424385949 
		
		Integer a = 1000, b = 1000; 
		System.out.println(a == b);//1
		Integer c = 100, d = 100; 
		System.out.println(c == d);//2
	}
	
	public int inc(AtomicInteger a){
		return a.incrementAndGet();
	}
	
	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
		 
	      Class cache = Integer.class.getDeclaredClasses()[0]; //1
	      Field myCache = cache.getDeclaredField("cache"); //2
	      myCache.setAccessible(true);//3
	      cache.getd
	 
	      Integer[] newCache = (Integer[]) myCache.get(cache); //4
	      newCache[132] = newCache[132]; //5
	 
	      int a = 2;
	      int b = a + a;
	      System.out.printf("%d + %d = %d", a, a, b); //
	}
	
	
}
