package vopo;

import java.util.Map;

public class StrategyVo {
	
	StrategyType type;  //策略类型
	int oneday;        //固定的一个参数，例如该参数为持有期，则之后的map键值对应为形成期
	Map<Integer, Double> outRiseMap;  //超额收益率，key对应的是形成期或持有期
	Map<Integer, Double> winRateMap;  //策略胜率
	boolean isHold;    //判断哪个变量固定的参数，true为持有期固定，false为形成期固定
	public StrategyVo(StrategyType type, int oneday, Map<Integer, Double> outRiseMap, Map<Integer, Double> winRateMap,
			boolean isHold) {
		this.type = type;
		this.oneday = oneday;
		this.outRiseMap = outRiseMap;
		this.winRateMap = winRateMap;
		this.isHold = isHold;
	}
	public StrategyType getType() {
		return type;
	}
	public void setType(StrategyType type) {
		this.type = type;
	}
	public int getOneday() {
		return oneday;
	}
	public void setOneday(int oneday) {
		this.oneday = oneday;
	}
	public Map<Integer, Double> getOutRiseMap() {
		return outRiseMap;
	}
	public void setOutRiseMap(Map<Integer, Double> outRiseMap) {
		this.outRiseMap = outRiseMap;
	}
	public Map<Integer, Double> getWinRateMap() {
		return winRateMap;
	}
	public void setWinRateMap(Map<Integer, Double> winRateMap) {
		this.winRateMap = winRateMap;
	}
	public boolean isHold() {
		return isHold;
	}
	public void setHold(boolean isHold) {
		this.isHold = isHold;
	}
	
}
