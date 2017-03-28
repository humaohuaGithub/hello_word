package designMode.CashFactoryPattern;

abstract class CashSuper {
	/**
	 * 现金收取超类的收取方法，收取现金，参数为原价，返回为当前价
	 * @param money
	 * @return
	 */
	public abstract double acceptCash(double money);
}
