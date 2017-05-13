package server.dataImpl;

import server.dao.UserDao;
import server.others.LoginResult;
import server.others.RegisterResult;
import server.others.UpdateResult;
import vopo.UserPo;


public class UserDaoImpl implements UserDao {
	/*
	 * 登陆
	 */
	public LoginResult getLoginResult (String id,String password){
		return null;
	}
	/*
	 * 注册信息
	 */
	public RegisterResult getRegisterResult (UserPo po){
		return null;
	}
	/*
	 * 获得ID对应的用户PO
	 */
	public UserPo getUserPo(String id){
		return null;
	}
	/*
	 * 更新用户PO
	 */
	public UpdateResult updateUserPo (UserPo vo){
		return null;
	}
}
