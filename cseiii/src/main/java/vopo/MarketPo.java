package vopo;

import java.util.ArrayList;

public class MarketPo {
	private String date;
	private ArrayList<SharesData> stockList;
	public MarketPo(String date, ArrayList<SharesData> stockList) {
		this.date = date;
		this.stockList = stockList;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ArrayList<SharesData> getStockList() {
		return stockList;
	}
	public void setStockList(ArrayList<SharesData> stockList) {
		this.stockList = stockList;
	}
	

}
