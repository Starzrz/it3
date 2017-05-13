package BL.blockBL;

import java.util.ArrayList;
import java.util.Map;

import BL.blservice.SharesBlockBL;
import server.dao.SharesDao;
import server.dataImpl.SharesDaoImpl;


public class BlockBLImpl implements SharesBlockBL{
	SharesDao dao;
	public BlockBLImpl() {
		dao = new SharesDaoImpl();
		// TODO Auto-generated constructor stub
	}
	@Override
	public ArrayList<String> getBlockList(int i) {
		
		// TODO Auto-generated method stub
		return dao.getBlockList(i);
	}

	@Override
	public Map<String, String> getSharesList(String name) {
		// TODO Auto-generated method stub
		return dao.getSharesList(name);
	}

}
