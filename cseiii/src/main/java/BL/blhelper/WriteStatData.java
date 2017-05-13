package BL.blhelper;

import java.util.ArrayList;
import java.util.Map;



import BL.blockBL.BlockBLImpl;
import BL.blservice.MomentumBL;
import BL.blservice.SharesBlockBL;
import BL.momentumBL.MomentumBLImpl;

/**
 * @author 朱润之
 * 写入统计数据
 */
public class WriteStatData {
	 String beginDate = "3/19/12";
	 String endDate = "1/19/13";
	 String blockName = "中小板";
	 public WriteStatData(String name) {
		 blockName = name;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param formDay 循环到的形成期天数
	 * @param holdDay 循环到的持有期天数
	 * 
	 */
	public void writeInData(int formDay,int holdDay){
		SharesBlockBL blockBL = new BlockBLImpl();
		SortHelper helper = new SortHelper();
		Map<String, String> temp = blockBL.getSharesList(blockName);
		ArrayList<String> idList = helper.mapKeyToArray(temp);
		MomentumBL bl = new MomentumBLImpl();
		
			for(int j=3;j<=holdDay;j++){
				bl.getMomentumResult(beginDate,endDate, 10, j, idList, blockName);
			}
		
	}
	public static void main(String[] args) {
		WriteStatData data = new WriteStatData("主板");
		
		data.writeInData(25, 25);
	}
}
