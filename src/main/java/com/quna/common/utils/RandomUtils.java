package com.quna.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomUtils {
	
	private static final String SEED_CHARS	= "ABCDEFGHIJKLMNOPQRSTUVWXVZabcdefghigklmnopqrstuvwxyz0123456789";
	/**
	 * 
	 * @return
	 */
	public static String getyyMMddHHmmssSSSID(){
		SimpleDateFormat sdf	= new SimpleDateFormat("yyMMddHHmmssSSS");
		return sdf.format(new Date());
	}
	
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
}
