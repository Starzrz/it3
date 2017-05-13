package BL.strategyBL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import BL.averStrategyBL.AverStrategyBLImpl;
import BL.blhelper.DateDeal;
import BL.blhelper.MathHelper;
import BL.blhelper.SortHelper;
import BL.blockBL.BlockBLImpl;
import BL.blservice.MomentumBL;
import BL.blservice.SharesAverStrategyBL;
import BL.blservice.SharesBlockBL;
import BL.blservice.SharesListBL;
import BL.blservice.StrategyBL;
import BL.momentumBL.MomentumBLImpl;
import server.dao.StrategyDao;
import server.dataImpl.StrategyDaoImpl;
import vopo.AverStrategyVo;
import vopo.BackTestPo;
import vopo.BackTestVo;
import vopo.QueryPo;
import vopo.StrategyData;
import vopo.StrategyPo;
import vopo.StrategyType;
import vopo.StrategyVo;

public class StrategyBLImpl implements StrategyBL{
	final String blockName = "主板";
	ArrayList<String> sharesList;
	public StrategyBLImpl() {
		SharesBlockBL bl = new BlockBLImpl();
		SortHelper helper = new SortHelper();
		sharesList = helper.mapKeyToArray(bl.getSharesList(blockName));
		// TODO Auto-generated constructor stub
	}

	@Override
	public StrategyVo getStrategyData(StrategyType type, boolean isHold, int day,String beginDate,String endDate) {
		Map<Integer, Double> outRiseMap = new HashMap<>();//超额胜率
		Map<Integer, Double> winRateMap = new HashMap<>();//策略胜率
		Map<Integer, Double> riseSize = new HashMap<>(); //保存指定天数对应数据的个数，用来做平均数
		SortHelper sortHelper = new SortHelper();
		if (type==StrategyType.average) {//均值回归
			SharesAverStrategyBL averStrategyBL=new AverStrategyBLImpl();
			MomentumBL bl = new MomentumBLImpl();
			StrategyDao dao=new StrategyDaoImpl();
			if(isHold){//持有期固定,day为持有期
				ArrayList<QueryPo> list=dao.getSharesDate(sharesList, beginDate, endDate).getSharesList();
				for(int i=5;i<=20;i+=5){
					AverStrategyVo vo=averStrategyBL.averStrategy(beginDate, endDate, sharesList, i, sharesList.size()/5, day, "主板");
					double strateRate=vo.getStrategyRate();
					double baseRate=bl.calBaseRise(list, vo.getAllDays(), beginDate, blockName)
							.get(sortHelper.getLastObject(bl.calBaseRise(list, vo.getAllDays(), beginDate, blockName)));
					double outRise=strateRate-baseRate;
					Map<String, Double>  holdWinRateMap=vo.getHoldMap();
					int index=0;
					for (String key : holdWinRateMap.keySet()) {
						if (holdWinRateMap.get(key)>0) {
							index++;
						}
					}
					double winRate=(index+0.0)/holdWinRateMap.size();
					outRiseMap.put(i, outRise);
					winRateMap.put(i,winRate);
				}
			}else{//形成期固定
				ArrayList<QueryPo> list=dao.getSharesDate(sharesList, beginDate, endDate).getSharesList();
				for(int i=2;i<=40;i+=2){
					AverStrategyVo vo=averStrategyBL.averStrategy(beginDate, endDate, sharesList, day, sharesList.size()/5, i, "主板");
					double strateRate=vo.getStrategyRate();
					
					double baseRate=bl.calBaseRise(list, vo.getAllDays(), beginDate, blockName)
							.get(sortHelper.getLastObject(bl.calBaseRise(list, vo.getAllDays(), beginDate, blockName)));
					double outRise=strateRate-baseRate;
					Map<String, Double>  holdWinRateMap=vo.getHoldMap();
					int index=0;
					for (String key : holdWinRateMap.keySet()) {
						if (holdWinRateMap.get(key)>0) {
							index++;
						}
					}
					double winRate=(index+0.0)/holdWinRateMap.size();
					outRiseMap.put(i,outRise);
					winRateMap.put(i,winRate);
				}
			}
		}else{//
			MomentumBL bl = new MomentumBLImpl();
			StrategyDao dao = new StrategyDaoImpl();
			DateDeal dateDeal = new DateDeal();
			String formBegin = dateDeal.logDateChange(beginDate, -20);
			
			StrategyPo po =dao.getSharesDate(sharesList, formBegin, endDate);
			Map<String, Double> allBaseList = new HashMap<>(); //整个区间的基准收益率
			allBaseList = bl.calBaseRise(po.getSharesList(), po.getSharesList().get(0).getDataList().size(), beginDate, blockName);
			ArrayList<StrategyData> dataList = new ArrayList<>();
			if(isHold){
				for(int i=2;i<=40;i+=2){
					BackTestVo tempVo = bl.calMomentumResult(po,allBaseList, beginDate, endDate, i, day, sharesList, blockName);
					StrategyData tempData = tempVo.getStrategyData();
					dataList.add(tempData);
				}
			}
			else{
				for(int i=2;i<=40;i+=2){
					BackTestVo tempVo = bl.calMomentumResult(po, allBaseList,beginDate, endDate, day, i, sharesList, blockName);
					StrategyData tempData = tempVo.getStrategyData();
					dataList.add(tempData);
				}
			}
			for(StrategyData temp:dataList){
				int tempDay =0;
				if(isHold){
					tempDay = temp.getFormDay();
				}
				else{
					tempDay = temp.getHoldDay();
				}
				
					outRiseMap.put(tempDay, temp.getOutRise());
					winRateMap.put(tempDay, temp.getWinRate());
					riseSize.put(tempDay, 1.0);
				
			}
			
		}
		StrategyVo vo = new StrategyVo(type, day, outRiseMap, winRateMap, isHold);
		return vo;
	}

}