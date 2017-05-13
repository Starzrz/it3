package BL.blservice;

import vopo.SharesListPo;
import vopo.SharesListVo;

public interface SharesListBL {
	/**
	 * @return 得到所有股票列表的逻辑层方法，可重载
	 */
	public SharesListVo getSharesList();

}
