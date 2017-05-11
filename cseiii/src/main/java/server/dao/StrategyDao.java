package server.dao;

import java.util.ArrayList;

import server.others.UpdateResult;
import server.po.StrategyPo;

public interface StrategyDao {

	public StrategyPo getStrategyPo(String strategyID);
	
	public ArrayList <StrategyPo> getMakeStrategyList (String userID);
	
	public ArrayList <StrategyPo> getStoreStrategyList (String userID);
	
	public UpdateResult updateStrategy (StrategyPo po);
	
	public ArrayList <StrategyPo> getAllStrategy ();
	
	public Boolean deleteStrategy (String strategyID);
	
	public Boolean addStoreStrategy (String userID, String strategyID);
	
	public StrategyPo cancelStoreStrategy (String userID, String strategyID);
	
	public Boolean addStrategy  (StrategyPo po);
}
