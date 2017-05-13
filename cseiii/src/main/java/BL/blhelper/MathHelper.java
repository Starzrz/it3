package BL.blhelper;

import java.util.ArrayList;

public class MathHelper {
	
	
	
	/**
	 * @param num
	 * @return 控制double的位数，精确到小数点之后4位
	 */
	public double subDouble(double num){
		num = num*10000;
		int temp =(int) num;
		double tempD = temp+0.0;
		return tempD/10000.0;
	}
	/**
	 * @param list
	 * @return 计算列表平均数
	 */
	public double average(ArrayList<Double> list){
		double sum = 0;
		for(double num:list){
			sum = sum+num;
		}
		return sum/list.size();
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
	

}
