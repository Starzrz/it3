package BL.userBL;

import BL.blservice.UserBL;
import server.dao.UserDao;
import server.dataImpl.UserDaoImpl;
import server.others.LoginResult;
import server.others.RegisterResult;
import vopo.UserPo;

public class UserBLImpl implements UserBL{
	UserDao dao;
	public UserBLImpl() {
		dao = new UserDaoImpl();
		// TODO Auto-generated constructor stub
	}
	@Override
	public LoginResult getLoginResult(String id, String password) {
		// TODO Auto-generated method stub
		return dao.getLoginResult(id, password)
	}

	@Override
	public RegisterResult getRegisterResult(UserPo po) {
		// TODO Auto-generated method stub
		return dao.getRegisterResult(po);
	}

	@Override
	public UserPo getUserPo(String id) {
		// TODO Auto-generated method stub
		return dao.getUserPo(id);
	}

}
