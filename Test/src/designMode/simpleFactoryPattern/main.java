/**
 * 
 */
package designMode.simpleFactoryPattern;

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
	    CashSuper cs = CashFactory.createCashAccept(2);//根据不同方式生成相应的对象
	    double returnMoney = cs.acceptCash(money);
	    System.out.println(returnMoney);
	}

}
