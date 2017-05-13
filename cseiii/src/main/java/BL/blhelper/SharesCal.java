package BL.blhelper;

import java.util.ArrayList;

import vopo.SharesData;



public class SharesCal {
	
	
	/**
	 * @param id
	 * @return 判断股票属于哪个版块，主板返回0，中小板返回1，创业板返回2,错误返回3
	 */
	public int whichBrench(String id){
		String headId = id.substring(0,3);
		if(headId.equals("600") || headId.equals("601") || headId.equals("000") || headId.equals("001")){
			return 0;
		}
		else if(headId.equals("002")){
			return 1;
		}
		else if(headId.equals("300")){
			return 2;
		}
		else{
			return 3;
		}
	}
	/**
	 * @param id
	 * @return 将id补全为6位
	 */
	public String dealId(String id){
		try{
		int x = Integer.valueOf(id);
		}
		catch (Exception e) {
			return id;
			// TODO: handle exception
		}
		int n = id.length();
		for(int i=0;i<6-n;i++){
			id = '0'+id;
		}
		return id;
	}
	/**
	 * @param data
	 * 计算对数收益率
	 */
	public double logVol(SharesData data){
		double close = data.adjClose;
		double last = data.lastAdj;
		return Math.log(close/last);
	}
	/**
	 * @param datas
	 * @return 计算方差
	 */
	public double variance(ArrayList<Double> datas){
		double n = datas.size()+0.0;
		double result1=0;
		double result2=0;
		for(double tempData:datas){
			result1 = result1+tempData*tempData;
			result2 = result2+tempData;
		}
		result1 = result1/n;
		result2 = result2*result2;
		result2 = result2/(n*n);
		return result1-result2;
		
	}
	/**
	 * @param data
	 * @return 计算股票当日的涨跌
	 */
	public double changeVal(SharesData data){
		double rate = 0;
		double change = data.adjClose-data.lastAdj;
		rate = change/data.lastAdj;
		//rate = 1.0/(100.0*data.lastAdj)+rate;
		if(Math.abs(rate)>0.3){
			if(rate>0){
				rate = 0.11;
			}
			else{
				rate = -0.11;
			}
			
		}
		return rate;
	}

}
