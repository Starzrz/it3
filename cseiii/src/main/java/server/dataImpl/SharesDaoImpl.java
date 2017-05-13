/**
 * 
 */
package server.dataImpl;

import java.util.ArrayList;
import java.util.Map;

import server.dao.SharesDao;
import server.others.UpdateResult;
import vopo.BlockPo;
import vopo.ComparePo;
import vopo.MarketPo;
import vopo.QueryPo;
import vopo.SharesData;
import vopo.SharesListPo;


/**
 * @author 朱应山
 *	
 */
public class SharesDaoImpl implements SharesDao{
	/*
	 * 获得IDList在时间期间的股票数据
	 */
	public ArrayList<SharesData> getSharesData (ArrayList<String> IDList,String beginDate,String endDate){
		return null;
	}
	/*
	 * 更新股票每日信息
	 */
	public UpdateResult updateSharesData (ArrayList<SharesData> list){
		return null;
	}
	public QueryPo sharesQuery(String id, String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryPo EMAChangeData(String id, String beginDate, String endDate, int day) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<String> zeroQuery(String id, String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BlockPo getBlockData(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<String> getBlockList(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, String> getSharesList(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public MarketPo findMarket(String date) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ComparePo findCompare(String id, String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SharesListPo getSharesList() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<SharesData> getOneDayShare(ArrayList<String> sharesId, String date) {
		// TODO Auto-generated method stub
		return null;
	}
}
