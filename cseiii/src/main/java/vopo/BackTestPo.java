package vopo;

import java.util.ArrayList;

/**
 * @author 朱润之
 * 动量策略的po
 */
public class BackTestPo {
	private ArrayList<QueryPo> sharesList;  
	private String beginDate;
	private String endDate;
	public ArrayList<QueryPo> getSharesList() {
		return sharesList;
	}
	public void setSharesList(ArrayList<QueryPo> sharesList) {
		this.sharesList = sharesList;
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
	public BackTestPo(ArrayList<QueryPo> sharesList, String beginDate, String endDate) {
		this.sharesList = sharesList;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
	

}
