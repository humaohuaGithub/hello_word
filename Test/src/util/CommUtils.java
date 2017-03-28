package util;

public class CommUtils {
	/**
	 * 判断一个整数的二进制位中某一位是否为1
	 * @param value 十进制数
	 * @param bit 第几位
	 * @return
	 */
	public static boolean isExist(int value,int bit){
		return Integer.toBinaryString(value >>> bit).endsWith("1");
	}
	/**
	 * 取得2的N次方
	 * @param bit
	 * @return
	 */
	public static int getIntegerValue(int bit){
		return 1 << bit;
	}
	
	public static void main(String arg[]){
		int a=11;//1011
		System.out.println("a="+Integer.toBinaryString(a));
		
		System.out.println("a0="+(Integer.toBinaryString(a).endsWith("1")));//1011 true
		System.out.println("a1="+(Integer.toBinaryString(a >> 1).endsWith("1")));//101 true
		System.out.println("a2="+(Integer.toBinaryString(a >> 2).endsWith("1")));//10 false
		System.out.println("a3="+(Integer.toBinaryString(a >> 3).endsWith("1")));//1  true
		
		System.out.println("b0="+(1 << 0));// 1
		System.out.println("b1="+(1 << 1));// 2
		System.out.println("b2="+(1 << 2));// 4
		System.out.println("b3="+(1 << 3));// 8
		System.out.println("b4="+(1 << 4));// 16
		
		int v0 = CommUtils.getIntegerValue(0);
		
		int v2 = CommUtils.getIntegerValue(2);
		
		int values = v0+v2;
		
		boolean has = CommUtils.isExist(values, 2);
		if(has){
			System.out.println(" values exists!");
		}else{
			System.out.println(" values is not exists!");
		}
		for(int i=0;i<100;i++){
		int table_Index = i % (1<<4);
		System.out.println("i="+i+ " table_Index="+table_Index);
		}
	}
}
