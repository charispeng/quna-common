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
	
	public static final SimpleDateFormat sdf	= new SimpleDateFormat();	//此对象是公用的,当心线程安全问题	
	
	public static Date getDateNow(){
		return new Date();
	}
	
	public static Calendar getCalendar(){
		return Calendar.getInstance();
	}
	
	public static Date getDateNext(){
		return getDateByNextDays(1);
	}
	
	public static Date getDatePre(){
		return getDateByNextDays(-1);
	}
	
	public static Date getDateByNextDays(int days){
		Calendar calendar	= getCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}
	
	public synchronized static String getDateStringByPattern(String pattern,Date date){
		sdf.applyPattern(pattern);		
		return sdf.format(date);
	}
	
	public synchronized static Date getDateByPattern(String pattern,String dateString) throws ParseException{
		sdf.applyPattern(pattern);		
		return sdf.parse(dateString);
	}
	
	public synchronized static String getDateNowByyyyyMMdd(){
		String pattern	= yyyy + HORIZONTAL + MM + HORIZONTAL + dd;
		return getDateStringByPattern(pattern, getDateNow());
	}
	
	public synchronized static String getDateNowByyyyyMMddHHmmss(){
		String pattern	= yyyy + HORIZONTAL + MM + HORIZONTAL + dd + " " + HH + COLON + mm + COLON + ss;
		return getDateStringByPattern(pattern, getDateNow());
	}
	
	public static void main(String[] args){
		System.out.println(getDateNowByyyyyMMddHHmmss());
	}
}
