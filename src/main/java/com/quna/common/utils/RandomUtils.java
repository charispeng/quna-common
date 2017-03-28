package com.quna.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class RandomUtils {
	
	private static final String SEED_CHARS		= "ABCDEFGHIJKLMNOPQRSTUVWXVZabcdefghigklmnopqrstuvwxyz0123456789";
	
	private static final String HORIZONTAL_LINE	= "-";
	
	/**
	 * 获取当前时间产生的随机数
	 * @return
	 */
	public static String getyyMMddHHmmssSSSID(){
		SimpleDateFormat sdf	= new SimpleDateFormat("yyMMddHHmmssSSS");
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当前时间产生的随机数
	 * @return
	 */
	public static String getyyyyMMddHHmmssSSSID(){
		SimpleDateFormat sdf	= new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}
	
	/**
	 * 随机生成N位随机数
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length){
		char[] chars	= SEED_CHARS.toCharArray();
		int chars_length= chars.length;
		Random random	= new Random();
		StringBuilder sb= new StringBuilder();
		for(int i=0;i<length;i++){
			int randomInt	= random.nextInt(chars_length);
			sb.append(chars[randomInt]);
		}
		
		return sb.toString();
	}
	
	/**
	 * UUID随机数,包含“-”字符
	 * @return
	 */
	public static String randomUUIDString(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * UUID随机数,不含“-”字符
	 * @return
	 */
	public static String randomUUIDStringHorizontalLine(){
		String uuid	= randomUUIDString();
		return uuid.replaceAll(HORIZONTAL_LINE, "");
	}
}
