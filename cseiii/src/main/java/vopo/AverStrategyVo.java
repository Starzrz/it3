package vopo;

import java.util.Map;

import BL.momentumBL.OutRiseData;

/**
 * @author 朱应山
 *
 */


public class AverStrategyVo {
	private double alphaRate; //阿尔法比率
	private double betaRate; //贝塔比率
	private double shapeRate; //夏普比率
	private double maxReturn; //最大回撤
	private double yearRate;  //年化收益
	private double baseYearRate; //基准年华收益
	private Map<String, Double > strategyMap;//策略
	private Map<String , Double> baseMap;//基准
	private Map<String, Double> holdTimeMap;
	private double strategyRate;//策略收益率
	private  int alldays;
	public AverStrategyVo (Map<String, Double> sMap,Map<String, Double> bMap,Map<String, Double> holdMap,
			double aRate,double bRate,double sRate,double mReturn,double yRate,double bYearRate,double strategy){
		this.strategyMap=sMap;
		this.baseMap=bMap;
		this.holdTimeMap=holdMap;
		this.alphaRate=aRate;
		this.betaRate=bRate;
		this.shapeRate=sRate;
		this.maxReturn=mReturn;
		this.yearRate=yRate;
		this.baseYearRate=bYearRate;
		this.strategyRate=strategy;
	}
	public void setAllDays(int days){
		this.alldays=days;
	}
	public int getAllDays(){
		return alldays;
	}
	public double getStrategyRate(){
		return strategyRate;
	}
/**
 *	阿尔法比率，贝塔比率，夏普比率，最大回撤，年化收益，基准年化收益
 */	
	public void setRate(double aRate,double bRate,double sRate,double mReturn,double yRate,double bYearRate){
		alphaRate=aRate;
		betaRate=bRate;
		shapeRate=sRate;
		maxReturn=mReturn;
		yearRate=yRate;
		baseYearRate=bYearRate;
	}
/**
 *策略map，和基准map
 */
	public void setMap(Map<String, Double> sMap,Map<String, Double> bMap){
		strategyMap=sMap;
		baseMap=bMap;
	}
	
	public Double getAlphaRate() {
		return alphaRate;
	}
	public Double getBetaRate() {
		return betaRate;
	}
	public Double getShapeRate() {
		return shapeRate;
	}
	public Double getMaxReturn() {
		return maxReturn;
	}
	public Double getYearRate() {
		return yearRate;
	}
	public Double getBaseYearRate() {
		return baseYearRate;
	}
	public Map<String, Double> getRiseMap() {
		return strategyMap;
	}
	public Map<String, Double> getBaseMap() {
		return baseMap;
	}
	public Map<String, Double> getHoldMap(){
		return holdTimeMap;
	}
}
