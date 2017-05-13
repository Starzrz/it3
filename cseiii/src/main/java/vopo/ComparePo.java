package vopo;

import java.util.ArrayList;
import java.util.Map;



public class ComparePo {
	private String stockName;
	private String stockNum;
	private String beginDate;
	private String endDate;

	private ArrayList<SharesData> datas;
	
	public ComparePo(String stockName, String stockNum, String beginDate, String endDate, ArrayList<SharesData> datas) {
		this.stockName = stockName;
		this.stockNum = stockNum;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.datas = datas;
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

	public ArrayList<SharesData> getDatas() {
		return datas;
	}

	public void setDatas(ArrayList<SharesData> datas) {
		this.datas = datas;
	}
	

}
