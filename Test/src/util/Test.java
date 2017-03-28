package util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.Header;

public class Test {
public static void main(String[] args) {
	  HttpClient httpClient = new HttpClient();
	  HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
	  // 设置连接超时时间(单位毫秒)
	  managerParams.setConnectionTimeout(30000);
	  // 设置读数据超时时间(单位毫秒)
	  managerParams.setSoTimeout(120000);
	  String url = "localhost";
	  PostMethod postMethod = new PostMethod(url);
	  Header  header = new Header();
	  header.setName("Cookie");
	  header.setValue("cookie");
	  header.setName("Refer");
	  header.setValue("refer");
	  postMethod.setRequestHeader(header);
	  String strResponse = null;
	  int statusCode=-1;
	  try 
	  {
	    statusCode= httpClient.executeMethod(postMethod);
	    if (statusCode != HttpStatus.SC_OK) 
	    {
	      throw new IllegalStateException("Method failed: "+ postMethod.getStatusLine());
	    }
	    strResponse = postMethod.getResponseBodyAsString();
	  } catch (Exception ex) 
	  {
	    throw new IllegalStateException(ex.toString());
	  } 
	    finally 
	  {
	     //释放连接
	     postMethod.releaseConnection();
	  }
	    System.out.println(strResponse);
	}
} 