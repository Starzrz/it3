package BL.queryBL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import BL.blhelper.DateDeal;
import BL.blservice.SharesQueryBL;
import server.dao.SharesDao;
import server.dataImpl.SharesDaoImpl;
import vopo.QueryPo;
import vopo.QueryVo;
import vopo.SharesData;

public class QueryBLImpl implements SharesQueryBL{

	@Override
	public QueryVo sharesQuery(String id, String beginDate, String endDate) {
		SharesDao dao = new SharesDaoImpl();
		DateDeal dateDeal = new DateDeal();
		beginDate = dateDeal.preToLog(beginDate);
		endDate = dateDeal.preToLog(endDate);
		QueryPo po =dao.sharesQuery(id, beginDate, endDate);
		return poToVo(po);
	}
	public static String changeID(String s){
		String string="";
		boolean flag=false;
		for(int i=0;i<s.length();i++){
			if (s.charAt(i)!='0') {
				flag=true;
			}
			if(flag){
				string=s.substring(i);
				break;
			}
		}
		return string;
	}
	/** 均线图EMA
	 * 开始日期日期之前的n天内的均值
	 */
	@Override
	public Map<String ,Double > EMAData(String id,String beginDate,String endDate,int day){
		id=changeID(id);
		Map<String, Double> EMAMap= new HashMap<>();
		SharesDao dao = new SharesDaoImpl();
		DateDeal dateDeal = new DateDeal();
		beginDate = dateDeal.preToLog(beginDate);
		endDate = dateDeal.preToLog(endDate);
		QueryPo po =dao.EMAChangeData(id, beginDate, endDate,day);
		//day=5||10||30||60||120||240，list中的数据需要包括begindate之前的day-1天
		ArrayList<SharesData> EMAList=po.getDataList();
		loop:for(int i=0;i<EMAList.size();i++){
			double sum=0;
			for(int counter=0;counter<day;counter++){//计数器
					if((counter+i)<EMAList.size()){
						sum+=EMAList.get(counter+i).closePrice;
					}else{
						break loop;
					}
				
			}
			EMAMap.put(EMAList.get(i).date, formatDouble1(sum/day));
		}
		return EMAMap;
	}
	/**
     * 把数组中的所有值加起来返回
     * @param d
     */
	private static double add(ArrayList<SharesData> tempList) {
		double sum=0;
		for(SharesData singleData:tempList){
			sum+=singleData.closePrice;
			System.out.println(tempList.size());
		}
		return sum;
	}

	/**
     * 保留两位小数，四舍五入的一个的方法
     * @param d
     */
    public static double formatDouble1(double d) {
        return (double)Math.round(d*100)/100;
    }
    
	public QueryVo poToVo(QueryPo po){
		if(po==null){
			return null;
		}
		if(po.getDataList().size()<1){
			return null;
		}
		String name = po.getName();
		String id = po.getId();
		String beginDate = po.getBeginDate();
		String endDate = po.getEndDate();
		Map<String, Double> openPrice = new HashMap<>();
		Map<String, Double> closePrice = new HashMap<>();
		Map<String, Double> highPrice = new HashMap<>();
		Map<String, Double> lowPrice = new HashMap<>();
		Map<String, Integer> volume = new HashMap<>();
		for(SharesData data:po.getDataList()){
			openPrice.put(data.date, data.openPrice);
			closePrice.put(data.date, data.closePrice);
			highPrice.put(data.date, data.maxPrice);
			lowPrice.put(data.date, data.minPrice);
			volume.put(data.date, (int) data.volume);
		}
		QueryVo vo = new QueryVo(name, id, beginDate, endDate, openPrice, closePrice, highPrice, lowPrice, volume);
		return vo;
	}
/*
	@Override
	public ArrayList<String> zeroQuery(String id, String beginDate, String endDate) {
		SharesDao dao = new SharesDaoImpl();
		return dao.zeroQuery(id, beginDate, endDate);
	}
	
*/
	
}
