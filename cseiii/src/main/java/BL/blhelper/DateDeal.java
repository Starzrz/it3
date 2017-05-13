package BL.blhelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import server.dao.HelperDao;
import server.dataImpl.HelperDaoImpl;


public class DateDeal {
	/**
	 * 将月日年转为年月日
	 */
	public String BlToEMA(String preDate){
		String [] temp=preDate.split("/");
		String month=temp[0];
		String day=temp[1];
		String year="20"+temp[2];
		String newDate=year+"/"+month+"/"+day;
		return newDate;
	}
	
	
	
	/**
	 * @param preDate
	 * @return 将展示层得到的日期格式转换成逻辑层需要的格式
	 */
	public String preToLog(String preDate){
		String []tempDate = preDate.split("/");
		String year = tempDate[0].substring(2, 4);
		String month = tempDate[1];
		if(month.charAt(0)=='0'){
			month = month.substring(1, 2);
		}
		String day = tempDate[2];
		if(day.charAt(0)=='0'){
			day = day.substring(1, 2);
		}
		return month+"/"+day+"/"+year;
	}
	
	/**
	 * @param date
	 * @return 日期转换，yyyy-mm-dd转为mm/dd/yy
	 */
	public String brenchChange(String date){
		String []tempDate = date.split("-");
		String year = tempDate[0].substring(2, 4);
		String month = tempDate[1];
		if(month.charAt(0)=='0'){
			month = month.substring(1, 2);
		}
		String day = tempDate[2];
		if(day.charAt(0)=='0'){
			day = day.substring(1, 2);
		}
		return month+"/"+day+"/"+year;
	}
	/**
	 * @param date
	 * @return 日期转换，yyyy/mm/dd转为mm/dd/yy
	 */
	public String brenchChange2(String date){
		String []tempDate = date.split("/");
		String year = tempDate[0].substring(2, 4);
		String month = tempDate[1];
		if(month.charAt(0)=='0'){
			month = month.substring(1, 2);
		}
		String day = tempDate[2];
		if(day.charAt(0)=='0'){
			day = day.substring(1, 2);
		}
		return month+"/"+day+"/"+year;
	}
	
	/**
	 * @param LogDate
	 * @return 日期转换，mm/dd/yy转为yyyy-mm-dd
	 */
	public String LogToPre(String LogDate){
		String []tempDate = LogDate.split("/");
		String year = "20"+tempDate[2];
		String month = tempDate[0];
		
		String day = tempDate[1];
		if(month.length()<2){
			month = "0"+month;
		}
		if(day.length()<2){
			day = "0"+day;
		}
		return year+"-"+month+"-"+day;
	}
	
	/**
	 * @param LogDate
	 * @return 日期转换，mm/dd/yy转为yyyy/mm/dd
	 */
	public String LogToPre2(String LogDate){
		String []tempDate = LogDate.split("/");
		String year = "20"+tempDate[2];
		String month = tempDate[0];
		String day = tempDate[1];
		if(month.length()<2){
			month = "0"+month;
		}
		if(day.length()<2){
			day = "0"+day;
		}
		return year+"/"+month+"/"+day;
	}
	/**
	 * @param datehange
	 * @return 将展示层得到的日期格式转换成逻辑层需要的格式
	 */
	public String datechange(LocalDate Date){
		String str = String.valueOf(Date);

		return str.substring(0,4)+"/"+str.substring(5,7)+"/"+str.substring(8);
		
	}
	
	
	/**
	 * @param date
	 * @param changeDays
	 * @return 日期加减的方法，传入返回格式为mm/dd/yy
	 */
	public String logDateChange(String date,int changeDays){
		String tempDate = LogToPre2(date);
		
		String result = dateChange(tempDate, changeDays);
		
		result = brenchChange2(result);
		return result;
	}
	
	/**
	 * @param date
	 * @param changeDays
	 * @return 日期加减的方法，传入和返回格式为yyyy/mm/dd
	 */
	public String dateChange(String date,int changeDays){
		date = brenchChange2(date);
		HelperDao change = new HelperDaoImpl();
		String result = change.changeDays(date, changeDays);
		result = LogToPre2(result);
		return result;
		
		
	}
	/**
	 * @param date1
	 * @param date2
	 * @return 比较两个日期的方法，如果date1在date2之后，则返回true,否则返回false
	 */
	public boolean dateCompare(String date1,String date2){
		String []dates1 = date1.split("/");
		String []dates2 = date2.split("/");
		int year1 = Integer.valueOf(dates1[2]);
		int month1 = Integer.valueOf(dates1[0]);
		int days1 = Integer.valueOf(dates1[1]);
		int year2 = Integer.valueOf(dates2[2]);
		int month2 = Integer.valueOf(dates2[0]);
		int days2 = Integer.valueOf(dates2[1]);
		if(year1>year2){
			return true;
		}
		else if(year1<year2){
			return false;
		}
		else{
			if(month1>month2){
				return true;
			}
			else if(month1<month2){
				return false;
			}
			else{
				if(days1>=days2){
					return true;
				}
				else{
					return false;
				}
			}
		}
	}
	
}
