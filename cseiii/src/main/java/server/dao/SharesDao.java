package server.dao;

import java.util.ArrayList;
import java.util.Map;

import server.others.LoginResult;
import server.others.RegisterResult;
import server.others.UpdateResult;
import vopo.BlockPo;
import vopo.ComparePo;
import vopo.MarketPo;
import vopo.QueryPo;
import vopo.SharesData;
import vopo.SharesListPo;


public interface SharesDao {
	/**
	 * @param sharesId
	 * @param date
	 * @return 得到某一日，对应股票id的所有股票信息
	 */
	public ArrayList<SharesData> getOneDayShare(ArrayList<String> sharesId,String date);
	/**
	 * @return 得到所有股票的列表，可重载
	 */
	public SharesListPo getSharesList();
	/**
	 * @param id
	 * @param beginDate
	 * @param endDate
	 * @return 得到对应id股票开始日期到结束日期的比较信息
	 */
	public ComparePo findCompare(String id,String beginDate,String endDate);
	/**
	 * @return 得到板块的历史数据
	 */
	public BlockPo getBlockData(String name);
	/**
	 * @param i=0代表三大板块，i=1代表行业板块
	 * @return 得到板块列表，
	 */
	public ArrayList<String> getBlockList(int i);
	
	/**
	 * @param name
	 * @return 得到板块的股票信息,map中key对应id，值对应名字
	 */
	public Map<String,String> getSharesList(String name);
	/**
	 * @param date
	 * @return 指定日期的市场信息
	 */
	public MarketPo findMarket(String date);
	/**
	 * @param id
	 * @param beginDate
	 * @param endDate
	 * @return 查询对应id股票一段时间的信息
	 */
	public QueryPo sharesQuery(String id,String beginDate,String endDate);
	
	
	/**
	 * @param id
	 * @param beginDate
	 * @param endDate
	 * @return 查询所有股票
	 */
	public ArrayList<String> zeroQuery(String id,String beginDate,String endDate);
	/**
	 * @param id
	 * @param beginDate
	 * @param endDate
	 * @param day
	 * @return 查询均线信息，返回开始日期的均线日之前到结束日的股票信息
	 */
	public QueryPo EMAChangeData(String id,String beginDate,String endDate,int day);
	
	public ArrayList<SharesData> getSharesData (ArrayList<String> IDList,String beginDate,String endDate);
	
	public UpdateResult updateSharesData (ArrayList<SharesData> list);
}
