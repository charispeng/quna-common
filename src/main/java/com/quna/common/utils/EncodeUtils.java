package com.quna.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import com.quna.common.exception.runtime.QunaRuntimeException;
/**
 * 
 * 
 * <pre>
 * <b>编码处理类</b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2015年9月17日 下午4:04:41
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2015年9月17日 下午4:04:41    252054576@qq.com  new file.
 * </pre>
 */
public class EncodeUtils extends _Util{
	
	public static final String UTF8		= "UTF-8";
	public static final String GBK		= "GBK";
	public static final String ISO88591	= "iso-8859-1";
	
	/**
	 * utf-8编码
	 * @param str
	 * @return
	 */
	public static String encodeUTF8(String str){
		if(str == null) return null;
		try {
			return URLEncoder.encode(str, UTF8);
		} catch (UnsupportedEncodingException e) {
			throw new QunaRuntimeException("UTF8编码时异常",e);
		}
	}
	
	public static String encodeGBK(String str){
		if(str == null) return null;
		try {
			return URLEncoder.encode(str, GBK);
		} catch (UnsupportedEncodingException e) {
			throw new QunaRuntimeException("GBK编码时异常",e);
		}
	}
	
	public static String decodeGBK(String str){
		if(str == null) return null;
		try {
			return URLDecoder.decode(str, GBK);
		} catch (UnsupportedEncodingException e) {
			throw new QunaRuntimeException("GBK解码时异常",e);
		}
	}
	
	public static String decodeUTF8(String str){
		if(str == null) return null;
		try {
			return URLDecoder.decode(str, UTF8);
		} catch (UnsupportedEncodingException e) {
			throw new QunaRuntimeException("UTF8解码时异常",e);
		}
	}
	public static String encode(String str,String toEncode){
		if(str == null) return null;
		try {
			return URLEncoder.encode(str, toEncode);
		} catch (UnsupportedEncodingException e) {
			throw new QunaRuntimeException("编码时异常",e);
		}
	}
	public static String decode(String str,String toEncode){
		if(str == null) return null;
		try {
			return URLDecoder.decode(str, toEncode);
		} catch (UnsupportedEncodingException e) {
			throw new QunaRuntimeException("解码时异常",e);
		}
	}
	
	public static String utf8_to_gbk(String str) throws UnsupportedEncodingException{
		return transCode(UTF8,GBK,str);
	}
	public static String gbk_to_utf8(String str) throws UnsupportedEncodingException{
		return transCode(GBK,UTF8,str);
	}
	public static String iso88591_to_utf8(String str) throws UnsupportedEncodingException{
		return transCode(ISO88591,UTF8,str);
	}
	public static String iso88591_to_gbk(String str) throws UnsupportedEncodingException{
		return transCode(ISO88591,GBK,str);
	}
	
	public static String transCode(String srcCode,String dstCode,String str) throws UnsupportedEncodingException{
		if(str == null){
			return null;
		}
		ByteBuffer byteBuffer	= ByteBuffer.wrap(str.getBytes(srcCode));
		Charset charset			= Charset.forName(dstCode);
		CharBuffer charbuffer	= charset.decode(byteBuffer);
		return charbuffer.toString();
	}
}
