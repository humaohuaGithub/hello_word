package designMode.CashFactoryPattern;

public class CashContext {
	CashSuper cashSuper = null;
	public CashContext(int type){
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
	}
	//取得计算结果
	public double getResult(double money){
		return cashSuper.acceptCash(money);
	}
}
