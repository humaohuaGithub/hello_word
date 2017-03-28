package sign;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import threads.SignVerify;

import com.alibaba.fastjson.JSONObject;

public class SignUtil {
	
	 private static Logger       log               = LoggerFactory.getLogger(SignUtil.class);
	 
	 public static String publicKey ="test key";
    
	 /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            //            try {
            //                value = URLEncoder.encode(value,charset);
            //            } catch (UnsupportedEncodingException e) {
            //                e.printStackTrace();
            //            }
            result.put(key, value);
        }

        return result;
    }
    
	/**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @param encode 是否需要urlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        String charset = params.get("_input_charset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
    
    /**
     * 生成RSA签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByRSA(Map<String, String> sPara, String privateKey,
                                           String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = RSA.sign(prestr, privateKey, inputCharset);
        return mysign;
    }
    /**
     * 把JSON数据转换成Map
     * @param json
     * @return
     */
    public static Map<String, String> json2Map(JSONObject json){
    	Map<String, String> result = new HashMap<String,String>();
    	if(json!=null){
    		List<Entry<String, Object>> list = new LinkedList<Entry<String, Object>>(json.entrySet());
    		for (Entry<String, Object> entry : list) {
				if (entry.getValue() != null) {
					result.put(entry.getKey(), String.valueOf(entry.getValue()));
				}
			}
    	}
    	return result;
    }
    /**
     * 生成RSA签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByRSA(JSONObject json, String privateKey,
    										String inputCharset) throws Exception {
    	Map<String, String> sPara = json2Map(json);
    	String prestr = createLinkString((sPara), false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = RSA.sign(prestr, privateKey, inputCharset);
        if(log.isInfoEnabled()){
        	log.info("签名原字符窜source="+prestr + "\n\r sign:"+mysign);
        }
        return mysign;
    }
    
    /**
     * 验证签名-RSA
     * @param json
     * @param sign
     * @param publicKey
     * @param charset
     * @return
     * @throws Exception
     */
   public static boolean verifyByRSA(JSONObject json,String publicKey,String charset) throws Exception{
	   String sign = json.getString("sign");
	   sign = URLDecoder.decode(sign, "UTF-8");
	   Map<String, String> sPara = json2Map(json);
	   String prestr = createLinkString(paraFilter(sPara), false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
	   if(log.isInfoEnabled()){
		   log.info(" 验证签名原字符窜 check content:" + prestr + "\n\r sign:"+ sign);
	   }
       return RSA.verify(prestr, sign, publicKey, charset);
   }
   
   /**
	 * 验证签名
	 * @param jsonObject
	 * @return
	 * @throws QeekaException
	 */
	public static boolean checkVerfiy(JSONObject jsonObject) throws Exception{
		try{
			if(jsonObject.containsKey("sign_type") && jsonObject.getString("sign_type").equalsIgnoreCase("RSA")){
				if(!SignUtil.verifyByRSA(jsonObject,publicKey, "UTF-8")){
					throw new Exception("checkVerfiy by rsa error!");
				}
			} else if(jsonObject.containsKey("sign_method") && jsonObject.getString("sign_method").equalsIgnoreCase("md5")){
				if(!SignVerify.verify(jsonObject)){
					throw new Exception("checkVerfiy by md5 error!");
				}
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
    /**
     * 生成MD5签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByMD5(JSONObject json,String key,
                                           String inputCharset) throws Exception {
    	Map<String, String> sPara = json2Map(json);
    	String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = MD5.sign(prestr, key, inputCharset);
        return mysign;
    }
    
    /**
     * 生成MD5签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByMD5(Map<String, String> sPara, String key,
                                           String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = MD5.sign(prestr, key, inputCharset);
        return mysign;
    }
    
    
    
    public static void main(String arg[]){
    	JSONObject json = new JSONObject();
    	String charset = "UTF-8";
		json.put("service", "prepay_pay");
		json.put("sign_type", "RSA");
		json.put("partner_id", "200006273608");
		json.put("version","1.0");
		json.put("_input_charset", charset);
		Map<String, String> map = SignUtil.json2Map(json);
		for(Entry<String, String> entry:map.entrySet()){
			System.out.println("key:"+entry.getKey() +" value:"+entry.getValue());
		}
    }

}
