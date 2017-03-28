package designMode.CashFactoryPattern;
/**
 * 打折收费类
 * @author humaohua
 *
 */
public class CashRebate extends CashSuper{

    private double moneyRebate = 1d;
    /**
     * 构造方法，初始化时必需要输入折扣率，如八折,就是0.8
     * @param moneyRebate
     */
    public CashRebate(String moneyRebate){
    	this.moneyRebate = Double.parseDouble(moneyRebate);
    }
    
	@Override
	public double acceptCash(double money) {
		// TODO Auto-generated method stub
		return money * moneyRebate;
	}

}
