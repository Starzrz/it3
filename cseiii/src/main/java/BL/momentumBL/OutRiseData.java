package BL.momentumBL;

import java.util.Map;

public class OutRiseData {
	Map<String, Double> riseDate;
	double winRate;
	
	public OutRiseData(Map<String, Double> riseDate, double winRate) {
		this.riseDate = riseDate;
		this.winRate = winRate;
	}
	public Map<String, Double> getRiseDate() {
		return riseDate;
	}
	public void setRiseDate(Map<String, Double> riseDate) {
		this.riseDate = riseDate;
	}
	public double getWinRate() {
		return winRate;
	}
	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}
	

}
