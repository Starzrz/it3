package BL.blhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sound.midi.SysexMessage;


import BL.blservice.SharesQueryBL;
import BL.momentumBL.MomentumCal;
import BL.queryBL.QueryBLImpl;
import server.dao.SharesDao;
import server.dataImpl.SharesDaoImpl;
import vopo.StrategyPo;
import vopo.QueryPo;
import vopo.SharesData;

/**
 * @author 朱应山
 *均线的回归策略助手
 */
public class AverStrategyHelper {
	MathHelper mathHelper=new MathHelper() ;
	DateDeal dateDeal=new DateDeal();
	MomentumHelper momentumHelper=new MomentumHelper();
	MomentumCal momentumCal=new MomentumCal();
	SortHelper sortHelper=new SortHelper();
/**
*获得正确的po
*
*/
	public StrategyPo getPo(String beginDateString,String endDateString,ArrayList<QueryPo> sharesArrayList,int averDay,int store,int transfer ){
		//String beginDate =dateDeal.preToLog(beginDateString);
		//String endDate=dateDeal.preToLog(endDateString);
		StrategyPo po=new StrategyPo(beginDateString, beginDateString, sharesArrayList, averDay,store, transfer);
		return po;
	}
/*
 * 用来把id中多余的0清除
 */
	public  ArrayList<String> changeID(ArrayList<String> arr){
		ArrayList<String> newArrayList=new ArrayList<>();
		for(String s:arr){
			newArrayList.add(s.replace("0", ""));
		}
		return newArrayList;
	}
	/*
	 * 获取到的是日期列表，从开始日期开始，一直到结束
	 */
	public ArrayList<String> cutList(ArrayList<QueryPo> baseList,String beginDate){
		ArrayList<String> list=new ArrayList<>();
		ArrayList<SharesData> datas=baseList.get(0).getDataList();
		for(SharesData singleData:datas){
			if(isBefore(singleData.date	, beginDate)){
				continue;
			}else {
				list.add(singleData.date);
			}
		}
		return  list;
		
	}
	public boolean isBefore (String date1,String date2){
		String[]d1=date1.split("/");
		String[] d2=date2.split("/");
		int  month1=Integer.parseInt(d1[0]);
		int day1=Integer.parseInt(d1[1]);
		int  year1=Integer.parseInt(d1[2]);
		int month2=Integer.parseInt(d2[0]);
		int day2=Integer.parseInt(d2[1]);
		int year2=Integer.parseInt(d2[2]);
		boolean result=true;
		if (year1<year2) {
			result=true;
		}else if(year1==year2){
			if (month1<month2) {
				result=true;
			}else if (month1==month2){
				if (day1<day2) {
					result=true;
				}else if (day1==day2) {
					result=false;
				}else{
					result=false;
				}
			}else{
				result=false;
			}
		}else{
			result=false;
		}
		return result;
	}
/**
*获得策略的map
*/	
	public Map<String, Double> getStrategyMap(StrategyPo po){
		Map<String, Double> strategyMap =new HashMap<String, Double>();//策略收益率map
		Map<String,Double> holdRistMap = new HashMap<>();  //持有期的收益率map
		String beginDate=po.getBeginDate();
		String endDate=po.getEndDate();
		int averLineDay=po.getAverLineDay();//均线天数，形成期
		int storeNum=po.getStoreNum();//仓内股票数
		int transferDay=po.getTransferDay();//股票的持有期，调仓天数
		double principle =1.0; //本金
		int allDay = po.getSharesList().get(0).getDataList().size();  //总交易日
		int turns = allDay/transferDay; //循环的轮数
		int rest = allDay%transferDay;  //最后剩下的天数
		//第一个形成期的收益率
		Map<String, Double> firstRate = calFormRate(po, beginDate, averLineDay);//第一个形成期
		ArrayList<String> winId = getWinGroup(firstRate,storeNum,beginDate,endDate,averLineDay,beginDate); //第一个胜者组
		ArrayList<QueryPo> winList = findShares(winId, po);
		for(int i=0;i<turns;i++){
			holdRistMap = momentumCal.calHoldRise(winList, beginDate, transferDay,principle).getRiseDate();
			strategyMap.putAll(holdRistMap);      //将收益率加入全部的列表
			String holdBegin = sortHelper.getFirstObject(holdRistMap);
			double beginMoney = holdRistMap.get(holdBegin);  //持有期第一天的收益率
			
			beginDate = sortHelper.getLastObject(holdRistMap);    //将上一个持有期的最后一天的后一天作为开始日期
			double lastMoney = holdRistMap.get(beginDate);     //持有期最后一天的收益率
			holdRistMap.put(holdBegin,lastMoney-beginMoney);   //持有期的总收益率
			principle = principle+holdRistMap.get(beginDate); //本金变化
			beginDate = dateDeal.logDateChange(beginDate, 1);
			String formBegin = dateDeal.logDateChange(beginDate, -averLineDay);
			formBegin = dateDeal.logDateChange(beginDate, -averLineDay);
			firstRate =calFormRate(po, formBegin, averLineDay);
			winId = getWinGroup(firstRate,storeNum,beginDate,endDate,averLineDay,beginDate);  //重新计算胜者组
			winList = findShares(winId, po);
		}
		if(rest>0){
			holdRistMap = momentumCal.calHoldRise(winList, beginDate, rest, principle).getRiseDate();  //最后一个周期
			strategyMap.putAll(holdRistMap);
			beginDate = sortHelper.getLastObject(holdRistMap);    
			principle = principle+holdRistMap.get(beginDate);  //最终金额
		}
		return strategyMap;
	}
/**
*获得基准的map
*/
	public Map<String, Double> getBaseMap(StrategyPo po,String blockName){
		int allDay = po.getSharesList().get(0).getDataList().size();  //总交易日
		Map<String, Double> baseMap =new HashMap<String, Double>();
		if(blockName.equals("自选股")){
			baseMap = momentumCal.calHoldRise(po.getSharesList(), po.getBeginDate(), allDay, 1.0).getRiseDate();  //如果是自选股，按照平均数计算
		}else{
			baseMap = momentumCal.calBlockRise(blockName,po.getBeginDate(), allDay);  //如果是板块，使用历史数据计算
		}
		return baseMap;
	}	
	
/**
 * 	
 * @param winId
 * @param po
 * @return ID列表中对应的股票列表
 */
public   ArrayList<QueryPo> findShares(ArrayList<String> winId, StrategyPo po) {
	ArrayList<QueryPo> List = po.getSharesList();
	ArrayList<QueryPo> result = new ArrayList<>();
	for(QueryPo temp:List){
		if(winId.contains(changeID(temp.getId()))){
			result.add(temp);
		}
	}
	return result;
}


/**
*获得形成期的股票收益率
*
*/	
	public  Map< String , Double> calFormRate(StrategyPo po,String formBeginDate,int formDays ){
		Map< String, Double> rateMap=new HashMap<String, Double>();
		MomentumHelper helper = new MomentumHelper();
		ArrayList<QueryPo> shareList = po.getSharesList();
		for(QueryPo temp:shareList){
			try{
			if(!helper.hasClose(temp, formBeginDate, formDays)){  //判断有无停牌情况，如果有，直接跳过
				continue; 
			}
			ArrayList<SharesData> calDate = helper.getPointData(temp.getDataList(), formBeginDate, formDays); //得到形成期内的股票历史数据
			if (calDate.size()==0) {
				continue;
			}
			double lastPrice = calDate.get(calDate.size()-1).adjClose; //最后一日的复权收盘价
			double firstPrice = calDate.get(0).lastAdj;  //第一日前一日的复权收盘价
			double rise = (lastPrice-firstPrice)/firstPrice; //收益率
			rateMap.put(temp.getId(),rise);}
			catch (Exception e) {
				System.out.println("错误ID"+temp.getId());
				e.printStackTrace();
				
			}
		}
		return rateMap;
	}
/**
*返回胜利组的ID集合
*/
	public  ArrayList<String> getWinGroup(Map<String, Double> riseMap,int storeNum,String beginDate,String endDate,
			int EMADay,String today){
		SharesDao getOneDay=new SharesDaoImpl();
		Map<String, Double> calMap=new HashMap<>();
		SharesQueryBL query=new QueryBLImpl();
		ArrayList<String> winGroup=new ArrayList<>();//s;
		ArrayList<String> list=new ArrayList<>();
		for(String id:riseMap.keySet()){
			list.add(id);
		}
		ArrayList<SharesData> datas=getOneDay.getOneDayShare(list, today);//不同股票的在某天所有的数据
		beginDate=dateDeal.BlToEMA(beginDate);
		endDate=dateDeal.BlToEMA(endDate);
		for(SharesData data:datas){
				String s=data.number;
				s=changeID(s);
				double close=data.adjClose;
				Map<String , Double> tempMap=query.EMAData(s, beginDate, endDate, EMADay);//均线返回的是日期和该日期的收益
				double temp=0.0;
				if (tempMap.containsKey(today)) {//缺失某个奇怪的数据导致不能回测
					temp=tempMap.get(today);
					double mark=(temp-close)/temp;
					calMap.put(s, mark);
				}else{
					calMap.put(s, (double) -1);
				}
				
		}
		SortHelper helper = new SortHelper();
		riseMap = helper.sortByRise(calMap); //按照收益率排序
		Iterator<Map.Entry<String, Double>> it = riseMap.entrySet().iterator();
		for(int i=0;i<storeNum;i++){
				winGroup.add(it.next().getKey());
		}
		return winGroup;
	}
private String changeID(String s) {
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
}
