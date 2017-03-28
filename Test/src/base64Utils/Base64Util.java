package base64Utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.util.regex.*;
/*
*   @copyright (c) Qeeka 2011 
 * @author YinChunhui    Oct 19, 2011 
 */

public class Base64Util {

    public String encode(String input){
       try {
           BASE64Encoder base64encoder = new BASE64Encoder();
           byte[] cbytes = input.getBytes("UTF-8");
           if(cbytes == null){
              return null;
           }
           String secret = base64encoder.encodeBuffer(cbytes);
           return secret;
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
    }
    
    public String decode(String secret){
       try {
           if(secret == null){
              return null;
           }
           BASE64Decoder base64decoder = new BASE64Decoder();
           byte[] bytes = base64decoder.decodeBuffer(secret);
           String output = new String(bytes,"UTF-8");
           return output;
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
    }
    
    public static void main(String[] args) {
       /*Base64Util o = new Base64Util();
       String s = o.encode("sw");
       System.out.println(s);
       System.out.println(o.decode(s));
       
       Calendar c = Calendar.getInstance();
       c.add(Calendar.MINUTE, 30);//自动取消时间定义为＝当前时间加30分钟后自动取消
       Date autoCancelTime = c.getTime();
       System.out.println(autoCancelTime.toString());
       */
    	
    }
}
