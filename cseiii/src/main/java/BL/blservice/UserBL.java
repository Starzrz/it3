package BL.blservice;

import server.others.LoginResult;
import server.others.RegisterResult;
import vopo.UserPo;

public interface UserBL {
/**
 * @param id
 * @param password
 * @return 登陆
 */
public LoginResult getLoginResult (String id,String password) ;
	
	/**
	 * @param po
	 * @return 注册
	 */
	public RegisterResult getRegisterResult (UserPo po);
	
	/**
	 * @param id
	 * @return 得到个人信息
	 */
	public UserPo getUserPo(String id);
}
