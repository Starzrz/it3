package vopo;

/**
 * @author 朱润之
 * 写入文件的单次策略相关信息
 */
public class StrategyData {
	private int formDay;  //形成期
	private int holdDay; //持有期
	private double outRise;  //超额收益率
	private double winRate;  //策略胜率
	private StrategyType type;  //策略种类
	
	
	public StrategyData(int formDay, int holdDay, double outRise, double winRate, StrategyType type) {
		this.formDay = formDay;
		this.holdDay = holdDay;
		this.outRise = outRise;
		this.winRate = winRate;
		this.type = type;
	}
	public int getFormDay() {
		return formDay;
	}
	public void setFormDay(int formDay) {
		this.formDay = formDay;
	}
	public int getHoldDay() {
		return holdDay;
	}
	public void setHoldDay(int holdDay) {
		this.holdDay = holdDay;
	}
	public double getOutRise() {
		return outRise;
	}
	public void setOutRise(double outRise) {
		this.outRise = outRise;
	}
	public double getWinRate() {
		return winRate;
	}
	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}
	public StrategyType getType() {
		return type;
	}
	public void setType(StrategyType type) {
		this.type = type;
	}
	
	
}
