package designMode.simpleFactoryPattern;

/**
 * @author humaohua
 * 现金收费工厂
 */
public class CashFactory {

	public static CashSuper createCashAccept(int type){
		CashSuper cashSuper = null;
		switch(type){
		case 1://正常收费
			cashSuper = new CashNormal(); 
			break;
		case 2://打折
			cashSuper = new CashRebate("0.8"); 
			break;
		case 3://返利
			cashSuper = new CashReturn("300","100"); 
			break;
		}
		return cashSuper;
	}
}
