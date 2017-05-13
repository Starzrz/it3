package server.dataImpl;

import java.util.ArrayList;

import server.dao.StrategyDao;
import server.others.UpdateResult;
import vopo.StrategyPo;


public class StrategyDaoImpl implements StrategyDao {
	/*
	 * 获得ID对应策略的策略PO
	 */
	public StrategyPo getStrategyPo(String strategyID){
		return null;
	}
	/*
	 * 获得用户制定的策略PO列表
	 */
	public ArrayList <StrategyPo> getMakeStrategyList (String userID){
		return null;
	}
	/*
	 * 获得用户收藏的策略PO列表
	 */
	public ArrayList <StrategyPo> getStoreStrategyList (String userID){
		return null;
	}
	/*
	 * 更新策略具体信息
	 */
	public UpdateResult updateStrategy (StrategyPo po){
		return null;
	}
	/*
	 * 获得所有策略列表
	 */
	public ArrayList <StrategyPo> getAllStrategy (){
		return null;
	}
	/*
	 * 删除策略
	 */
	public Boolean deleteStrategy (String strategyID){
		return null;
	}
	/*
	 * 收藏策略
	 */
	public Boolean addStoreStrategy (String userID, String strategyID){
		return null;
	}
	/*
	 * 取消策略
	 */
	public StrategyPo cancelStoreStrategy (String userID, String strategyID){
		return null;
	}
	/*
	 * 添加策略
	 */
	public Boolean addStrategy (StrategyPo po){
		return null;
	}
	@Override
	public StrategyPo getSharesDate(ArrayList<String> sharesId, String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}
}
