package server.dao;

import server.others.LoginResult;
import server.others.RegisterResult;
import server.others.UpdateResult;
import vopo.UserPo;


public interface UserDao {

	public LoginResult getLoginResult (String id,String password) ;
	
	public RegisterResult getRegisterResult (UserPo po);
	
	public UserPo getUserPo(String id);
	
	public UpdateResult updateUserPo (UserPo vo);
	
}
