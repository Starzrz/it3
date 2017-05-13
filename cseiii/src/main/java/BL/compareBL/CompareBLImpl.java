package BL.compareBL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BL.blhelper.DateDeal;
import BL.blhelper.SortHelper;
import BL.blhelper.SharesCal;
import BL.blservice.SharesCompareBL;
import server.dao.SharesDao;
import server.dataImpl.SharesDaoImpl;
import vopo.ComparePo;
import vopo.CompareVo;
import vopo.SharesData;

/**
 * @author 朱润之
 * 比较数据处理的逻辑层
 */
public class CompareBLImpl implements SharesCompareBL{
	@Override
	public CompareVo[] compareSh(String id1,String id2,String beginDate,String endDate){
		SortHelper helper = new SortHelper();
		SharesDao dao = new SharesDaoImpl();
		DateDeal dateDeal = new DateDeal();
		beginDate = dateDeal.preToLog(beginDate);
		endDate = dateDeal.preToLog(endDate);
		ComparePo data1 = dao.findCompare(id1, beginDate, endDate);
		ComparePo data2 = dao.findCompare(id2, beginDate, endDate);
		CompareVo vo1  = po2vo(data1);
		CompareVo vo2 = po2vo(data2);
		Map<String,Double> dataList1 = vo1.getClosePrice();
		Map<String,Double> dataList2 = vo2.getClosePrice();
		dataList1 = helper.subMapSame(dataList1, dataList2);
		dataList2 = helper.subMapSame(dataList2, dataList1);
		vo1.setClosePrice(dataList1);
		vo2.setClosePrice(dataList2);
		dataList1 = vo1.getLogReturn();
		dataList2 = vo2.getLogReturn();
		dataList1 = helper.subMapSame(dataList1, dataList2);
		dataList2 = helper.subMapSame(dataList2, dataList1);
		vo1.setLogReturn(dataList1);
		vo2.setLogReturn(dataList2);
		CompareVo[] result = {vo1,vo2};
		return result;
		
		
	}
	
	/**
	 * 毕潇晗
	 * @param id1
	 * @param id2
	 * @param beginDate
	 * @param endDate
	 * @return  返回一段时间内比较两股票的最高值、最低值，用二维数组表示[0][0]为股票1最高价[0][1]为最低[1][0]以此类推
	 */
	@Override
	public double[][] minAndMax(String id1,String id2,String beginDate,String endDate) {
		SharesDao dao = new SharesDaoImpl();
		DateDeal dateDeal = new DateDeal();
		beginDate = dateDeal.preToLog(beginDate);
		endDate = dateDeal.preToLog(endDate);
		ComparePo data1 = dao.findCompare(id1, beginDate, endDate);
		ComparePo data2 = dao.findCompare(id2, beginDate, endDate);
		CompareVo vo1  = po2vo(data1);
		CompareVo vo2 = po2vo(data2);
		double[][] minax = {{0.0,100.0},{0.0,100.0}};
		for (Map.Entry<String, Double> entry : vo1.getMaxPrice().entrySet()) {
			   if(minax[0][0]<entry.getValue())
				   minax[0][0]=entry.getValue();
		}
		for (Map.Entry<String, Double> entry : vo1.getMinPrice().entrySet()) {
			   if(minax[0][1]>entry.getValue())
				   minax[0][1]=entry.getValue();
		}
		for (Map.Entry<String, Double> entry : vo2.getMaxPrice().entrySet()) {
			   if(minax[1][0]<entry.getValue())
				   minax[1][0]=entry.getValue();
		}
		for (Map.Entry<String, Double> entry : vo2.getMinPrice().entrySet()) {
			   if(minax[1][1]>entry.getValue())
				   minax[1][1]=entry.getValue();
		}
		
		return minax;
	}
	
	public CompareVo po2vo(ComparePo po){
		if(po==null){
			return null;
		}
		SharesCal cal = new SharesCal();
		ArrayList<SharesData> dataList = po.getDatas();
		String stockName = po.getStockName();
		String stockNum = po.getStockNum();
		String beginDate = po.getBeginDate();
		String endDate = po.getEndDate();
		Map<String, Double> minPrice = new HashMap<>();
		
		Map<String, Double> maxPrice =new HashMap<>();
		Map<String, Double> openPrice = new HashMap<>();
		Map<String, Double> closePrice = new HashMap<>();
		
		double increase =0.0;
		double lastDate = dataList.get(0).adjClose;
		double firstDate = dataList.get(dataList.size()-1).lastAdj;
		increase = (lastDate-firstDate)/firstDate;
		Map<String, Double> logReturn = new HashMap<>();
		ArrayList<Double> logList = new ArrayList<>();
		for(SharesData tempData:dataList){
			String date = tempData.date;
			minPrice.put(date, tempData.minPrice);
			maxPrice.put(date, tempData.maxPrice);
			openPrice.put(date, tempData.openPrice);
			closePrice.put(date, tempData.closePrice);
			
			logReturn.put(date, cal.logVol(tempData));
			logList.add( cal.logVol(tempData));
		}
		SortHelper deal = new SortHelper();
		minPrice = deal.sortByDate(minPrice);
		maxPrice = deal.sortByDate(maxPrice);
		openPrice = deal.sortByDate(openPrice);
		closePrice = deal.sortByDate(closePrice);
		
		
		double logReturn_Var =cal.variance(logList);
		CompareVo result =new CompareVo(stockName, stockNum, beginDate, endDate, minPrice, maxPrice, openPrice, closePrice, increase, logReturn, logReturn_Var);
		return result;
		
	}

	@Override
	public double[] change(String id1, String id2, String beginDate, String endDate) {
		SharesDao dao = new SharesDaoImpl();
		DateDeal dateDeal = new DateDeal();
		beginDate = dateDeal.preToLog(beginDate);
		endDate = dateDeal.preToLog(endDate);
		ComparePo data1 = dao.findCompare(id1, beginDate, endDate);
		ComparePo data2 = dao.findCompare(id2, beginDate, endDate);

		double begineDay1 = 0,endDay1 = 0; 
		for(int i=0;i<data1.getDatas().size();i++) {
			if(data1.getDatas().get(i).date.equals(beginDate))
				begineDay1 = data1.getDatas().get(i).lastAdj; //获得第一支股票第一天前一日的复权收盘价
			if(data1.getDatas().get(i).date.equals(endDate))
				endDay1 = data1.getDatas().get(i).adjClose; //获得第一支股票最后一天的复权收盘价
		}
		double rate1 = 0;
		double change1 = endDay1-begineDay1;
		rate1 = change1/begineDay1; //计算第一支股票这段时间内的涨跌幅
		
		double begineDay2 = 0,endDay2 = 0; 
		for(int i=0;i<data2.getDatas().size();i++) {
			if(data2.getDatas().get(i).date.equals(beginDate))
				begineDay2 = data2.getDatas().get(i).lastAdj; //获得第二支股票第一天前一日的复权收盘价
			if(data2.getDatas().get(i).date.equals(endDate))
				endDay2 = data2.getDatas().get(i).adjClose; //获得第二支股票最后一天的复权收盘价
		}
		double rate2 = 0;
		double change2 = endDay2-begineDay2;
		rate2 = change2/begineDay2;
		
		double[] change = {rate1,rate2};
		// TODO Auto-generated method stub
		return change;
	}
	
	
}
