package BL.blservice;

import vopo.StrategyType;
import vopo.StrategyVo;

public interface StrategyBL {
	/**
	 * @param type 策略类型
	 * @param isHold 固定的是哪个变量，true则固定持有期，false则固定形成期
	 * @param day 固定的天数
	 * @return 返回策略的统计结果,传入日期格式为mm/dd/yy
	 */
	public StrategyVo getStrategyData(StrategyType type,boolean isHold,int day,String beginDate,String endDate);
}
