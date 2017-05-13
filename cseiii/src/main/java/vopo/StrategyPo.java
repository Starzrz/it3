package vopo;

import java.util.ArrayList;
import java.util.Map;



/**
 * @author 朱应山
 *	均值回归的策略PO
 */
public class StrategyPo {
	private String beginDate;
	private String endDate;//开始日期和结束日期
	private ArrayList<QueryPo> sharesList;//获得的股票列表
	private int averLineDay;//均线类型
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
	public ArrayList<QueryPo> getSharesList() {
		return sharesList;
	}
	public void setSharesList(ArrayList<QueryPo> sharesList) {
		this.sharesList = sharesList;
	}
	public int getAverLineDay() {
		return averLineDay;
	}
	public void setAverLineDay(int averLineDay) {
		this.averLineDay = averLineDay;
	}
	public int getStoreNum() {
		return storeNum;
	}
	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}
	public int getTransferDay() {
		return transferDay;
	}
	public void setTransferDay(int transferDay) {
		this.transferDay = transferDay;
	}
	private int storeNum;
	private int transferDay;
public StrategyPo(String beginString,String endDateString,ArrayList<QueryPo> sharesArrayList){
	beginDate=beginString;
	endDate=endDateString;
	sharesList=sharesArrayList;
}
 /**
 * 开始日期，结束日期，股票池，均线类型天数（5，10，20），仓库中的股票数，调仓间隔的天数
 */
public StrategyPo(String beginDateString,String endDateString,ArrayList<QueryPo> sharesArrayList,int averDay,int store,int transfer){ 
		beginDate=beginDateString;
		endDate=endDateString;
		sharesList=sharesArrayList;
		storeNum=store;
		averLineDay=averDay;
		transferDay=transfer;
}

}
