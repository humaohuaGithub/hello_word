/**
 * 
 */
package designMode.CashFactoryPattern;

/**
 * @author humaohua
 *
 */
public class main {

	/**
	 * 
	 */
	public main() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double money=3000;
		CashContext cs = new CashContext(2);//根据不同方式生成相应的对象
	    double returnMoney =  cs.getResult(money);
	    System.out.println(returnMoney);
	}

}
