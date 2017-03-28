package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtil1 {
	/**
	 * 逗号
	 */
	public static final String COMMA=",";
	/**
	 * 去掉最后的字符
	 * @param src
	 * @param trimStr
	 */
	public static String trimSuffix(String src,String suffix){
		if(src!=null && src.length()> 0 ){
			while(src.indexOf(suffix+suffix)!=-1){
				src = src.replaceAll(suffix+suffix, "").trim();
			}
			if(src.endsWith(suffix)){
				src =src.substring(0,src.length()-1);
			}
		}
	    return src;
	}
	
	public static void main(String arg[]){
//		String a="dad SFDD$$%%%,,,,fadfad,addfad,ad,,,,fadf,ada,,,,df,,,,,,";
//		System.out.println("start src="+a);
//		String b =StringUtils.trimSuffix(a, COMMA);
//		System.out.println("end src="+b);
//		System.out.println("NOW="+System.currentTimeMillis());
//		DateUtil du = new DateUtil();
		String src = "2014-01-02 00:00:00";
//		System.out.println("date="+du.dateStr2Timestamp(src);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		// 2. parse date string
 		try {
 			java.util.Date date= simpleDateFormat.parse(src);
 		    String dateString = simpleDateFormat.format(date);
 		    System.out.println("date==="+dateString);
 		}catch(ParseException ignored){}
 		// 3. get timestamp from date
// 		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
// 		return timestamp;
 		
 		    long mTime = System.currentTimeMillis();
 		    int offset = Calendar.getInstance().getTimeZone().getRawOffset();
 		    Calendar c = Calendar.getInstance();
 		    c.setTime(new Date(mTime - offset));
 		    System.out.println(c.getTime());
 		    Date d = new Date();
 		   System.out.println();
	}
	
}
