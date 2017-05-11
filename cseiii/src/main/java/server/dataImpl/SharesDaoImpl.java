/**
 * 
 */
package server.dataImpl;

import java.util.ArrayList;

import server.dao.SharesDao;
import server.others.UpdateResult;
import server.po.SharesData;

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
}
