package BL.momentumBL;

import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



import BL.blhelper.DateDeal;
import BL.blhelper.MomentumHelper;
import BL.blhelper.SharesCal;
import BL.blhelper.SortHelper;
import server.dao.SharesDao;
import server.dataImpl.SharesDaoImpl;
import vopo.BlockPo;
import vopo.BackTestPo;
import vopo.QueryPo;
import vopo.SharesData;
import vopo.StrategyPo;

/**
 * @author 朱润之
 * 动量策略的各种计算实现
 */
public class MomentumCal {
	
	/**
	 * @param po
	 * @param beginDate
	 * @param days
	 * @return 计算形成期的收益率
	 */
	public Map<String, Double> calFormRise(StrategyPo po,String beginDate,int days){
		MomentumHelper helper = new MomentumHelper();
		ArrayList<QueryPo> shareList = po.getSharesList();
		Map<String, Double> result = new HashMap<>();
		for(QueryPo temp:shareList){
			
			try{
			if(!helper.hasClose(temp, beginDate, days)){  //判断有无停牌情况，如果有，直接跳过
				continue; 
			}
			
			ArrayList<SharesData> calDate = helper.getPointData(temp.getDataList(), beginDate, days); //得到形成期内的股票历史数据
			double lastPrice = calDate.get(calDate.size()-1).adjClose; //最后一日的复权收盘价
			double firstPrice = calDate.get(0).lastAdj;  //第一日前一日的复权收盘价
			double rise = (lastPrice-firstPrice)/firstPrice; //收益率
			
			result.put(temp.getId(),rise);}
			catch (Exception e) {
				System.out.println(temp.getId());
				// TODO: handle exception
			}
		}
		return result;
	}
	
	/**
	 * @param riseList
	 * @return 寻找最大回撤
	 */
	public double findReturnMax(Map<String, Double> riseList){
		boolean flag = true;
		String nowDate="";
		String nextDate = "";
		double maxDrop = 0.0;
		for(String key:riseList.keySet()){
			nextDate = key;
			if(flag){
				flag = false;
				nowDate = key;
				continue;
			}
			double rise1 = riseList.get(nowDate);
			double rise2 = riseList.get(nextDate);
			double dist = rise2-rise1;
			if(dist<maxDrop){
				maxDrop = dist;
			}
			nowDate = key;
		}
		return maxDrop;
	}
	
	/**
	 * @param principle 本金
	 * @param winList
	 * @param beginDate
	 * @param days
	 * @return 计算持有期内的策略收益率，返回的map，string对应日期，double对应收益率
	 */
	public HoldRiseData calHoldRise(ArrayList<QueryPo> winList,String beginDate,int days,double principle){
		int size = winList.size();
		
		SharesCal cal = new SharesCal();
		MomentumHelper helper = new MomentumHelper();
		QueryPo tempPo =winList.get(0);
		Map<String, Double> result = new HashMap<>();
		ArrayList<SharesData> datas =helper.getPointData(tempPo.getDataList(), beginDate, days+2); //得到日期的列表
		for(int i=0;i<datas.size();i++){
			String date = datas.get(i).date;
			double rate = 0.0;
			ArrayList<SharesData> tempData = new ArrayList<>();
			if(date.equals("7/16/13")){
				rate = 0.0;
			}
			for(QueryPo usePo:winList){  //遍历股票
				tempData = helper.getPointData(usePo.getDataList(), beginDate, days+2);
				if(tempData==null){
					continue;
				}
				if(tempData.size()<=i){
					size--;
				}
				else{
				double rise = cal.changeVal(tempData.get(i));  //取每只股票该日期的涨跌幅
				rate = rate+rise;
				if(rate>10){
					rate = rate-5;
				}
				}
				
			}
			if(size==0){
				size=1;
			}
			rate = rate/(size+0.0); //取平均数
			
			principle = principle*(1+rate);
			
			if(Math.abs(rate)>1){
				rate++;
				rate--;
			}
			double accumRate = principle-1.0;
			//System.out.println(accumRate+" "+rate);
			result.put(date, accumRate);
			if(result.size()==days){
				break;
			}
		}
		HoldRiseData data = new HoldRiseData(result, principle);
		return data;
	}
	
	/**
	 * @param blockName
	 * @param beginDate
	 * @param days
	 * @return 计算板块的收益率，即基准收益率
	 */
	public Map<String, Double> calBlockRise(String blockName,String beginDate,int days){
		DateDeal dateDeal = new DateDeal();
		SharesDao dao = new SharesDaoImpl();
		BlockPo po = dao.getBlockData(blockName);
		SortHelper helper = new SortHelper();
		Map<String, Double> riseList = po.getRiseList();
		Map<String, Double> result = new HashMap<>();
		double principle = 1.0; //本金
		for(String key:riseList.keySet()){
			if(!dateDeal.dateCompare(key, beginDate)){
				continue;
			}
			principle = principle*(1.0+riseList.get(key)); //当日涨跌幅算出总价
			result.put(key, principle-1.0);  //累计收益率
			if(result.size()==days){
				break;
			}
			
		}
		result = helper.sortByDate(result);
		return result;
	}
	
	/**
	 * @param riseList
	 * @return 根据收益率找出胜者组
	 */
	public ArrayList<String> findWinner(Map<String, Double> riseList,int size){
		ArrayList<String> result = new ArrayList<>();
		SortHelper helper = new SortHelper();
		riseList = helper.sortByRise(riseList); //按照收益率排序
		int winSize = 0;
		int totalSize=size;
		//确定分组的大小
		if(totalSize%5==0){
			winSize = totalSize/5;
		}
		else{
			winSize = totalSize/4;
		}
		
		for(String key:riseList.keySet()){
			result.add(key);
			if(result.size()==winSize){
				break;
			}
		}
		return result;
	}
	

}
