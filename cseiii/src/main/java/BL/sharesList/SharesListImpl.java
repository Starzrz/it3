package BL.sharesList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BL.blhelper.SortHelper;
import BL.blservice.SharesListBL;

import server.dao.SharesDao;
import server.dataImpl.SharesDaoImpl;
import vopo.SharesData;
import vopo.SharesListPo;
import vopo.SharesListVo;

public class SharesListImpl implements SharesListBL{

	@Override
	public SharesListVo getSharesList() {
		SharesDao dao = new SharesDaoImpl();
		SharesListPo po = dao.getSharesList();
		ArrayList<SharesData> datas =po.getDataList();
		Map<String, String> result = new HashMap<>();
		for(SharesData data:datas){
			result.put(data.number, data.name);
		}
		SortHelper deal = new SortHelper();
		result = deal.sortById(result);
		SharesListVo vo = new SharesListVo();
		vo.setSharesList(result);
		// TODO Auto-generated method stub
		return	vo;
	}
	

}
