package util;

import java.util.StringTokenizer;

public class STDemo {
	
	static String in = "title=java:The complete Reference;"+
	"author=Schildt;"+
			"publisher=OsBORNE/mcGraw-Hill;"+
	"copyright=2001;";
	public static void main(String[] args) {
		StringTokenizer st = new StringTokenizer(in,"=;");
		while(st.hasMoreElements()){
			String key = st.nextToken();
			String val = st.nextToken();
			System.out.println(key +"\t" + val);
		}
	}
/**
 * 输出如下：
title	java:The complete Reference
author	Schildt
publisher	OsBORNE/mcGraw-Hill
copyright	2001
 */
}
