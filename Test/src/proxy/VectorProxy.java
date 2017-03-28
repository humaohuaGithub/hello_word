/**
 * 
 */
package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Vector;

/**
 * @author Administrator
 *
 */
public class VectorProxy implements InvocationHandler
{
 //代理对象
 private Object proxyobj;
 //构造函数
 public VectorProxy(Object obj)
 {
  this.proxyobj=obj;
 }
 //静态工厂方法
 public static Object factory(Object obj)
 {
  return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),new VectorProxy(obj));
 }
 //代理对象的处理函数(实现InvocationHandler接口函数)
 /**
  *proxy代理对象，这里是proxyobj
  *method代理对象执行被代理对象的函数，相当于C++中的函数指针，但Method是个封装函数指针的类，我个人这么认为的^_^
  *args是执行函数method的参数数组
  */
 public Object invoke(Object proxy,Method method,Object[] args) throws Throwable
 {
  System.out.println("调用"+method.toString()+"之前"); 
  if(args!=null)
  {
   System.out.println("调用参数:");
   for(int i=0;i<args.length;i++)
   {
    System.out.println(args[i]+"/");
   }
  }
  Object o = method.invoke(proxyobj,args);
  System.out.println("调用"+method.toString()+"之后");
  System.out.println("___________________________________________________");
  return o;
  
 }
 public static void main(String[] args)
 {
  List v = null;
  //List是Vector实现的一个接口，所以可以用这个接口指向实际的代理对象，详细问题查Jdk去，
  //我一般很负责任的，只是有时候偷懒
  v = (List)factory(new Vector());
  v.add("Hello");
  v.add("ByeBye");
  v.add(1,"Welcome");
  v.remove(0);
  v.remove(1);
 }
}
