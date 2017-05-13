package vopo;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 朱润之
 *
 */
public class BlockPo {
	private Map<String, Double> riseList; //涨跌幅列表
	private Map<String, Double> openPrice; //开盘价
	private Map<String, Double> closePrice; //收盘价
	private Map<String, Double> lastClose; //上一日收盘价
	private String blockName;
	private String blockId;
	private Map<String, String> sharesList;
	
	public Map<String, String> getSharesList() {
		return sharesList;
	}
	public void setSharesList(Map<String, String> sharesList) {
		this.sharesList = sharesList;
	}
	public Map<String, Double> getRiseList() {
		return riseList;
	}
	public void setRiseList(Map<String, Double> riseList) {
		this.riseList = riseList;
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
	public Map<String, Double> getLastClose() {
		return lastClose;
	}
	public void setLastClose(Map<String, Double> lastClose) {
		this.lastClose = lastClose;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	public BlockPo(Map<String, Double> riseList, Map<String, Double> openPrice, Map<String, Double> closePrice,
			Map<String, Double> lastClose, String blockName, String blockId, Map<String, String> sharesList) {
		this.riseList = riseList;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.lastClose = lastClose;
		this.blockName = blockName;
		this.blockId = blockId;
		this.sharesList = sharesList;
	}
	


}
