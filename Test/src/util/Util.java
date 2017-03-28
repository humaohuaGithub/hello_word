package util;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;



public final class Util {

	private static AtomicInteger atomicInteger = new AtomicInteger(0);
	
	public static final SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static final SimpleDateFormat sFormat_Int = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 格式化当前时间
	 * @param type
	 */
	public static String formatDateTime() {
		return sFormat.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 生成事务ID
	 * @return tracIDStr
	 */
	public static String getTracId() {
		synchronized (atomicInteger) {
			int tracID = atomicInteger.get();
			if (atomicInteger.get() >= 9999) {
				atomicInteger.set(0);
			} else {
				atomicInteger.addAndGet(1);
			}
			String tracIDStr = String.valueOf(tracID);
			while (tracIDStr.length() < 4) {
				tracIDStr = "0" + tracIDStr;
			}
			return sFormat_Int.format(Calendar.getInstance().getTime()) + tracIDStr;
		}
	}
	

   /**
    * 计算两点间的距离
    * @param srcX 起始位置X
    * @param srcY 起始位置Y
    * @param destX 目的位置X
    * @param destY 目的位置Y
    * @return
    */
   public static double calculateAttackDistance(float srcX, float srcY, float destX, float destY){
	   	float xdiff = destX - srcX;
	   	float ydiff = destY - srcY;
	   	double d = Math.pow((xdiff*xdiff + ydiff*ydiff), 0.5f);
		return d;
   }
   
   
   
   /**
    * 产生一个随机数
    * @param max 随机数区间最大值
    * @return
    */
   public static int random(int max){
	   Random random = new Random();
       int result = Math.abs(random.nextInt()) % max;
       return result;
   }
   
   
   
   public static void  main(String[] args){
    	  int[] uts = Util.random(1, 1, 1);
    	  System.out.println(Arrays.toString(uts));
   }
   
   /**
    * 根据给出区间范围返回一个随机数    ---- 包含最大值
    * @param min 大于等于最小数
    * @param max 小于等于最大数
    * @return
    */
   public static int random(int min, int max) throws IllegalArgumentException{
	   if(min >= max){
		   throw new IllegalArgumentException();
	   }
	   Random random = new Random();
       int result = Math.abs(random.nextInt()) % (max-min+1);
       return result+min; 
   }
   
   /**
    * 随机取出一组不相同的数字
    * @param min 取值数字下限 包含下限
    * @param max 取值数字上限 包含上限
    * @param len 取值数量
    * @return
    * @throws IllegalArgumentException
    */
   public static int[] random(int min, int max, int len) throws IllegalArgumentException{
	   if(min > max || max-min+1 < len){
		   throw new IllegalArgumentException();
	   }
	   
	   /**
	    * 处理极限情况 added by wangwenjun on 2011-10-27
	    */
	   if(min == max && len == 1) {
		   return new int[]{min};
	   }
	   
	   int[] rst = new int[len];
	   for(int i =0; i<rst.length; i++){
		   rst[i] = -1;
	   }
	   Random random = new Random();
	   int l = 0;
	   while(l<len){
		   int result = Math.abs(random.nextInt()) % (max-min+1);
		   boolean exist = false;
		   for(int r : rst){
			   if(r == result){
				   exist = true;
			   }
		   }
		   if(!exist){
			   rst[l++] = result;
		   }
	   }
       return rst; 
   }
   
   
   
   /**
    * 对几率数组选择
    * @param srcArray 几率数组 0.29f
    * @param generalRate 生成几率百分比值 0.33f
    * @return 没有产生结果则返回-1
    */
   public static int randomSelect(float[] srcArray, float generalRate){
	   float[] rangeFloats = new float[srcArray.length];		//取值区间
	   float rateTotalValue = 0;
	   for(int i=0; i<srcArray.length; i++){
		   rangeFloats[i] = srcArray[i] * 100;
		   rateTotalValue += rangeFloats[i];
		   if(i > 0){
			   rangeFloats[i] += rangeFloats[i-1];
		   }
	   }
	   
	   int selectTotal =(int) (rateTotalValue / generalRate);
	   int randomValue = random(selectTotal);
	   if(randomValue >= rateTotalValue){
		   return -1;  
	   }else{
		   for(int i = 0; i < rangeFloats.length; i++){
			 if(randomValue <= rangeFloats[i]){
				 return i;
			 }
		   }
	   }
	   return -1;
   }
   
   public static int randomSelect(float[] srcArray){
	   if(srcArray.length == 1 && srcArray[0] == 1){
		   return 0;
	   }
	   float[] rangeFloats = new float[srcArray.length];		//取值区间
	   float rateTotalValue = 0;
	   for(int i=0; i<srcArray.length; i++){
		   rangeFloats[i] = srcArray[i] * 100;
		   rateTotalValue += rangeFloats[i];
		   if(i > 0){
			   rangeFloats[i] += rangeFloats[i-1];
		   }
	   }
	   
	   int selectTotal =(int) (rateTotalValue);
	   if(selectTotal < 100){
		   selectTotal = 100;
	   }
	   int randomValue = random(selectTotal);
	   if(randomValue >= rateTotalValue){
		   return -1;  
	   }else{
		   for(int i = 0; i < rangeFloats.length; i++){
			 if(randomValue <= rangeFloats[i]){
				 return i;
			 }
		   }
	   }
	   return -1;
   }
   
   /**
    * Added by WangWenjun on 2011-10-13
    * 
    * 在指定的数组中根据数字大小随机选择N个数字
    * @param srcArray - 随机数里列表，例如{100,100,120,110,130,...}
    * @param length - 返回的随机数的数量
    * @return - 返回生成的随机数对应的index
    */
   public static int[] randomSelect(float[] srcArray, int length){
	   if(srcArray.length == 1 && srcArray[0] == 1){
		   return null;
	   }
	   
	   int[] indexes = new int[length];
	   int index = 0;
	   
	   float[] rangeFloats = new float[srcArray.length];		//取值区间
	   float rateTotalValue = 0;
	   for(int i=0; i<srcArray.length; i++){
		   rangeFloats[i] = srcArray[i] * 100;
		   rateTotalValue += rangeFloats[i];
		   if(i > 0){
			   rangeFloats[i] += rangeFloats[i-1];
		   }
	   }
	   
	   int selectTotal =(int) (rateTotalValue);
	   
	   boolean exist = false;
	   
	   while( index< length ) {
		   int randomValue = random(selectTotal);
		   exist = false;
		   
		   for(int i = 0; i < rangeFloats.length; i++){
				if(randomValue <= rangeFloats[i]){
					//检查该索引值是否已经存在，如果存在，则继续进行随机运算
					for(int j=0;j<length;j++) {
						if(indexes[j] == i ) {
							exist = true;
							break;
						}
					}
					
					if( exist ) {
						break;
					}
					
					indexes[index] = i;
					index++;
					break;
				}
		   }
	   }
	   
	   
	   return indexes;
   }
   
   
	/**
	 * 两数精确相除
	 * @param int a
	 * @param  int b
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
	 * 将长整数转化为字符窜
	 * @param time
	 * @param format
	 * @return   
	 * @since  1.0.0
	 */
	public static String getLong2String(long time, String format) {
		if(null==format || format.length()<1){
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(time));
		}else{
			return new SimpleDateFormat(format).format(new java.util.Date(time));
		}
	}
	/**
	 * 将字符窜转化为长整数
	 * @param time
	 * @param format
	 * @return   
	 * @since  1.0.0
	 */
	public static long getString2Long(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate.getTime();
	}
	/**
	 * 取得当天日期 
	 * @return 格式   yyyy-MM-dd
	 * @since  1.0.0
	 */
	public static String getToday() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date());
	}

	/**
	 * 取得昨天日期
	 * @return 格式   yyyy-MM-dd
	 * @since  1.0.0
	 */
	public static String getYesterToday() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date(System.currentTimeMillis()-86400000L));
	}
	/**
	 * 判断时间是否是在当天
	 * @param time
	 * @return   
	 * @since  1.0.0
	 */
	public static boolean isToday(long time){
	   boolean ret = false;
	   String today = getToday();
	   String strtoday1 = today+" 00:00:00";
	   String strtoday2 = today+" 23:59:59";
	   long today1 = getString2Long(strtoday1);
	   long today2 = getString2Long(strtoday2);
	   if(time>=today1 && time<=today2){
		   ret = true;
	   }
	   return ret;
	}
	
	/**
	 * 判断时间是否是是昨天
	 * @param time
	 * @return   
	 * @since  1.0.0
	 */
	public static boolean isYesterday(long time){
	   boolean ret = false;
	   String today = getYesterToday();
	   String strtoday1 = today+" 00:00:00";
	   String strtoday2 = today+" 23:59:59";
	   long today1 = getString2Long(strtoday1);
	   long today2 = getString2Long(strtoday2);
	   if(time>=today1 && time<=today2){
		   ret = true;
	   }
	   return ret;
	}
	
}