package com.quna.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static final String yyyy				= "yyyy";
	
	public static final String MM				= "MM";
	
	public static final String dd				= "dd";
	
	public static final String HH				= "HH";
	
	public static final String hh				= "hh";
	
	public static final String mm				= "mm";
	
	public static final String ss				= "ss";
	
	public static final String SSS				= "SSS";

	public static final String HORIZONTAL		= "-";
	
	public static final String SLASH			= "/";
	
	public static final String COLON			= ":";
	
	public static final String yyyyMMdd			= yyyy + HORIZONTAL	+ MM + HORIZONTAL + dd;
	
	public static final String yyyyMMddHHmmss	= yyyyMMdd + " " + HH + COLON + mm + COLON + ss;
	
	public static long getNanoTime(){
		return System.nanoTime();
	}
	
	public static long getCurrentTimeMillis(){
		return System.currentTimeMillis();
	}
	
	public static Date getNowDate(){
		return new Date();
	}
	public static int getNowHour(){
		return getCalendar().get(Calendar.HOUR_OF_DAY);
	}
	public static int getNowMinute(){
		return getCalendar().get(Calendar.MINUTE);
	}
	public static int getNowSecond(){
		return getCalendar().get(Calendar.SECOND);
	}	
	public static Calendar getCalendar(){
		return Calendar.getInstance();
	}
	
	public static Date getNextDate(){
		return getDateByNextDays(1);
	}
	
	public static Date getPrevDate(){
		return getDateByNextDays(-1);
	}
	
	public static Date getDate0(Date date){
		Calendar calendar	= getCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
	public static Date getDate2359(Date date){
		Calendar calendar	= getCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		
		return calendar.getTime();
	}
	
	public static Date getDateByNextDays(int days){
		Calendar calendar	= getCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);
		return calendar.getTime();
	}	
	
	public static Date getDateByNextDays(Date date,int days){
		Calendar calendar	= getCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);
		return calendar.getTime();
	}
	
	public static String format(String pattern,Date date){
		SimpleDateFormat sdf	= new SimpleDateFormat();
		sdf.applyPattern(pattern);		
		return sdf.format(date);
	}
	
	public static Date parse(String pattern,String dateString) throws ParseException{
		SimpleDateFormat sdf	= new SimpleDateFormat();
		sdf.applyPattern(pattern);		
		return sdf.parse(dateString);
	}
	
	/**
	 * yyyy-MM-dd
	 * @return
	 */
	public static String defaultSimpleDateString(){
		String pattern	= yyyy + HORIZONTAL + MM + HORIZONTAL + dd;
		return format(pattern, getNowDate());
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String defaultDateString(){
		String pattern	= yyyy + HORIZONTAL + MM + HORIZONTAL + dd + " " + HH + COLON + mm + COLON + ss;
		return format(pattern, getNowDate());
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @param nextDays
	 * @return
	 */
	public static String getNextDaysDateStringByDefaultFormat(Date date,int nextDays){
		Date otherDate	= getDateByNextDays(date, nextDays);
		return format(yyyyMMddHHmmss,otherDate);
	}
	
	public static void main(String[] args){
		System.out.println(getDate2359(getNextDate()));
	}
}
