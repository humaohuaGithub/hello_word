package designMode.CashFactoryPattern;
/**
 * 返利收费子类
 * @author humaohua
 *
 */
public class CashReturn extends CashSuper{

	private double moneyCondition=0.0d;
	private double moneyReturn = 0.0d;
	/**
	 * 返利收费，初始化必须输入返利条件和返利值，比如：满300返100，则moneyCondition为300，moneyReturn为100
	 * @param moneyCondition
	 * @param moneyReturn
	 */
	public CashReturn(String moneyCondition,String moneyReturn) {
		// TODO Auto-generated constructor stub
		this.moneyCondition = Double.parseDouble(moneyCondition);
		this.moneyReturn = Double.parseDouble(moneyReturn);
	}

	@Override
	public double acceptCash(double money) {
		// TODO Auto-generated method stub
		double result=money;
		if(money >= moneyCondition)
			result = money - Math.floor(money/moneyCondition) * moneyReturn;//若大于返利条件，则需要减去返利值
		return result;
	}

}
