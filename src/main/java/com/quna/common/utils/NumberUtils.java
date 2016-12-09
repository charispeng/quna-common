package com.quna.common.utils;

import java.math.BigDecimal;

public class NumberUtils {
	
	public static BigDecimal stringTransformBigDecimal(String str){
		BigDecimal ret	= null;
		try{
			ret			= new BigDecimal(str);
			return ret;			
		}catch(Exception e){
			throw new IllegalStateException(str + "类型错误,不能转换成BigDecimal");
		}
	}
	
	public static BigDecimal stringTransformBigDecimalReturnDefault(String str){
		return stringTransformBigDecimalReturnDefault(str,new BigDecimal(0));
	}
	
	public static BigDecimal stringTransformBigDecimalReturnDefault(String str,BigDecimal defaultValue){
		BigDecimal ret	= null;
		try{
			ret			= new BigDecimal(str);
			return ret;
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	public static int stringTransformInt(String str){
		int ret			= 0;
		try{
			ret			= Integer.parseInt(str);
			return ret;
		}catch(Exception e){
			throw new IllegalStateException(str + "类型错误,不能转换成Integer");
		}
	}
	
	public static Integer stringTransformIntReturnDefault(String str){
		return stringTransformIntReturnDefault(str,0);
	}
	
	public static int stringTransformIntReturnDefault(String str,int defaultValue){
		int ret;
		try{
			ret			= Integer.parseInt(str);
			return ret;
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	public static long stringTransformLong(String str){
		long ret;
		try{
			ret			= Long.parseLong(str);
			return ret;
		}catch(Exception e){
			throw new IllegalStateException(str + "类型错误,不能转换成Long");
		}
	}
	
	public static long stringTransformLongReturnDefault(String str){
		return stringTransformLongReturnDefault(str,0l);
	}
	
	public static long stringTransformLongReturnDefault(String str,long defaultValue){
		Long ret;
		try{
			ret			= Long.parseLong(str);
			return ret;			
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	public static double stringTransformDouble(String str){
		double ret;
		try{
			ret			= Double.parseDouble(str);
			return ret;
		}catch(Exception e){
			throw new IllegalStateException(str + "类型错误,不能转换成Double");
		}
	}
	
	public static double stringTransformDoubleReturnDefault(String str){
		return stringTransformDoubleReturnDefault(str,0d);
	}
	
	public static double stringTransformDoubleReturnDefault(String str,double defaultValue){
		double ret;
		try{
			ret			= Double.parseDouble(str);
			return ret;			
		}catch(Exception e){
			return defaultValue;
		}
	}
}
