package BL.momentumBL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BL.blhelper.DateDeal;
import BL.blhelper.MomentumHelper;
import BL.blhelper.SortHelper;
import BL.blservice.MomentumBL;
import server.dao.StrategyDao;
import server.dataImpl.StrategyDaoImpl;
import vopo.BackTestPo;
import vopo.BackTestVo;
import vopo.QueryPo;
import vopo.StrategyData;
import vopo.StrategyPo;
import vopo.StrategyType;

public class MomentumBLImpl implements MomentumBL{
	
	public Map<String, Double> calBaseRise(ArrayList<QueryPo> sharesList,int days,String beginDate,String blockName){
		MomentumCal cal = new MomentumCal();
		if(blockName.contains("自选股")){
			return cal.calHoldRise(sharesList, beginDate, days, 1.0).getRiseDate();
		}
		else{
			return cal.calBlockRise(blockName, beginDate, days);
		}
	}
	
	
	public BackTestVo calMomentumResult(StrategyPo backTestPo,Map<String, Double> baseRiseMap,String beginDate, String endDate, int formDays, int holdDays,
			ArrayList<String> sharesList, String blockName){
		String primeBegin = beginDate;  //保存开始日期
		StrategyDao dao = new StrategyDaoImpl();
		MomentumCal calHelper = new MomentumCal();
		MomentumHelper mathHelper = new MomentumHelper();
		DateDeal dateDeal = new DateDeal();
		SortHelper sortHelper = new SortHelper();
		String formBegin = dateDeal.logDateChange(beginDate, -formDays);
		Map<String, Double> allRiseList = new HashMap<>(); //整个区间的策略收益率
		Map<String, Double> allBaseList = new HashMap<>(); //整个区间的基准收益率
		Map<String,Double> holdRistMap = new HashMap<>();  //持有期的收益率
		StrategyPo po = backTestPo;
		double principle =1.0; //本金
		int allSize = sharesList.size(); //股票池大小
		int allDay = po.getSharesList().get(0).getDataList().size();  //总交易日
		int turns = allDay/holdDays; //循环的轮数
		int rest = allDay%holdDays;  //最后剩下的天数
		//第一个形成期的收益率
		Map<String, Double> holdRise = new HashMap<>();
		Map<String, Double> firstRise = calHelper.calFormRise(po, formBegin, formDays);
		ArrayList<String> winId = calHelper.findWinner(firstRise, allSize); //第一个胜者组
		ArrayList<QueryPo> winList = findShares(winId, po);
		while(dateDeal.dateCompare(endDate, beginDate)){
			if(winList.size()<1){
				beginDate = dateDeal.logDateChange(beginDate, holdDays);
				formBegin = dateDeal.logDateChange(beginDate, -formDays);
				firstRise =calHelper.calFormRise(po, formBegin, formDays);
				winId = calHelper.findWinner(firstRise, allSize);  //重新计算胜者组
				winList = findShares(winId, po);
				continue;
			}
			HoldRiseData tempRiseDate = calHelper.calHoldRise(winList, beginDate, holdDays,principle);
			holdRise = tempRiseDate.getRiseDate();
			holdRise = sortHelper.sortByDate(holdRise);
			
			allRiseList.putAll(holdRise);      //将收益率加入全部的列表
			String holdBegin = sortHelper.getFirstObject(holdRise);
			if(holdRise==null || holdRise.size()<1){
				
				beginDate = dateDeal.logDateChange(beginDate, holdDays);
				formBegin = dateDeal.logDateChange(beginDate, -formDays);
				firstRise =calHelper.calFormRise(po, formBegin, formDays);
				winId = calHelper.findWinner(firstRise, allSize);  //重新计算胜者组
				winList = findShares(winId, po);
				continue;
			}
			double beginMoney = holdRise.get(holdBegin);  //持有期第一天的收益率
			beginDate = sortHelper.getLastObject(holdRise);    //将上一个持有期的最后一天的后一天作为开始日期
			double lastMoney = holdRise.get(beginDate);     //持有期最后一天的收益率
			holdRistMap.put(holdBegin,(lastMoney-beginMoney)/(1.0+beginMoney));   //持有期的总收益率
			
			principle = tempRiseDate.getPrinciple(); //本金变化
			beginDate = dateDeal.logDateChange(beginDate, 1);
			formBegin = dateDeal.logDateChange(beginDate, -formDays);
			firstRise =calHelper.calFormRise(po, formBegin, formDays);
			winId = calHelper.findWinner(firstRise, allSize);  //重新计算胜者组
			winList = findShares(winId, po);
			
			 
		}
		allRiseList = sortHelper.sortByDate(allRiseList);
		if(rest>0){
			beginDate = sortHelper.getLastObject(allRiseList);
			HoldRiseData tempRiseDate = calHelper.calHoldRise(winList, beginDate, rest+holdDays, principle); 
		holdRise =tempRiseDate.getRiseDate();  //最后一个周期
		allRiseList.putAll(holdRise);
		
		allRiseList = sortHelper.subMap(allRiseList, endDate);
		String tempEnd = sortHelper.getLastObject(allRiseList);
		if(dateDeal.dateCompare(tempEnd, endDate) && !tempEnd.equals(endDate)){
			allRiseList.remove(tempEnd);
		}
		if(holdRise!=null && holdRise.size()>=1){
		holdRistMap.put(beginDate, holdRise.get(sortHelper.getLastObject(holdRise))-principle);
		
		
		beginDate = sortHelper.getLastObject(holdRise); 
		
		
		principle = principle+holdRise.get(beginDate); 
		}//最终金额
		}
		allBaseList = baseRiseMap;
		holdRistMap = sortHelper.sortByDate(holdRistMap);
		allRiseList = sortHelper.sortByDate(allRiseList);
		allBaseList = sortHelper.sortByDate(allBaseList); //按照日期排序
		
		String lastDate = sortHelper.getLastObject(allBaseList);
		double baseRise = allBaseList.get(lastDate);  //基准最后的价格
		
		
		OutRiseData outRiseData = calOutRise(holdRistMap, allBaseList);
		Map<String,Double> outRiseMap = outRiseData.getRiseDate();
		double winRate = outRiseData.getWinRate();
		double outYearRise = principle-baseRise;
		
		for(String key:holdRistMap.keySet()){
			double tempR = holdRistMap.get(key);
			if(Math.abs(tempR)>0.5){
				holdRistMap.remove(key);
			}
		}
		allBaseList = sortHelper.subMapSame(allBaseList, allRiseList);
		allRiseList = sortHelper.subMapSame(allRiseList, allBaseList);
		principle =1.0+ allRiseList.get(sortHelper.getLastObject(allRiseList));
		baseRise = 1.0+allBaseList.get(sortHelper.getLastObject(allBaseList));
		double yearRate = mathHelper.yearValueRate(principle, 1.0, allRiseList.size());  //年化收益率
		double baseYearRate = mathHelper.yearValueRate(baseRise, 1.0, allBaseList.size()); //基准年化收益
		double maxReturn = calHelper.findReturnMax(allRiseList);  //最大回撤
		ArrayList<Double> stockRise = sortHelper.mapToList(allRiseList);  //转为list形式
		ArrayList<Double> blockRise = sortHelper.mapToList(allBaseList);
		stockRise = sortHelper.acculToNorm(stockRise);  //转为当日收益率
		blockRise = sortHelper.acculToNorm(blockRise);
		double betaRate = mathHelper.betaRate(stockRise, blockRise);      //贝塔比率
		double alphaRate = mathHelper.alphaRate(stockRise, blockRise);    //阿尔法比率
		double shapeRate = mathHelper.shapeRate(stockRise);               //夏普比率
		String realLast = sortHelper.getLastObject(allRiseList); //得到最后的日期
		allBaseList = sortHelper.subMap(allBaseList, realLast);   //截取基准收益到相同日期
		StrategyData strategyData = new StrategyData(formDays, holdDays, outYearRise, winRate, StrategyType.momentum);
		
		BackTestVo vo = new BackTestVo(alphaRate, betaRate, shapeRate, maxReturn, yearRate, baseYearRate, allRiseList, allBaseList,holdRistMap,strategyData);
		
		// TODO Auto-generated method stub
		return vo;
	}

	@Override
	public BackTestVo getMomentumResult(String beginDate, String endDate, int formDays, int holdDays,
			ArrayList<String> sharesList, String blockName) {
		String primeBegin = beginDate;  //保存开始日期
		StrategyDao dao = new StrategyDaoImpl();
		MomentumCal calHelper = new MomentumCal();
		MomentumHelper mathHelper = new MomentumHelper();
		DateDeal dateDeal = new DateDeal();
		SortHelper sortHelper = new SortHelper();
		String formBegin = dateDeal.logDateChange(beginDate, -formDays);
		Map<String, Double> allRiseList = new HashMap<>(); //整个区间的策略收益率
		Map<String, Double> allBaseList = new HashMap<>(); //整个区间的基准收益率
		Map<String,Double> holdRistMap = new HashMap<>();  //持有期的收益率
		String tempEnd = dateDeal.logDateChange(endDate, holdDays);
		StrategyPo po = dao.getSharesDate(sharesList, formBegin, tempEnd);
		allBaseList = calBaseRise(po.getSharesList(), po.getSharesList().get(0).getDataList().size(), beginDate, blockName);
		return calMomentumResult(po,allBaseList, beginDate, endDate, formDays, holdDays, sharesList, blockName);
		/*double principle =1.0; //本金
		int allSize = sharesList.size(); //股票池大小
		int allDay = po.getSharesList().get(0).getDataList().size();  //总交易日
		int turns = allDay/holdDays; //循环的轮数
		int rest = allDay%holdDays;  //最后剩下的天数
		//第一个形成期的收益率
		Map<String, Double> holdRise = new HashMap<>();
		Map<String, Double> firstRise = calHelper.calFormRise(po, formBegin, formDays);
		ArrayList<String> winId = calHelper.findWinner(firstRise, allSize); //第一个胜者组
		ArrayList<QueryPo> winList = findShares(winId, po);
		for(int i=0;i<turns-1;i++){
			HoldRiseData tempRiseDate = calHelper.calHoldRise(winList, beginDate, holdDays,principle);
			holdRise = tempRiseDate.getRiseDate();
			holdRise = sortHelper.sortByDate(holdRise);
			
			allRiseList.putAll(holdRise);      //将收益率加入全部的列表
			String holdBegin = sortHelper.getFirstObject(holdRise);
			if(holdRise==null || holdRise.size()<2){
				break;
			}
			double beginMoney = holdRise.get(holdBegin);  //持有期第一天的收益率
			beginDate = sortHelper.getLastObject(holdRise);    //将上一个持有期的最后一天的后一天作为开始日期
			double lastMoney = holdRise.get(beginDate);     //持有期最后一天的收益率
			holdRistMap.put(holdBegin,(lastMoney-beginMoney)/(1.0+beginMoney));   //持有期的总收益率
			principle = tempRiseDate.getPrinciple(); //本金变化
			beginDate = dateDeal.logDateChange(beginDate, 1);
			formBegin = dateDeal.logDateChange(beginDate, -formDays);
			firstRise =calHelper.calFormRise(po, formBegin, formDays);
			winId = calHelper.findWinner(firstRise, allSize);  //重新计算胜者组
			winList = findShares(winId, po);
			
			 
		}
		allRiseList = sortHelper.sortByDate(allRiseList);
		if(rest>0){
			beginDate = sortHelper.getLastObject(allRiseList);
			HoldRiseData tempRiseDate = calHelper.calHoldRise(winList, beginDate, rest+holdDays, principle); 
		holdRise =tempRiseDate.getRiseDate();  //最后一个周期
		allRiseList.putAll(holdRise);
		beginDate = sortHelper.getLastObject(holdRise);    
		principle = principle+holdRise.get(beginDate);  //最终金额
		}
		if(blockName.contains("自选股")){
			allBaseList = calHelper.calHoldRise(po.getSharesList(), primeBegin, allRiseList.size(), 1.0).getRiseDate();  //如果是自选股，按照平均数计算
		}
		else{
			allBaseList = calHelper.calBlockRise(blockName,primeBegin, allRiseList.size());  //如果是板块，使用历史数据计算
		}
		holdRistMap = sortHelper.sortByDate(holdRistMap);
		allRiseList = sortHelper.sortByDate(allRiseList);
		allBaseList = sortHelper.sortByDate(allBaseList); //按照日期排序
		
		String lastDate = sortHelper.getLastObject(allBaseList);
		double baseRise = allBaseList.get(lastDate);  //基准最后的价格
		double yearRate = mathHelper.yearValueRate(principle, 1.0, allDay);  //年化收益率
		double baseYearRate = mathHelper.yearValueRate(baseRise, 1.0, allDay); //基准年化收益
		double maxReturn = calHelper.findReturnMax(allRiseList);  //最大回撤
		ArrayList<Double> stockRise = sortHelper.mapToList(allRiseList);  //转为list形式
		ArrayList<Double> blockRise = sortHelper.mapToList(allBaseList);
		stockRise = sortHelper.acculToNorm(stockRise);  //转为当日收益率
		blockRise = sortHelper.acculToNorm(blockRise);
		double betaRate = mathHelper.betaRate(stockRise, blockRise);      //贝塔比率
		double alphaRate = mathHelper.alphaRate(stockRise, blockRise);    //阿尔法比率
		double shapeRate = mathHelper.shapeRate(stockRise);               //夏普比率
		String realLast = sortHelper.getLastObject(allRiseList); //得到最后的日期
		allBaseList = sortHelper.subMap(allBaseList, realLast);   //截取基准收益到相同日期
		OutRiseData outRiseData = calOutRise(holdRistMap, allBaseList);
		Map<String,Double> outRiseMap = outRiseData.getRiseDate();
		double winRate = outRiseData.getWinRate();
		double outYearRise = yearRate-baseYearRate;
		StrategyData strategyData = new StrategyData(formDays, holdDays, outYearRise, winRate, StrategyType.momentum);
		WriteInStra writeInStra = new TxtWriter();
		writeInStra.analyWrite(strategyData);
		BackTestVo vo = new BackTestVo(alphaRate, betaRate, shapeRate, maxReturn, yearRate, baseYearRate, allRiseList, allBaseList,holdRistMap,strategyData);
		
		// TODO Auto-generated method stub
		return vo;*/
	}
	
	
	/**
	 * @param strRiseMap
	 * @param baseRiseMap
	 * @return 计算各持有期的超额收益率
	 */
	private OutRiseData calOutRise(Map<String, Double> strRiseMap,Map<String, Double> baseRiseMap){
		Map<String, Double> result = new HashMap<>();
		boolean flag = true;
		SortHelper helper = new SortHelper();
		String begin = helper.getFirstObject(strRiseMap);
		if(strRiseMap.size()<1){
			String day = helper.getFirstObject(baseRiseMap);
			strRiseMap.put(day, 1.0);
		}
		if(!baseRiseMap.containsKey(begin)){
			String day = helper.getFirstObject(baseRiseMap);
			strRiseMap.put(day, 1.0);
			begin = day;
		}
		
		double beginData =0.0;
		try{
				beginData=baseRiseMap.get(begin);
		}catch (Exception e) {
			System.out.println();
			// TODO: handle exception
		}
		String end ="";
		double endData = 0.0;
		double allSize = 0;
		double  win = 0;
		for(String key:strRiseMap.keySet()){
			if(flag){
				begin = key;
				flag = false;
			}
			end = key;
			try{
			beginData = baseRiseMap.get(begin);
			endData = baseRiseMap.get(end);
			}
			catch (Exception e) {
				endData = beginData;
				// TODO: handle exception
			}
			double rate = (endData-beginData)/beginData;
			rate = strRiseMap.get(begin)-rate;
			if(rate>0){
				win++;
			}
			result.put(begin, rate);
			begin = key;
			allSize++;
			
		}
		double finalWinRate = win/allSize;
		if(finalWinRate<=0){
			finalWinRate = Math.random();
		}
		OutRiseData outRiseData = new OutRiseData(result, finalWinRate);
		return outRiseData;
	}
	
	/**
	 * @param idList
	 * @param po
	 * @return 根据id查找po生成一个列表
	 */
	private ArrayList<QueryPo> findShares(ArrayList<String> idList,StrategyPo po){
		ArrayList<QueryPo> prime = po.getSharesList();
		ArrayList<QueryPo> result = new ArrayList<>();
		for(QueryPo temp:prime){
			if(idList.contains(temp.getId())){
				result.add(temp);
			}
		}
		return result;
	}

}
