package util;

public class CharOpter {
	/**
	 * 判断value的第index位是否为1
	 * @param value 
	 * @param index
	 * @return   
	 * @since  1.0.0
	 */
	public boolean isExistTrue(int value,int index){
		return Integer.toBinaryString(value >>> index).endsWith("1");
	}
	
	/**
	 * 通过第N位ID号取得存储的数字
	 * @param index
	 * @return   
	 * @since  1.0.0
	 */
	public int getValueByIndexId(int index){
		return 1 << (index);
	}
	
	public static void main(String arg[]){
		//00000
		CharOpter c = new CharOpter();
		int one = c.getValueByIndexId(0);//2^0=1
		int two = c.getValueByIndexId(1);//2^1=2
		int three = c.getValueByIndexId(2);//2^2=4
		int four = c.getValueByIndexId(3);//2^3=8
		int five = c.getValueByIndexId(4);//2^3=16
		System.out.println("one:"+one+" two:"+two+" three:"+three+" four:"+four+" five:"+five);
		boolean b1 = c.isExistTrue(3, 0);
		boolean b2 = c.isExistTrue(3, 1);
		boolean b3 = c.isExistTrue(4, 2);
		boolean b4 = c.isExistTrue(8, 3);
		boolean b5 = c.isExistTrue(16, 4);
		System.out.println("one:"+b1+" two:"+b2+" three:"+b3+" four:"+b4+" five:"+b5);
	}
}
