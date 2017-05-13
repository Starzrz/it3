package BL.blservice;

import vopo.MarketVo;

/**
 * @author 朱润之
 * 市场温度计的逻辑层接口
 */
public interface SharesMarketBL {
	public MarketVo market(String date);

}
