package BL.momentumBL;

import java.util.Map;

public class HoldRiseData {
	Map<String, Double> riseDate;
	double principle;
	public Map<String, Double> getRiseDate() {
		return riseDate;
	}
	public void setRiseDate(Map<String, Double> riseDate) {
		this.riseDate = riseDate;
	}
	public double getPrinciple() {
		return principle;
	}
	public void setPrinciple(double principle) {
		this.principle = principle;
	}
	public HoldRiseData(Map<String, Double> riseDate, double principle) {
		this.riseDate = riseDate;
		this.principle = principle;
	}
	
}
