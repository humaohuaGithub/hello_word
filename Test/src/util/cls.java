package util;

import java.text.DecimalFormat;

public class cls {
	public static DecimalFormat df = new DecimalFormat("0.00");
	
	public String getFloatString(int a){
		String result =  df.format((float)(a/100));
		return result;
	}
}
