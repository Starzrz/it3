package BL.blhelper;

import java.util.ArrayList;


import vopo.QueryPo;
import vopo.SharesData;



/**
 * @author 朱润之
 * 计算策略的相关比率数据包括：
 * 年化收益率，最大回撤，阿尔法，贝塔，夏普比率，超额收益率
 */
/**
 * @author 朱润之
 *
 */
public class MomentumHelper {
	final double rf = 0.0175;
	MathHelper mathHelper ;
	DateDeal dateDeal;
	public MomentumHelper() {
		mathHelper = new MathHelper();
		dateDeal = new DateDeal();
	}
		// TODO Auto-generated constructor stub
	
	/**
	 * @param po
	 * @param beginDate
	 * @param endDate
	 * @return 判断股票在该期间有没有停牌情况,true为无停牌
	 */
	public boolean hasClose(QueryPo po,String beginDate,int days){
		ArrayList<SharesData> datas = po.getDataList();
		ArrayList<SharesData> dealData = getPointData(datas, beginDate, days);
		if (datas.size()==0) {
			return false;
		}
		for(SharesData data:dealData){
			int count = 3;  //停牌可接受日期
			if(data.closePrice==0 ||data.volume==0){
				count--;
			}
			if(count==0){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param datas
	 * @param beginDate
	 * @param endDate
	 * @return 得到指定日期内的数据
	 */
	public ArrayList<SharesData> getPointData(ArrayList<SharesData> datas,String beginDate,int days){
		ArrayList<SharesData> result = new ArrayList<>();
		if (datas.size()==1) {
			return datas;
		}
		if (datas.size()==0) {
			return null;
		}
		String lastDate = datas.get(0).date;
		
		
		String nextDate = datas.get(1).date;
		for(int i=0;i<datas.size();i++){
			SharesData temp = datas.get(i);
			//读取两个日期
			lastDate = temp.date;
			if(i<datas.size()-1)
			nextDate = datas.get(i+1).date;
			
			//判断开始日期是否在读取的两个日期之间或者等于读取的日期
			if((dateDeal.dateCompare(beginDate, lastDate) && dateDeal.dateCompare(nextDate, beginDate)) || nextDate.equals(beginDate) ||dateDeal.dateCompare(lastDate, beginDate)){
				if(i==datas.size()-1){
					i--;
				}
				for(int j=i+1;j<datas.size();j++){ //开始读取数据
					SharesData readData = datas.get(j);
					result.add(readData);
					if(result.size()==days){
						break;
					}
					
				}
				break;
			}
		}
		return result;
	}
	
	/**
	 * @param nowVal 目前持有的股票价值
	 * @param beginVal	本金
	 * @param days	持有时长
	 * 
	 * @return 年化收益率
	 */
	public double yearValueRate(double nowVal,double beginVal,int days){
		double earn = nowVal-beginVal;
		double earnRate = (earn/beginVal);
		double day = days+0.0;
		return (earnRate/day)*365;
	}
	
	/**
	 * @param stockRise 每一日的涨跌
	 * @return 年化收益率
	 */
	public double yearStockRate(ArrayList<Double> stockRise){
		double nowVal = 1.0;
		for(Double rise:stockRise){
			nowVal= nowVal*(1+rise);
		}
		Double days = stockRise.size()+0.0;
		nowVal = nowVal-1.0;
		return (nowVal/days)*365.0;
	}
	
	/**
	 * @param stockRise
	 * @param brenchRise
	 * @return 超额回报率
	 */
	public ArrayList<Double> riseRate(ArrayList<Double> stockRise,double brenchRise){
		ArrayList<Double> riseList = new ArrayList<>();
		for(double stock:stockRise){
			riseList.add(stock-brenchRise);
		}
		return riseList;
	}
	/**
	 * @param stockRise 所选组合的涨跌幅
	 * @param brenchRise 所选板块的涨跌幅
	 * @return 贝塔比率
	 */
	public double betaRate(ArrayList<Double> stockRise,ArrayList<Double> brenchRise){
		ArrayList<Double> stoAndBre = new ArrayList<>();
		for(int i=0;i<stockRise.size();i++){
			stoAndBre.add(stockRise.get(i)*brenchRise.get(i));
			
		}
		double cov = mathHelper.average(stoAndBre)-mathHelper.average(stockRise)*mathHelper.average(brenchRise);
		double varM = mathHelper.variance(brenchRise);
		cov = cov/varM;
		return cov;
	}
	
	
	/**
	 * @param stockRise
	 * @param brenchRise
	 * @return 阿尔法比率
	 */
	public double alphaRate(ArrayList<Double> stockRise,ArrayList<Double> brenchRise){
		double stockR = yearStockRate(stockRise);
		double brenchR = yearStockRate(brenchRise);
		double num1 = stockR - rf;
		double num2 = brenchR - rf;
		double beta = betaRate(stockRise, brenchRise);
		return num1 - beta*num2;
	}
	
	/**
	 * @param stockRise
	 * @param brenchRise
	 * @return 夏普比率
	 */
	public double shapeRate(ArrayList<Double> stockRise){
		double erp =yearStockRate(stockRise);
		double sigmaEx = Math.sqrt(mathHelper.variance(stockRise));
		return (erp-rf)/sigmaEx;
	}
	
}
	
	
