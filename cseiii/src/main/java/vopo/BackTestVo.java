package vopo;

import java.util.Map;

public class BackTestVo {
	private Double alphaRate; //阿尔法比率
	private Double betaRate; //贝塔比率
	private Double shapeRate; //夏普比率
	private Double maxReturn; //最大回撤
	private Double yearRate;  //年化收益
	private Double baseYearRate; //基准年华收益
	private Map<String, Double> riseMap; //策略的累计收益率
	private Map<String, Double> baseRiseMap; //基准的累计收益率
	private Map<String, Double> holdRiseMap; //各持有期的收益，key对应持有期的开始日期
	private StrategyData strategyData;
	public BackTestVo() {
		// TODO Auto-generated constructor stub
	}
	
	public Map<String, Double> getHoldRiseMap() {
		return holdRiseMap;
	}

	public void setHoldRiseMap(Map<String, Double> holdRiseMap) {
		this.holdRiseMap = holdRiseMap;
	}

	

	public StrategyData getStrategyData() {
		return strategyData;
	}

	public void setStrategyData(StrategyData strategyData) {
		this.strategyData = strategyData;
	}

	public BackTestVo(Double alphaRate, Double betaRate, Double shapeRate, Double maxReturn, Double yearRate,
			Double baseYearRate, Map<String, Double> riseMap, Map<String, Double> baseRiseMap,
			Map<String, Double> holdRiseMap, StrategyData strategyData) {
		this.alphaRate = alphaRate;
		this.betaRate = betaRate;
		this.shapeRate = shapeRate;
		this.maxReturn = maxReturn;
		this.yearRate = yearRate;
		this.baseYearRate = baseYearRate;
		this.riseMap = riseMap;
		this.baseRiseMap = baseRiseMap;
		this.holdRiseMap = holdRiseMap;
		this.strategyData = strategyData;
	}

	public Double getAlphaRate() {
		return alphaRate;
	}
	public void setAlphaRate(Double alphaRate) {
		this.alphaRate = alphaRate;
	}
	public Double getBetaRate() {
		return betaRate;
	}
	public void setBetaRate(Double betaRate) {
		this.betaRate = betaRate;
	}
	public Double getShapeRate() {
		return shapeRate;
	}
	public void setShapeRate(Double shapeRate) {
		this.shapeRate = shapeRate;
	}
	public Double getMaxReturn() {
		return maxReturn;
	}
	public void setMaxReturn(Double maxReturn) {
		this.maxReturn = maxReturn;
	}
	public Double getYearRate() {
		return yearRate;
	}
	public void setYearRate(Double yearRate) {
		this.yearRate = yearRate;
	}
	public Double getBaseYearRate() {
		return baseYearRate;
	}
	public void setBaseYearRate(Double baseYearRate) {
		this.baseYearRate = baseYearRate;
	}
	public Map<String, Double> getRiseMap() {
		return riseMap;
	}
	public void setRiseMap(Map<String, Double> riseMap) {
		this.riseMap = riseMap;
	}
	public Map<String, Double> getBaseRiseMap() {
		return baseRiseMap;
	}
	public void setBaseRiseMap(Map<String, Double> baseRiseMap) {
		this.baseRiseMap = baseRiseMap;
	}
	

}
