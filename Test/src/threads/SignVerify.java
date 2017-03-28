package threads;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import com.qjmall.util.logging.ESLogger;
//import com.qjmall.util.logging.Loggers;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("static-access")
//@Component
public class SignVerify {
	
	//private static ESLogger log = Loggers.getLogger(SignVerify.class);

	private static String secret = "4747b7ed16de29d4ff097a67593e9c8e";
	
	private static String jiapaySecret="pay.jia.com/2013/li";
	/*
	@Value("#{newOrderConfig['sign.secret']}")
	private void setSecret(String secret) {
		this.secret = secret;
	}
	
	@Value("#{newOrderConfig['jiapay.sign.secret']}")
	private void setJiapaySecret(String jiapaySecret) {
		this.jiapaySecret = jiapaySecret;
	}*/

	public static boolean verify(Map<String, List<String>> map) {
		boolean ret = false;
		String sign = "";
		if (map != null) {
			List<Entry<String, List<String>>> list = new LinkedList<Entry<String, List<String>>>(map.entrySet());
			Collections.sort(list, new Comparator<Entry<String, List<String>>>() {
				public int compare(Entry<String, List<String>> o1, Entry<String, List<String>> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});
			StringBuilder sb = new StringBuilder(1024);
			for (Entry<String, List<String>> entry : list) {
				if (entry.getKey().equals("sign")) {
					sign = entry.getValue().get(0);
					continue;
				}
				if (entry.getValue() != null && entry.getValue().size() > 0) {
					sb = appendParam(sb, entry.getKey(), entry.getValue().get(0));
				}
			}

			try {
				sb = appendParam(sb, "secret", secret);
				String signMsg = encodePassword(sb.toString(), "md5").toUpperCase();
				ret = signMsg.equals(sign);
			} catch (Exception e) {
				System.out.println("签名校验失败");
			}
		}
		return ret;
	}
	
	public static JSONObject signJson(JSONObject json){
		if (json != null) {
			json.put("sign_method", "md5");
			List<Entry<String, Object>> list = new LinkedList<Entry<String, Object>>(json.entrySet());
			Collections.sort(list, new Comparator<Entry<String, Object>>() {
				public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});
			StringBuilder sb = new StringBuilder(1024);
			for (Entry<String, Object> entry : list) {
				if (entry.getKey().equals("signature")) {
					continue;
				}
				if (entry.getValue() != null) {
					sb = appendValue(sb, String.valueOf(entry.getValue()));
				}
			}

			try {
				sb = appendValue(sb, jiapaySecret);
				String sign = encodePassword(sb.toString(), "md5").substring(0, 16);
				json.put("signature", sign);
			} catch (Exception e) {
				System.out.println("签名失败");
			}
		}
		return json;
	}
	
	public static boolean verify(JSONObject json) {
		boolean ret = false;
		String sign = "";
		if (json != null) {
			List<Entry<String, Object>> list = new LinkedList<Entry<String, Object>>(json.entrySet());
			Collections.sort(list, new Comparator<Entry<String, Object>>() {
				public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});
			StringBuilder sb = new StringBuilder(1024);
			for (Entry<String, Object> entry : list) {
				if (entry.getKey().equals("signature")) {
					sign = (String) entry.getValue();
					continue;
				}
				if (entry.getValue() != null) {
					sb = appendValue(sb, String.valueOf(entry.getValue()));
				}
			}

			try {
				sb = appendValue(sb, jiapaySecret);
				String signMsg = encodePassword(sb.toString(), "md5").substring(0, 16);
				ret = signMsg.equals(sign);
			} catch (Exception e) {
				System.out.println("签名校验失败");
			}
		}
		return ret;
	}

	public static StringBuilder appendParam(StringBuilder returnStr, String paramId, String paramValue) {
		if (returnStr.length() > 0) {
			if (paramValue != null && !paramValue.equals("")) {
				returnStr.append("&").append(paramId).append("=").append(paramValue);
			}
		} else {
			if (paramValue != null && !paramValue.equals("")) {
				returnStr.append(paramId).append("=").append(paramValue);
			}
		}
		return returnStr;
	}
	
	public static StringBuilder appendValue(StringBuilder returnStr, String paramValue) {
		if (paramValue != null && !paramValue.equals("")) {
			returnStr.append(paramValue);
		}
		return returnStr;
	}

	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword;
		try {
			unencodedPassword = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return password;
		}

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}
	
	public static void main(String[] agrs){
		SignVerify sign = new SignVerify();
		//sign.setJiapaySecret("760881697c81313b77c7784e0bb232cc");
		//String s = "{\"api_name\":\"create_refund\",\"callback_url\":\"http:\\/\\/pay.jia.com\\/api\\/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",";
		//String c = "\"orig_outer_trade_no\":\"3099771-2\",\"outer_trade_no\":\"F026311\",\"refund_amount\":\"3.00\",\"refund_ensure_amount\":\"0.01\",\"request_no\":\"0011dba69625fa4f34\"";
		//String e = ",\"sign_method\":\"md5\"}";
		//退款发起
		String str = "{\"api_name\":\"create_refund\",\"callback_url\":\"http:\\/\\/pay.jia.com\\/api\\/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"2710236-1\",\"outer_trade_no\":\"F0033511\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"00be95796f81c1e6c3\",\"sign_method\":\"md5\"}";
		
		String str1 = "{\"api_name\":\"create_refund\",\"callback_url\":\"http:\\/\\/pay.jia.com\\/api\\/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":02978350,\"outer_trade_no\":\"021722\",\"refund_amount\":\"9.20\",\"refund_ensure_amount\":\"0.01\",\"request_no\":\"0028e0549a3d7b7f9\",\"sign_method\":\"md5\"}";
		
		String str2 = "{\"api_name\":\"create_refund\",\"callback_url\":\"http:\\/\\/pay.jia.com\\/api\\/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3079375-1\",\"outer_trade_no\":\"F0029774\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"003b35a1a398da7aa4\",\"sign_method\":\"md5\"}";
		
		String str3 = "{\"api_name\":\"create_refund\",\"callback_url\":\"http:\\/\\/pay.jia.com\\/api\\/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"12.56\",\"orig_outer_trade_no\":03168397,\"outer_trade_no\":\"F033018\",\"refund_amount\":\"22.30\",\"refund_ensure_amount\":\"0.07\",\"request_no\":\"0008647154faad50f92\",\"sign_method\":\"md5\"}";
		
		String str4 = "{\"api_name\":\"create_refund\",\"callback_url\":\"http://pay.jia.com/api/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"1.00\",\"orig_outer_trade_no\":03099929,\"outer_trade_no\":\"F026292\",\"refund_amount\":\"1.30\",\"refund_ensure_amount\":\"0.07\",\"request_no\":\"0bb4d2305c26b788a\",\"sign_method\":\"md5\"}";
		
		String str5 = "{\"api_name\":\"create_refund\",\"callback_url\":\"http://pay.jia.com/api/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"2417002-1\",\"outer_trade_no\":\"011413\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"0b773d8830ffd9fa7\",\"sign_method\":\"md5\"}";
		
		String str6 = "{\"api_name\":\"create_refund\",\"callback_url\":\"http://pay.jia.com/api/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"2711674-1\",\"outer_trade_no\":\"017787\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"04afd74d161410a29\",\"sign_method\":\"md5\"}";
		
		str6 = "{\"api_name\":\"create_refund\",\"callback_url\":\"http://pay.jia.com/api/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3407505-1\",\"outer_trade_no\":\"F53561\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"3b6d41f1aca0f4f200\"}";
		str6 = "{\"api_name\":\"create_refund\",\"callback_url\":\"http://pay.jia.com/api/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3352872-1\",\"outer_trade_no\":\"F53380\",\"refund_amount\":\"1300.00\",\"refund_ensure_amount\":\"265.20\",\"request_no\":\"4dffc8a2c3730b8500\",\"sign_method\":\"md5\",\"signature\":\"98f9ae7732f54870\"}";
		
		String str7="{\"api_name\":\"create_refund\",\"callback_url\":\"http://pay.jia.com/api/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3293616-1\",\"outer_trade_no\":\"F53559\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"2137ee850c660a0500\",\"sign_method\":\"md5\"}";
		String str8="{\"api_name\":\"create_refund\",\"callback_url\":\"http:\\/\\/pay.jia.com\\/api\\/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3396261-1\",\"outer_trade_no\":\"F53390\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"d21dd28bf6cce64200\",\"sign_method\":\"md5\"}";
		String str9="{\"api_name\":\"create_refund\",\"callback_url\":\"http:\\/\\/pay.jia.com\\/api\\/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3348251-1\",\"outer_trade_no\":\"F53388\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"1189603a318c2d970\",\"sign_method\":\"md5\"}";
		String str10="{\"api_name\":\"create_refund\",\"callback_url\":\"http:\\/\\/pay.jia.com\\/api\\/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3123227-1\",\"outer_trade_no\":\"F53379\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"0d4699b0b4cb66b30\",\"sign_method\":\"md5\"}";
		String str11="{\"api_name\":\"create_refund\",\"callback_url\":\"http:\\/\\/pay.jia.com\\/api\\/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3405216-1\",\"outer_trade_no\":\"F53375\",\"refund_amount\":\"1000.00\",\"refund_ensure_amount\":\"204.00\",\"request_no\":\"57927832faef65240\",\"sign_method\":\"md5\"}";
		String str12="{\"api_name\":\"create_refund\",\"callback_url\":\"http:\\/\\/pay.jia.com\\/api\\/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3254140-1\",\"outer_trade_no\":\"F53321\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"c9163f8de58216cd0\",\"sign_method\":\"md5\"}";
		String str13="{\"api_name\":\"create_refund\",\"callback_url\":\"http:\\/\\/pay.jia.com\\/api\\/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3342880-1\",\"outer_trade_no\":\"F53320\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"0c0c1cfbf5ac954c0\",\"sign_method\":\"md5\"}";
		String s1="{\"api_name\":\"create_refund\",\"callback_url\":\"http://pay.jia.com/api/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3251919-1\",\"outer_trade_no\":\"F51025\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"292b62d436b07b270\",\"sign_method\":\"md5\"}";
		s1="{\"api_name\":\"create_refund\",\"callback_url\":\"http://pay.jia.com/api/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3251860-1\",\"outer_trade_no\":\"F53316\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"c442cf4a2439ce68\",\"sign_method\":\"md5\"}";
		s1 = "{\"api_name\":\"create_refund\",\"callback_url\":\"http://pay.jia.com/api/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3347860-1\",\"outer_trade_no\":\"F56991\",\"refund_amount\":\"50.00\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"6e26281885f8ce27\",\"sign_method\":\"md5\"}";
		s1="{\"api_name\":\"create_refund\",\"callback_url\":\"http://pay.jia.com/api/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm\",\"deposit_amount\":\"0.00\",\"gold_coin\":\"0.00\",\"orig_outer_trade_no\":\"3329017-1\",\"outer_trade_no\":\"F57060\",\"refund_amount\":\"66.50\",\"refund_ensure_amount\":\"0.00\",\"request_no\":\"16d64dd7c47abc5f00\",\"sign_method\":\"md5\"}";
		//{"api_name":"create_refund","callback_url":"http://pay.jia.com/api/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm","deposit_amount":"0.00","gold_coin":"0.00","orig_outer_trade_no":"2710236-1","outer_trade_no":"F33511","refund_amount":"50.00","refund_ensure_amount":"0.00","request_no":"be95796f81c1e6c3"}
//		{"api_name":"create_refund","callback_url":"http://pay.jia.com/api/index.php?c=refundcallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2FrefundNotify.htm","deposit_amount":"0.00","gold_coin":"4.56","orig_outer_trade_no":"3168397","outer_trade_no":"F33018","refund_amount":"22.30","refund_ensure_amount":"0.07","request_no":"8647154faad50f92"}
		
		String cancan1 = "{\"api_name\":\"cancel_trade\",\"outer_trade_no\":\"3229430-11\",\"request_no\":\"0000195f96497173ef70e\",\"sign_method\":\"md5\"}";
		//由于钱包没有收单－手动收单
		String create_pay = "{\"api_name\":\"create_ensure_trade\",\"buyer_id\":101622701,\"buyer_id_type\":\"UID\",\"go_cashier\":\"N\",\"request_no\":\"05085ae4c5f17274e\",\"return_url\":\"http://mall.jia.com/pay/pay/pay_success_page\",\"sign_method\":\"md5\",\"signature\":\"d3fbd4dd06634093\",\"trade_list\":\"3229430-14~12月20 21日齐家网第289届大型建材品牌盛典~11100.00~1~1000.00~204.00~163~COMPANY_ID~2010211450556~~~0.00~0.00~~20150106210800~http://pay.jia.com/api/index.php?c=tradecallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2ForderNotify.htm~紫薇木门\"}";
		create_pay = "{\"api_name\":\"create_ensure_trade\",\"buyer_id\":101638362,\"buyer_id_type\":\"UID\",\"go_cashier\":\"N\",\"request_no\":\"df24710e2103c37d00\",\"return_url\":\"http://mall.jia.com/pay/pay/pay_success_page\",\"trade_list\":\"3286003-2~1月24日至25日大型建材家居团购会~50.00~1~2212.00~442.40~2525~COMPANY_ID~1000240648353~~~0.00~50.00~3286003-1~20150125134428~http://pay.jia.com/api/index.php?c=tradecallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2ForderNotify.htm~三木合雅\"}";
		create_pay = "{\"api_name\":\"create_ensure_trade\",\"buyer_id\":101470710,\"buyer_id_type\":\"UID\",\"go_cashier\":\"N\",\"request_no\":\"e66f66ff57544d6f00\",\"return_url\":\"http://mall.jia.com/pay/pay/pay_success_page\",\"trade_list\":\"3249132-2~1月1 2日齐家网跨年建材嘉年华~50.00~1~2778.00~566.72~293~COMPANY_ID~2010210095954~~~0.00~50.00~3249132-1~20150101142215~http://pay.jia.com/api/index.php?c=tradecallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2ForderNotify.htm~汉顶集成吊顶\"}";
		create_pay = "{\"api_name\":\"create_ensure_trade\",\"buyer_id\":101612500,\"buyer_id_type\":\"UID\",\"go_cashier\":\"N\",\"request_no\":\"13e9c4bf5850a460000\",\"return_url\":\"http://mall.jia.com/pay/pay/pay_success_page\",\"trade_list\":\"3207401-2~12月6 7日齐家网第288届大型建材品牌盛典~50.00~1~4319.00~881.08~2897~COMPANY_ID~2010211646159~~~0.00~50.00~3207401-1~20141207133945~http://pay.jia.com/api/index.php?c=tradecallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2ForderNotify.htm~奥普1+N浴顶\"}";
		create_pay = "{\"api_name\":\"create_ensure_trade\",\"buyer_id\":101597037,\"buyer_id_type\":\"UID\",\"go_cashier\":\"N\",\"request_no\":\"d46ee4e7591ab36a00\",\"return_url\":\"http://mall.jia.com/pay/pay/pay_success_page\",\"trade_list\":\"3344241-2~展厅日常3月9日至20日~50.00~1~5020.00~1024.08~261~COMPANY_ID~2010211264155~~~0.00~50.00~3344241-1~20150315142006~http://pay.jia.com/api/index.php?c=tradecallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2ForderNotify.htm~布鲁斯特墙纸\"}";
		create_pay = "{\"api_name\":\"create_ensure_trade\",\"buyer_id\":101572666,\"buyer_id_type\":\"UID\",\"go_cashier\":\"N\",\"request_no\":\"6ee067d3d22bf98f00\",\"return_url\":\"http://mall.jia.com/pay/pay/pay_success_page\",\"trade_list\":\"3114150-2~展厅日常10月27日至10月31日~50.00~1~27000.00~5508.00~240~COMPANY_ID~2010211607550~~~0.00~50.00~3114150-1~20141029144559~http://pay.jia.com/api/index.php?c=tradecallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2ForderNotify.htm~宝视无框阳台窗\"}";
		create_pay="{\"api_name\":\"create_ensure_trade\",\"buyer_id\":101610986,\"buyer_id_type\":\"UID\",\"go_cashier\":\"N\",\"request_no\":\"5ac9e914ca328b5a00\",\"return_url\":\"http://mall.jia.com/pay/pay/pay_success_page\",\"trade_list\":\"3294410-2~1月31 2月1日齐家网大型建材品牌盛会~50.00~1~500.00~102.00~6154~COMPANY_ID~2000218396450~~~0.00~50.00~3294410-1~20150201092018~http://pay.jia.com/api/index.php?c=tradecallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2ForderNotify.htm~绿叶\"}";
		create_pay="{\"api_name\":\"create_ensure_trade\",\"buyer_id\":101817057,\"buyer_id_type\":\"UID\",\"go_cashier\":\"N\",\"request_no\":\"90f4da7633e901b300\",\"return_url\":\"http://mall.jia.com/pay/pay/pay_success_page\",\"trade_list\":\"3418809-2~4月展厅订单1~8000.00~1~6000.00~1224.00~583~COMPANY_ID~1005124913400~~~0.00~0.00~~20150401125832~http://pay.jia.com/api/index.php?c=tradecallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2ForderNotify.htm~依诺\"}";
		create_pay = "{\"api_name\":\"create_ensure_trade\",\"buyer_id\":101510426,\"buyer_id_type\":\"UID\",\"go_cashier\":\"N\",\"request_no\":\"970a081bd4650bf00\",\"return_url\":\"http://mall.jia.com/pay/pay/pay_success_page\",\"trade_list\":\"3418861~【多乐士油漆】多乐士金装A997+A914超低净味墙面漆套餐2面1底漆|物流点自提 颜色备注~578.00~1~701.00~2.89~5353~COMPANY_ID~01142786652335265469~~~0.00~0.00~~20150401133523~http://pay.jia.com/api/index.php?c=tradecallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2ForderNotify.htm~齐家自营\"}";
		create_pay="{\"api_name\":\"create_ensure_trade\",\"buyer_id\":101615015,\"buyer_id_type\":\"UID\",\"go_cashier\":\"N\",\"request_no\":\"142b899d9391a3640\",\"return_url\":\"http://mall.jia.com/pay/pay/pay_success_page\",\"trade_list\":\"3317667-2~3月7 8日齐家网开年建材团购嘉年华盛典~50.00~1~2362.00~2362.00~5384~COMPANY_ID~2010212625157~~~0.00~50.00~3317667-1~20150307160203~http://pay.jia.com/api/index.php?c=tradecallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2ForderNotify.htm~都芳\"}";
		create_pay = "{\"api_name\":\"create_ensure_trade\",\"buyer_id\":101446324,\"buyer_id_type\":\"UID\",\"go_cashier\":\"N\",\"request_no\":\"7f0e44d2b0d022b20\",\"return_url\":\"http://mall.jia.com/pay/pay/pay_success_page\",\"trade_list\":\"2899089-2~8月9 10日齐家网第280届大型建材品牌盛典~1300.00~1~51.00~51.00~3540~COMPANY_ID~2000215494850~~~0.00~50.00~2899089-1~20140809144751~http://pay.jia.com/api/index.php?c=tradecallback&returnUrl=http%3A%2F%2Fdingdan.api.tg.local%2Fcallback%2ForderNotify.htm~墨林水槽\"}";
		create_pay="";
		//str = "{\"api_name\":\"query_balance\",\"identity_no\":2881,\"identity_type\":\"COMPANY_ID\"}";
		//支付成功回调算签名
		String callBackNotiy = "{\"gmt_create\":\"20141114154943\",\"gmt_payment\":\"20141114154942\",\"inner_trade_no\":\"101141595088765335911\",\"notify_type\":\"trade_status_sync\",\"outer_trade_no\":\"3151389\",\"trade_amount\":\"74.00\",\"trade_status\":\"PAY_FINISHED\",\"version\":\"1.0\"}";
		String []  strArray = new String[]{s1};
//		String []  strArray = new String[]{cancan1,cancan2,cancan3,cancan4,cancan5,cancan6,cancan7,cancan8,cancan9,cancan10,cancan11,cancan12,cancan13,cancan14,cancan15,cancan16,cancan17};
		for(int i=0; i<strArray.length;i++){
		    System.out.println("i="+i);
			String strs = strArray[i];
			JSONObject json = JSONObject.parseObject(strs);
			sign.signJson(json);
			System.out.println(json);
			System.out.println(sign.verify(json));
		}
	}

}
