package vopo;

import java.util.ArrayList;

public class QueryPo {
	private String name;
	private String id;
	private String beginDate;
	private String endDate;
	private ArrayList<SharesData> dataList;
	public QueryPo(String name, String id, String beginDate, String endDate, ArrayList<SharesData> dataList) {
		this.name = name;
		this.id = id;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.dataList = dataList;
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
	public ArrayList<SharesData> getDataList() {
		return dataList;
	}
	public void setDataList(ArrayList<SharesData> dataList) {
		this.dataList = dataList;
	}
	

}
