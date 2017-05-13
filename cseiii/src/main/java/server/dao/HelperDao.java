package server.dao;

/**
 * @author 朱润之
 * 工具方法的dao
 */
public interface HelperDao {
	/**
	 * @param indate
	 * @param days
	 * @return 日期加减的方法，传入格式为mm/dd/yy，days正负表示加减
	 */
	public String changeDays(String indate,int days);
}
