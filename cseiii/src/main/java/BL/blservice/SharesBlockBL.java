package BL.blservice;

import java.util.ArrayList;
import java.util.Map;

public interface SharesBlockBL {
/**
 * @param i
 * @return 得到板块名字的列表，i=0表示三大板块，i=1表示行业
 */
public ArrayList<String> getBlockList(int i);
	
	/**
	 * @param name
	 * @return 返回板块下的所有股票，key表示id，val表示名字
	 */
	public Map<String,String> getSharesList(String name);

}
