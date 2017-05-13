package BL.blservice;

import vopo.CompareVo;

/**
 * @author 朱润之
 * 比较股票的逻辑层接口
 */
public interface SharesCompareBL {
	public CompareVo[] compareSh(String id1,String id2,String beginDate,String endDate);

	/**
	 * 
	 * @param id1
	 * @param id2
	 * @param beginDate
	 * @param endDate
	 * @return 返回一段时间内比较两股票的最高值、最低值
	 */
	public double[][] minAndMax(String id1,String id2,String beginDate,String endDate);
	
	/**
	 * 
	 * @param id1
	 * @param id2
	 * @param beginDate
	 * @param endDate
	 * @return 返回一段时间内的两股票涨跌幅
	 */
	public double[] change(String id1,String id2,String beginDate,String endDate);
}
