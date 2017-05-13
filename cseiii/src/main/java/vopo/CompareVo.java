package vopo;

import java.util.Map;

/**
 * @author 朱润之
 * 比较功能的vo
 */
public class CompareVo {
	private String stockName;
	private String stockNum;
	private String beginDate;
	private String endDate;
	private Map<String, Double> minPrice;
	private Map<String, Double> maxPrice;
	private Map<String, Double> openPrice;  //开盘价
	private Map<String, Double> closePrice; //收盘价
	private double increase;   //涨幅
	     //跌幅
	private Map<String, Double>logReturn;  //对数收益率
	private double logReturn_Var; //对数收益率方差
	public CompareVo(){
		
	}
	
 	

	



	







	public CompareVo(String stockName, String stockNum, String beginDate, String endDate, Map<String, Double> minPrice,
			Map<String, Double> maxPrice, Map<String, Double> openPrice, Map<String, Double> closePrice,
			double increase, Map<String, Double> logReturn, double logReturn_Var) {
		this.stockName = stockName;
		this.stockNum = stockNum;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.increase = increase;
		this.logReturn = logReturn;
		this.logReturn_Var = logReturn_Var;
	}















	public String getStockName() {
		return stockName;
	}















	public void setStockName(String stockName) {
		this.stockName = stockName;
	}















	public String getStockNum() {
		return stockNum;
	}















	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}















	public String getBeginDate() {
		return beginDate;
	}















	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}















	public String getEndDate() {
		return endDate;
	}















	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}















	public Map<String, Double> getMinPrice() {
		return minPrice;
	}















	public void setMinPrice(Map<String, Double> minPrice) {
		this.minPrice = minPrice;
	}















	public Map<String, Double> getMaxPrice() {
		return maxPrice;
	}















	public void setMaxPrice(Map<String, Double> maxPrice) {
		this.maxPrice = maxPrice;
	}















	public Map<String, Double> getOpenPrice() {
		return openPrice;
	}















	public void setOpenPrice(Map<String, Double> openPrice) {
		this.openPrice = openPrice;
	}















	public Map<String, Double> getClosePrice() {
		return closePrice;
	}















	public void setClosePrice(Map<String, Double> closePrice) {
		this.closePrice = closePrice;
	}















	public double getIncrease() {
		return increase;
	}















	public void setIncrease(double increase) {
		this.increase = increase;
	}















	public Map<String, Double> getLogReturn() {
		return logReturn;
	}















	public void setLogReturn(Map<String, Double> logReturn) {
		this.logReturn = logReturn;
	}















	public double getLogReturn_Var() {
		return logReturn_Var;
	}















	public void setLogReturn_Var(double logReturn_Var) {
		this.logReturn_Var = logReturn_Var;
	}















	

	
	
	
}
