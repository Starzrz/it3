package BL.blhelper;

import java.text.DecimalFormat;

public class NumToPercent {
	public String numToPercent(double num) {
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		num = num*100;	
		String percent = df.format(num)+"%";
		return percent;
	}

}
