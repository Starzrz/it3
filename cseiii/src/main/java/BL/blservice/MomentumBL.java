package BL.blservice;

import java.util.ArrayList;
import java.util.Map;

import vopo.BackTestPo;
import vopo.BackTestVo;
import vopo.QueryPo;
import vopo.StrategyPo;

public interface MomentumBL {
	
	/**
	 * @param begindate 开始日期
	 * @param endDate 结束日期
	 * @param formDays 形成期
	 * @param holdDays 持有期
	 * @param sharesList 股票池
	 * @param blockName 板块名，如果为自选股，则为"自选股票"
	 * @return 回测的结果
	 */
	public BackTestVo getMomentumResult(String beginDate,String endDate,int formDays,int holdDays,ArrayList<String> sharesList,String blockName);
	
	public BackTestVo calMomentumResult(StrategyPo backTestPo,Map<String, Double> baseRiseMap,String beginDate, String endDate, int formDays, int holdDays,
			ArrayList<String> sharesList, String blockName);
	
	public Map<String, Double> calBaseRise(ArrayList<QueryPo> sharesList,int days,String beginDate,String blockName);
}
