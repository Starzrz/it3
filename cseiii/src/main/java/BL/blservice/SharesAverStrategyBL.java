package BL.blservice;

import java.util.ArrayList;
import java.util.Map;

import vopo.StrategyPo;
import vopo.AverStrategyVo;
import vopo.QueryPo;

public interface SharesAverStrategyBL {
	/**
	 * 开始日期，结束日期，股票池，均线类型天数，仓库中的股票数，调仓间隔的天数,板块名称
	 */
	public AverStrategyVo averStrategy(String beginDateString,String endDateString,ArrayList<String	> sharesIDArrayList,int averDay,
			int store,int transfer,String blockName);
}
