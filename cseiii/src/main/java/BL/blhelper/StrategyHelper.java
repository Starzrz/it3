package BL.blhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BL.averStrategyBL.AverStrategyBLImpl;
import BL.blservice.SharesAverStrategyBL;
import BL.momentumBL.HoldRiseData;
import BL.momentumBL.MomentumCal;
import server.dao.StrategyDao;
import server.dataImpl.StrategyDaoImpl;
import vopo.StrategyPo;
import vopo.QueryPo;

public class StrategyHelper {

		public StrategyRate getStrategyRate(ArrayList<String> idList,int averDay,int holdDay,double StoreNum,String beginDate,String endDate){
			StrategyRate rate=new StrategyRate();
			StrategyDao averStrategyDao=new StrategyDaoImpl();
			SortHelper sortHelper=new SortHelper();
			MomentumCal momentumCal=new MomentumCal();
			DateDeal dateDeal=new DateDeal();
			MomentumHelper momentumHelper=new MomentumHelper();
			AverStrategyHelper helper=new AverStrategyHelper();//需要的辅助类
			//开始操作
			//计算持有期的胜率
			//开始最后一天的策略胜率和基础胜率
			
			//准备
			
			String formBegin = dateDeal.logDateChange(beginDate, -averDay);
			ArrayList<QueryPo> sharesDataList=averStrategyDao.getSharesDate(idList, beginDate, endDate).getSharesList();//所有的数据!!!
			beginDate=sharesDataList.get(0).getDataList().get(averDay+1).date;//因为开始日期是夹在中间的,所以要进行调整
			StrategyPo po=new StrategyPo(beginDate, endDate, sharesDataList);
			idList=helper.changeID(idList);//所有股票ID的集合
			
			double principle =1.0; //本金
			int allDay = sharesDataList.get(0).getDataList().size();  //总交易日,是数据中的天数，所以没问题
			int turns = allDay/holdDay; //循环的轮数
			int rest=allDay%holdDay;
			Map<String, Double> strategyMap =new HashMap<String, Double>();//策略收益率map
			Map<String,Double> holdMap = new HashMap<>();  //持有期的收益率map
			Map<String,Double> holdTimeMap = new HashMap<>();//各个持有期中的
			int averLineDay=po.getAverLineDay();//均线天数，形成期
			int storeNum=po.getStoreNum();//仓内股票数
			int transferDay=po.getTransferDay();//股票的持有期，调仓天数
			
			//第一个形成期的收益率
			Map<String, Double> firstRate = helper.calFormRate(po, beginDate, averLineDay);//第一个形成期
			ArrayList<String> winId = helper.getWinGroup(firstRate,storeNum,beginDate,endDate,averLineDay,beginDate); //第一个胜者组
			ArrayList<QueryPo> winList = helper.findShares(winId, po);
			
			//开始循环
			for(int i=0;i<turns;i++){
				HoldRiseData holdRiseData=momentumCal.calHoldRise(winList, beginDate, transferDay,principle);
				holdMap = holdRiseData.getRiseDate();//持有期中的收益率
				principle =holdRiseData.getPrinciple();
				strategyMap.putAll(holdMap);      //将收益率加入全部的列表
				String holdBegin = sortHelper.getFirstObject(holdMap);
				double beginMoney = holdMap.get(holdBegin);  //持有期第一天的收益率
				holdMap=sortHelper.sortByDate(holdMap);
				beginDate = sortHelper.getLastObject(holdMap);    //将上一个持有期的最后一天的后一天作为开始日期
				double lastMoney = holdMap.get(beginDate);     //持有期最后一天的收益率
				holdTimeMap.put(holdBegin,lastMoney-beginMoney);   //持有期的总收益率
				
				
				beginDate = dateDeal.logDateChange(beginDate, 1);//进行下一次的准备
				formBegin=dateDeal.logDateChange(beginDate, -averDay);
				if (sharesDataList.get(0).getDataList().size()>averDay) {
					beginDate=sharesDataList.get(0).getDataList().get(averDay).date;//因为开始日期是夹在中间的,所以要进行调整
				}else{
					break;
				}
				firstRate = helper.calFormRate(po, formBegin, averLineDay);
				winId = helper.getWinGroup(firstRate,storeNum,beginDate,endDate,averLineDay,beginDate);  //重新计算胜者组
				winList = helper.findShares(winId, po);
			}
			if(rest>0){
				HoldRiseData holdRiseData=momentumCal.calHoldRise(winList, beginDate, transferDay,principle);
				holdMap = holdRiseData.getRiseDate();//持有期中的收益率
				principle =holdRiseData.getPrinciple();
				strategyMap.putAll(holdMap);
			}
			rate.strategyRate=principle-1;
			Map<String, Double> baseMap =new HashMap<String, Double>();//基准
			baseMap = momentumCal.calBlockRise("主板",po.getBeginDate(), allDay);  //如果是板块，使用历史数据计算
			
			return rate;
		}
		
		
		
}
