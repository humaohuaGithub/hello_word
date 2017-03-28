package util;

import java.util.HashMap;
import java.util.Iterator;

public class HashMapTest {
	 
	 public static void main(String[] args){
	  HashMap<String,Object> hm=new HashMap<String,Object>();
	  
	  HashMap<People,Object> hmMap = new HashMap<People,Object>();
	  
	  People p1=new People();
	  People p2=new People();
	  People p3=new People();
	  People p4=new People();

	  hm.put("People3", p1);
	  hm.put("People1", p2);
	  hm.put("People4", p3);
	  hm.put("People2", p4);  
	  
	  hmMap.put(p3, "p3");
	  hmMap.put(p1, "p1");
	  hmMap.put(p4, "P4");
	  hmMap.put(p2, "p2");
	  

	  Iterator<String> it=hm.keySet().iterator();
	  while(it.hasNext()){
	   System.out.println(it.next());
	  }  
	  System.out.println("----------------");
	  Iterator<People> it2=hmMap.keySet().iterator();
	  
	  while(it2.hasNext()){
	   System.out.println(it2.next());
	  } 
	  
	  
	 }
	}
	class People {
	 private String name;
	 private int age;
	 public int getAge() {
	  return age;
	 }
	 public void setAge(int age) {
	  this.age = age;
	 }
	 public String getName() {
	  return name;
	 }
	 public void setName(String name) {
	  this.name = name;
	 }
}
