package vopo;



public class MarketVo {
	String date;	   //日期
	long totalVolume; //当日总交易量
	double numOfUp10; //涨停股票数
	double numOfDrop10; //跌停股票数
	double numOfUp5;  //涨5%的股票数
	double numOfDrop5; //跌5%的股票数
	double numOfDisL;  //开盘-收盘大于5%的股票数
	double numOfDisS;  //开盘-收盘小于5%的股票数
	public MarketVo(){
		
	}
	
	

	public MarketVo(String date, long totalVolume, double numOfUp10, double numOfDrop10, double numOfUp5,
			double numOfDrop5, double numOfDisL, double numOfDisS) {
		super();
		this.date = date;
		this.totalVolume = totalVolume;
		this.numOfUp10 = numOfUp10;
		this.numOfDrop10 = numOfDrop10;
		this.numOfUp5 = numOfUp5;
		this.numOfDrop5 = numOfDrop5;
		this.numOfDisL = numOfDisL;
		this.numOfDisS = numOfDisS;
	}



	public MarketVo(String date) {
		this.date = date;
		// TODO Auto-generated constructor stub
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public long getTotalVolume() {
		return totalVolume;
	}



	public void setTotalVolume(long totalVolume) {
		this.totalVolume = totalVolume;
	}



	public double getNumOfUp10() {
		return numOfUp10;
	}
	public void setNumOfUp10(double numOfUp10) {
		this.numOfUp10 = numOfUp10;
	}
	public double getNumOfDrop10() {
		return numOfDrop10;
	}
	public void setNumOfDrop10(double numOfDrop10) {
		this.numOfDrop10 = numOfDrop10;
	}
	public double getNumOfUp5() {
		return numOfUp5;
	}
	public void setNumOfUp5(double numOfUp5) {
		this.numOfUp5 = numOfUp5;
	}
	public double getNumOfDrop5() {
		return numOfDrop5;
	}
	public void setNumOfDrop5(double numOfDrop5) {
		this.numOfDrop5 = numOfDrop5;
	}
	public double getNumOfDisL() {
		return numOfDisL;
	}
	public void setNumOfDisL(double numOfDisL) {
		this.numOfDisL = numOfDisL;
	}
	public double getNumOfDisS() {
		return numOfDisS;
	}
	public void setNumOfDisS(double numOfDisS) {
		this.numOfDisS = numOfDisS;
	}
	
}
