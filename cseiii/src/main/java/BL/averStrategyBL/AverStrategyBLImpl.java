package BL.averStrategyBL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import BL.blhelper.AverStrategyHelper;
import BL.blhelper.DateDeal;
import BL.blhelper.MomentumHelper;
import BL.blhelper.SortHelper;
import BL.blservice.SharesAverStrategyBL;
import BL.momentumBL.HoldRiseData;
import BL.momentumBL.MomentumCal;
import BL.momentumBL.OutRiseData;
import server.dao.StrategyDao;
import server.dataImpl.StrategyDaoImpl;
import vopo.StrategyPo;
import vopo.AverStrategyVo;
import vopo.QueryPo;

/**
 * @author 朱应山
 *均值回归的策略实现
 */
public class AverStrategyBLImpl implements SharesAverStrategyBL{
	@Override
	public AverStrategyVo averStrategy(String beginDateString,String endDateString,ArrayList<String> sharesIDArrayList,
			int averDay,int store,int transfer, String blockName){
		double strategyRate=0.0;
		AverStrategyHelper helper=new AverStrategyHelper();//需要的辅助类
		SortHelper sortHelper=new SortHelper();
		MomentumCal momentumCal=new MomentumCal();
		DateDeal dateDeal=new DateDeal();
		MomentumHelper momentumHelper=new MomentumHelper();
		StrategyDao dao = new StrategyDaoImpl();
		//准备
		String formBegin = dateDeal.logDateChange(beginDateString, -averDay);
		ArrayList<QueryPo> sharesArrayList=dao.getSharesDate(sharesIDArrayList, formBegin, endDateString).getSharesList();
		beginDateString=sharesArrayList.get(0).getDataList().get(averDay+1).date;//因为开始日期是夹在中间的,所以要进行调整
		StrategyPo po=new StrategyPo(beginDateString, endDateString, sharesArrayList, averDay, store, transfer);
		sharesIDArrayList=helper.changeID(sharesIDArrayList);//所有股票ID的集合
		
		Map<String, Double> strategyMap =new HashMap<String, Double>();//策略收益率map
		Map<String,Double> holdMap = new HashMap<>();  //持有期的收益率map
		Map<String,Double> holdTimeMap = new HashMap<>();//各个持有期中的
		Map<String, Double> baseMap =new HashMap<String, Double>();//基准
		String beginDate=po.getBeginDate();
		String endDate=po.getEndDate();
		int averLineDay=po.getAverLineDay();//均线天数，形成期
		int storeNum=po.getStoreNum();//仓内股票数
		int transferDay=po.getTransferDay();//股票的持有期，调仓天数
		double principle =1.0; //本金
		int allDay = po.getSharesList().get(0).getDataList().size();  //总交易日,是数据中的天数，所以没问题
		int turns = allDay/transferDay; //循环的轮数
		int rest = allDay%transferDay;  //最后剩下的天数
		
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
			holdTimeMap.put(holdBegin+"",lastMoney-beginMoney);   //持有期的总收益率
			
			
			beginDate = dateDeal.logDateChange(beginDate, 1);//进行下一次的准备
			formBegin=dateDeal.logDateChange(beginDate, -averDay);
			ArrayList<String> dateList=helper.cutList(sharesArrayList, beginDate);
			if (dateList.size()>averDay) {
				beginDate=dateList.get(averDay);//因为开始日期是夹在中间的,所以要进行调整
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
		strategyRate=principle-1;
		
		if(blockName.contains("自选股")){
			baseMap = momentumCal.calHoldRise(po.getSharesList(), po.getBeginDate(), allDay, 1.0).getRiseDate();  //如果是自选股，按照平均数计算
		}else{
			baseMap = momentumCal.calBlockRise(blockName,po.getBeginDate(), allDay);  //如果是板块，使用历史数据计算
		}
		strategyMap = sortHelper.sortByDate(strategyMap);
		baseMap = sortHelper.sortByDate(baseMap); //按照日期排序
		baseMap=sortHelper.subMapSame(baseMap, strategyMap);
		strategyMap=sortHelper.subMapSame(strategyMap, baseMap);
		
		double baseRise = baseMap.get(sortHelper.getLastObject(baseMap));  //基准最后的价格
		double yearRate = momentumHelper.yearValueRate(principle, 1.0, allDay);  //年化收益率
		double baseYearRate = momentumHelper.yearValueRate(baseRise, 1.0, allDay); //基准年化收益
		double maxReturn = momentumCal.findReturnMax(strategyMap);  //最大回撤
		ArrayList<Double> stockRise = sortHelper.mapToList(strategyMap);  //转为list形式
		ArrayList<Double> blockRise = sortHelper.mapToList(baseMap);
		stockRise = sortHelper.acculToNorm(stockRise);  //转为当日收益率
		blockRise = sortHelper.acculToNorm(blockRise);
		double betaRate = momentumHelper.betaRate(stockRise, blockRise);      //贝塔比率
		double alphaRate = momentumHelper.alphaRate(stockRise, blockRise);    //阿尔法比率
		double shapeRate = momentumHelper.shapeRate(stockRise);   //夏普比率
		String lastDate=sortHelper.getLastObject(strategyMap);
		
		
		//OutRiseData outRiseData = calOutRise(holdMap, strategyMap);
		//Map<String,Double> outRiseMap = outRiseData.getRiseDate();
		//double winRate = outRiseData.getWinRate();
		AverStrategyVo vo =new AverStrategyVo(strategyMap, baseMap,holdTimeMap, alphaRate, betaRate, shapeRate, maxReturn, yearRate, baseYearRate,strategyRate);
		vo.setAllDays(allDay);
		System.out.println("已经返回");
		return vo;
	}
	
	
}
