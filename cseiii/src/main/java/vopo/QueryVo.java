package vopo;

import java.util.Map;

public class QueryVo {
	
	private String name;
	private String id;
	private String beginDate;
	private String endDate;
	private Map<String, Double> openPrice;
	private Map<String, Double> closePrice;
	private Map<String, Double> highPrice;
	private Map<String, Double> lowPrice;
	private Map<String, Integer> volume;
	public QueryVo(String name, String id, String beginDate, String endDate, Map<String, Double> openPrice,
			Map<String, Double> closePrice, Map<String, Double> highPrice, Map<String, Double> lowPrice,Map<String, Integer> volume) {
		this.name = name;
		this.id = id;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.volume = volume;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Map<String, Double> getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(Map<String, Double> highPrice) {
		this.highPrice = highPrice;
	}
	public Map<String, Double> getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(Map<String, Double> lowPrice) {
		this.lowPrice = lowPrice;
	}
	public Map<String, Integer> getVolume() {
		return volume;
	}
	public void setVolume(Map<String, Integer> lowPrice) {
		this.volume = volume;
	}
	
}
