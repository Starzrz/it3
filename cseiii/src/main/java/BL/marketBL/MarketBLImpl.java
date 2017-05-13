package BL.marketBL;

import java.util.ArrayList;

import BL.blhelper.SharesCal;
import BL.blservice.SharesMarketBL;

import server.dao.SharesDao;
import server.dataImpl.SharesDaoImpl;
import vopo.MarketPo;
import vopo.MarketVo;
import vopo.SharesData;

public class MarketBLImpl implements SharesMarketBL{

	/* (non-Javadoc)
	 * @see BL.blservice.SharesMarketBL#market(java.lang.String)
	 */
	@Override
	public MarketVo market(String date) {
		SharesCal mathCal = new SharesCal();
		SharesDao data = new SharesDaoImpl();
		MarketPo po = data.findMarket(date);
		if(po==null){
			return null;
		}
		ArrayList<SharesData> dataList = po.getStockList();
		long totalVolume=0; //当日总交易量
		double numOfUp10=0; //涨停股票数
		double numOfDrop10=0; //跌停股票数
		double numOfUp5=0;  //涨5%的股票数
		double numOfDrop5=0; //跌5%的股票数
		double numOfDisL=0;  //开盘-收盘大于5%的股票数
		double numOfDisS=0;  //开盘-收盘小于5%的股票数
		for(SharesData tempData:dataList){
			totalVolume = totalVolume+tempData.volume;
			double shareChange = mathCal.changeVal(tempData);
			if(shareChange>=0.1){
				numOfUp10++;
			}
			else if(shareChange>=0.05){
				numOfUp5++;
				if(tempData.name.contains("ST")){  //如果是ST股票，则5%算涨停
					numOfUp10++;
				}
			}
			else if(shareChange<=-0.1){
				numOfDrop10++;
			}
			else if(shareChange<=-0.05){
				numOfDrop5++;
				if(tempData.name.contains("ST")){  //如果是ST股票，则5%算涨停
					numOfDrop10++;
				}
			}
			double lastPrice5pre = tempData.lastPrice*0.05;
			double changePrice = tempData.openPrice-tempData.closePrice;
			if(changePrice>lastPrice5pre){
				numOfDisL++;
			}
			if(changePrice<-lastPrice5pre){
				numOfDisS++;
			}
		}
		
		return new MarketVo(date, totalVolume, numOfUp10, numOfDrop10, numOfUp5, numOfDrop5, numOfDisL, numOfDisS);
	}

}
