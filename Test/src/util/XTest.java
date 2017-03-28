package util;

public class XTest {
public static int cnt=0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		x(x(8));
	}
public static int x(int n){
	    System.out.println("cnt="+(cnt++));
	    if (n<=3)
	    {
	        return 1;
	    }
	    else
	    {
	        return x(n-2)+x(n-4)+1;
	    }
	}

}
