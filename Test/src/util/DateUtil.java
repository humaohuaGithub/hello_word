
package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



/**
 * 日期工具类
 * @author sunwuhua
 * 
 * 2012-02-06   sunwuhua    增加date2DateStr(Date date, String format)方法,日期串更灵活      
 * 2012-02-07   sunwuhua    增加 public String currentDateBefore(int n)方法
 *                              public String currentDateAfter(int n)方法
 *                          修改getCurrentTimeStr()方法为只获取当前时间，  getCurrentDateTimeStr()方法获取日期加时间   
 */
public final class DateUtil {

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";   // 默认时间格式
	private static final String FORMAT_DATE = "yyyy-MM-dd";
	private static final String BILL99_FORMAT = "yyyyMMddHHmmss";
	private static final String PAYMENT_CODE_DATE = "yyMMddHHmmss";
	private static final String REFUND_CODE_DATE = "yyyyMMddHH";
	private static final String REFUND_QUERY_DATE = "yyyyMMdd";
	
    /**
     * private constructor
     */
    DateUtil() {// prevent instantiation
    }
    
    
    public static String date2DateStr(Date date) {
    	 return  date2DateStr(date, DEFAULT_DATE_FORMAT);
    }
    
    public static String date2DateStr(Date date, String format){
    	 SimpleDateFormat sdf = new SimpleDateFormat(format);
	      String dateString = sdf.format(date);
	      return dateString;
    }
     
    
    public static String getCurrentDateTimeStr() {
	      return  date2DateStr(new Date());
	}
	
	public static String getCurrentDateStr() {
		   return  getCurrentDateTimeStr().substring(0,10);
	}
	
	public static String getCurrentTimeStr(){
		return  getCurrentDateTimeStr().substring(10,19);
	}
	
	/*
    private static String  dateStrHelper(String dateFormat) {
    	  Date currentTime = new Date();		  
	      SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	      String dateString = sdf.format(currentTime);
	      return dateString;
    }*/
    
	 // date string like '2010-01-22 00:00:00'
 	public static Timestamp dateStr2Timestamp(String dateString){
 		// 1. set date format 
 		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
 		// 2. parse date string
 		try {
 			java.util.Date date= simpleDateFormat.parse(dateString);
 		}catch(ParseException ignored){}
 		// 3. get timestamp from date
 		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
 		return timestamp;
 	}
 	
 	public static Timestamp date2Timestamp(Date date) {
 		String dateStr = date2DateStr(date);
 		return dateStr2Timestamp(dateStr);
 	}
	
	
    public static Date getCurrentDate() {
       	// convert to specific style 
		
    	  Date currentTime = new Date();
	      SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
	      String dateString = sdf.format(currentTime);
	      java.util.Date currentTime_2 = sdf.parse(dateString,new ParsePosition(0));
	     	       	    
	      return currentTime_2;
	}
       
    public static Date timestamp2Date(Timestamp timestamp){
    	long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
        return new java.util.Date(milliseconds);

    }
    
    public static String timestamp2DateStr(Timestamp timestamp){
    	Date d = timestamp2Date(timestamp);
    	return date2DateStr(d);
    }
    
    
    
    /** fn-hd
     * rem: ���ڱȽ� s1��s2������� ��s2-s1 =?
     * par: s1,s2
     * ret: 
     */
    public static int compDate(String s1, String s2)
    {
               
        int day = 0;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        Date xxx1 = new Date();
        Date xxx2 = new Date();
        try
        {
            xxx1 = sf.parse(s1);
            xxx2 = sf.parse(s2);
            day = (int) ((xxx2.getTime() - xxx1.getTime()) / 3600 / 24 / 1000);
        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
        return day;

    } 
    
    /**
     * 计算当前时间之前n天的日期
     * @param n     天数
     * @return 
     */
    public static String currentDateBefore(int n){
    	 Calendar now = Calendar.getInstance();
    	 now.add(Calendar.DATE, -n);
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         Date d = now.getTime();
         return sdf.format(d);
    }
        
       
    /**
     * 计算当前时间之后n天的日期
     * @param n  天数
     * @return
     */
    public static String currentDateAfter(int n) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, n);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = now.getTime();
        return sdf.format(d);
    }
  
    /**
	 * 
	 * @param datestr 日期字符串
	 * @param day  相对天数，为正数表示之后，为负数表示之前
	 * @return 指定日期字符串n天之前或者之后的日期
	 */
	public static String getBeforeAfterDate(String datestr, int day) {
		SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		Date olddate = null;
		try {
			df.setLenient(false);
			olddate = new Date(df.parse(datestr).getTime());
		} catch (ParseException e) {
			throw new RuntimeException("日期转换错误");
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(olddate);
		cal.add(Calendar.DAY_OF_MONTH, day);
		Date date = new Date(cal.getTimeInMillis());
		
		return df.format(date);
	}
    
	/**
	 * 计算n个月前的时间
	 * @param n 
	 * @return
	 */
    public static String getDateBeforeMonth(int n) {
    	Date date = new Date(); 
    	GregorianCalendar gc = new GregorianCalendar(); 
    	gc.setTime(date); 
    	gc.add(GregorianCalendar.MONTH, -n); 
    	Date dateBefore = gc.getTime();  
    	SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sdf.format(dateBefore);
    }
	
	
	public static void main(String[] agrs) {
		/*
		System.out.println("当前日期时间：" + DateUtil.getCurrentDateTimeStr());
		System.out.println("当前日期：" + DateUtil.getCurrentDateStr());
		System.out.println("当前时间：" + DateUtil.getCurrentTimeStr());
		System.out.println("14天后的日期：" + DateUtil.currentDateAfter(14));
		System.out.println("2天前的日期：" + DateUtil.currentDateBefore(2));
		System.out.println("��ǰʱ�䣺" + DateUtil.date2Timestamp(new Date()));
		System.out.println("��ǰʱ�䣺" + DateUtil.dateStr2Timestamp("2012-01-12 13:06:00"));
		System.out.println("��ǰʱ�䣺" + DateUtil.timestamp2Date( DateUtil.dateStr2Timestamp("2012-01-12 13:06:00")  ));
		System.out.println("��ǰʱ�䣺" + DateUtil.timestamp2DateStr( DateUtil.dateStr2Timestamp("2012-01-12 13:06:00")  ));
		*/
//		System.out.println("三个月以前的时间:"+getDateBeforeMonth(3));
		System.out.println("="+compDate("2014-02-14","2014-02-14 23:59:59"));
		System.out.println("="+compDate("2014-02-15","2014-02-17 23:59:59"));
		System.out.println("="+compDate("2014-02-15","2014-02-19"));
		
		System.out.println("es time:"+getESTimeFromTimeStamp("1478401728000"));
		   
	}

	public static String getDatetimeStr(long datetime){
    	return datetime == 0 ? "" : format(new Date(datetime),DEFAULT_DATE_FORMAT);
    }
    
    public static String getDatetimeStr(Date datetime){
    	return datetime == null ? null : format(datetime, DEFAULT_DATE_FORMAT);
    }
    
    public static String getDateStr(long datetime){
    	return format(new Date(datetime),FORMAT_DATE);
    }
    
    public static String get99BillDateStr(Date date){
    	return format(date, BILL99_FORMAT);
    }
    
    public static String getPaymentCodeDateStr(Date date){
    	return format(date, PAYMENT_CODE_DATE);
    }
    
    public static String getRefundCodeDateStr(Date date){
    	return format(date, REFUND_CODE_DATE);
    }
    
    public static String getRefundQueryDateStr(Date date){
    	return format(date, REFUND_QUERY_DATE);
    }
    
    private static String format(Date aTs_Datetime, String as_Pattern){
        if (aTs_Datetime == null || as_Pattern == null){
      	  return null;
        }
        SimpleDateFormat dateFromat = new SimpleDateFormat(as_Pattern);
        return dateFromat.format(aTs_Datetime);
    }
    
    public static Date parseStringToDateTime(String date_str){
    	return parseStringToDate(date_str, DEFAULT_DATE_FORMAT);
    }
    
    public static Date parseStringTo99BillDateTime(String strDate){
    	return parseStringToDate(strDate, BILL99_FORMAT);
    }
    
    private static Date parseStringToDate(String strDate, String format){
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	Date date = null;
    	try {
    		if(strDate != null && strDate.length() > 0){
    			date = sdf.parse(strDate);
    		}
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	return date;
    }
    
	public static Date addForNow(int field, int amount){
		return addDate(new Date(), field, amount);
	}
	
	public static Date addDate(Date date, int field, int amount){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}
	/**
	 * 格式化为GMT格式的时间
	 * @param timeStamp(时间戳)
	 * @return
	 */
	public static String getESTimeFromTimeStamp(String timeStamp){
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat osf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			Timestamp ts = new Timestamp(Long.valueOf(timeStamp));
			String date = osf.format(sf.parse(ts.toString()));
			return date;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
