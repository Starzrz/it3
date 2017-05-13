package vopo;

import java.util.Map;

public class SharesListVo {
	private Map<String, String> sharesList;  //包含股票列表，键值是id,值是名称
	
	public SharesListVo() {
		// TODO Auto-generated constructor stub
	}
	public SharesListVo(Map<String, String> sharesList) {
		this.sharesList = sharesList;
	}

	public Map<String, String> getSharesList() {
		return sharesList;
	}

	public void setSharesList(Map<String, String> sharesList) {
		this.sharesList = sharesList;
	}
	

}
