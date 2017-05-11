package server.dao;

import java.util.ArrayList;

import server.others.LoginResult;
import server.others.RegisterResult;
import server.others.UpdateResult;
import server.po.SharesData;
import server.po.StrategyPo;
import server.po.UserPo;

public interface SharesDao {
	public ArrayList<SharesData> getSharesData (ArrayList<String> IDList,String beginDate,String endDate);
	
	public UpdateResult updateSharesData (ArrayList<SharesData> list);
}
