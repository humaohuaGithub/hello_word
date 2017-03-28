/**
 * 
 */
package util;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 */
public class Statuts {
	
	public static final int getMember(int value){
		String str = Integer.toBinaryString(value);
		return 0;
	}
	
	/**
	 * 两数精确相除
	 * 
	 * @param int a
	 * @param int b
	 * @return float a/b
	 */
	public static float divide(int a, int b) {
		if (b == 0) {
			return 0f;
		}
		return new BigDecimal(a).divide(new BigDecimal(b), 4,
				BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/**
	 * 两数精确相除
	 * 
	 * @param float a
	 * @param float b
	 * @return float a/b
	 */
	public static float divide(float a, float b) {
		if (b == 0) {
			return 0f;
		}
		return new BigDecimal(a).divide(new BigDecimal(b), 4,
				BigDecimal.ROUND_HALF_UP).floatValue();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int value = 10;
		String str = Integer.toBinaryString(value);
		System.out.println("str==="+str);
		
		int i=10;
		i = i >>> 1;
		System.out.println("i="+i);
		String result="{\"strReturn\":\"总数量：0\",\"outParameter\":[\"5664\"],\"Result\":0,\"statusCode\":400}";
		String statutsCode = result.substring(result.indexOf("statusCode")+"statusCode".length()+2, result.length()-1);
		System.out.println("statutsCode="+statutsCode);
		if("200".equals(statutsCode)){
			System.out.println("200");
		}else{
			System.out.println("400");
		}
		int tables = 16;
		
		
	}
	
	

}
