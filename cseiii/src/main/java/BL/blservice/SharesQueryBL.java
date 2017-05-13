package BL.blservice;

import java.util.ArrayList;
import java.util.Map;

import vopo.QueryPo;
import vopo.QueryVo;

/**
 * @author 朱润之
 * 查询股票的逻辑层接口
 */
public interface SharesQueryBL {
	public QueryVo sharesQuery(String id,String beginDate,String endDate);

	Map<String, Double> EMAData(String id, String beginDate, String endDate, int day);
	//public ArrayList<String> zeroQuery(String id,String beginDate,String endDate);

}
