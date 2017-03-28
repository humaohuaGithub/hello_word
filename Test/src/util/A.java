/**
 * 
 */
package util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author humaohua
 *
 */
public class A {
 private Integer a ;
 private Integer b;
public Integer getA() {
	return a;
}
public void setA(Integer a) {
	this.a = a;
}
public Integer getB() {
	return b;
}
public void setB(Integer b) {
	this.b = b;
}
 

private void test(int t){
	A a = new A();
	if(t==a.getA() ){
		System.out.println("t > a");
	}
}

public static Set<Integer> ITEM_BRAND_SHOP_IDS = new HashSet<Integer>();
static{
	ITEM_BRAND_SHOP_IDS.add(5775);//生产上正式店铺
	ITEM_BRAND_SHOP_IDS.add(1614);//生产上测试店铺
	
 }

public static Date addDate(Date date, int field, int amount){
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(field, amount);
	return cal.getTime();
}


	public static void main(String arg[]){
		/*JSONObject json = new JSONObject();
		json.put("type", 1);
		IntegerUtil offlineOrder = JSONObject.toJavaObject(json, IntegerUtil.class);
		
		IntegerUtil iu = new IntegerUtil();
		iu.setId(offlineOrder.getId());
		System.out.println("aaaaaaaaaa");
		
		String testRelpace ="dddq我iadsafadi,没俄俄,";
		
		testRelpace = testRelpace.replaceAll(",", "-");
		System.out.println("白斑病="+testRelpace);
		
		int price =900000;
		int deductibleAmount=9;
		price = (price - price * deductibleAmount/100)/100*100 < 100 ? 100 : (price - price * deductibleAmount/100)/100*100;
		System.out.println("price="+price);  
		String userSource="fulishe_97";
		int index = userSource.indexOf("_");
		double vipxs = Double.valueOf(userSource.substring(index+1));
		System.out.println("price="+price+" vipxs="+vipxs);  
		
		Integer priceInteger = new Integer(90);
		price = priceInteger;
		System.out.println("price="+price);*/
		int rFinalAmount = 0;
		if(rFinalAmount < 0 || (rFinalAmount > 0 && rFinalAmount <= 10)){ //不能小于0.1元
			String msg = "下次支付款不能小于0.1元";
			System.out.println("msg="+msg);
		}
		String response="&is_success=F&_input_charset=UTF-8&error_code=MEMBER_ID_NOT_EXIST&error_message=用户MemberId不存在&error_memo=会员个人钱包未注册&memo=会员个人钱包未注册&cost_time=19";
		String errorCode = response.contains("&memo=")?response.substring(response.indexOf("&memo=")+6,response.lastIndexOf("&")):response;
		System.out.println("msg:"+errorCode);
	}
	
	public static String filterString(String str){
		
		return StringFilter(str);
	}
	
	
	// 过滤特殊字符     
	   public   static   String StringFilter(String   str)   throws   PatternSyntaxException   {        
	               // 只允许字母和数字          
	                //String   regEx  =  "[^a-zA-Z0-9]";                        
	                  // 清除掉所有特殊字符     
	         String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";     
	         Pattern   p   =   Pattern.compile(regEx);        
	         Matcher   m   =   p.matcher(str);        
	         return   m.replaceAll("").trim();        
	         }        
	       
	   public    void   testStringFilter()   throws   PatternSyntaxException   {        
	         String   str   =   "*adCVs*34_a _09_b5*[/435^*&城池()^$$&*).{}+.|.)%%*(*.中国}34{45[]12.fd'*&999下面是中文的字符￥……{}【】。，；’“‘”？";        
	         System.out.println(str);        
	         System.out.println(StringFilter(str));        
	         } 
}
