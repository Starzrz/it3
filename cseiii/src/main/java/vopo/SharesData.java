package vopo;

public class SharesData {
	public String name;
	public String number;
	public String date;
	public double minPrice;
	public double maxPrice;
	public double openPrice;
	public double closePrice;
    public long volume;
    public double lastPrice; //上一交易日的收盘价
    public double adjClose;  //复权收盘价
    public double lastAdj;   //上一日的复权收盘价
    public SharesData() {
		// TODO Auto-generated constructor stub
	}
	public SharesData(String name, String number, String date, double minPrice, double maxPrice, double openPrice,
			double closePrice, long volume, double lastPrice, double adjClose, double lastAdj) {
		this.name = name;
		this.number = number;
		this.date = date;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.volume = volume;
		this.lastPrice = lastPrice;
		this.adjClose = adjClose;
		this.lastAdj = lastAdj;
	}
	
	
	
    
}
