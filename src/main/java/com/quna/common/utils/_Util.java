package com.quna.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <pre>
 * <b>通用辅助工具.</b>
 * <b>Description:</b> 主要提供如下: 
 *   1、通过字符串获取指定编码的二进制, 已经将二进制转为指定编码的字符串;
 * 
 * <b>Author:</b> liuuhong@yeah.net
 * <b>Date:</b> 2014-1-1 上午10:00:01
 * <b>Copyright:</b> Copyright &copy;2006-2014 onefly.org Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *    1.0   2014-01-01 10:00:01    liuuhong@yeah.net
 *          new file.
 * </pre>
 */
public abstract class _Util {
    /**
     * 常量, 空的字符串, ""
     */
    public static final String EMPTY_STR 	= "";

    /**
     * 常量, 空的二进制数组, new byte[] {}
     */
    public static final byte[] EMPTY_BYTES 	= new byte[] {};

    /**
     * 字符串编码, 默认: UTF-8.
     */
    public static final String ENCODING 	= "UTF-8";

    /**
     * HEX 16进制对照字典.
     */
    public static final char HEX_DIGITS[] 	= { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * 定义通用参数变量格式模板, <br/>
     * 格式为: ${参数值}, 即 ${THun2VqRzUwe/GsUmt/MO}.<br/>
     * Pattern.compile("\\$\\{[^\\$\\{\\}]+\\}"); <br/>
     */
    public static final Pattern VAR_PATTERN = Pattern.compile("\\$\\{[^\\$\\{\\}]+\\}");

    /**
     * 将字符串转换二进制数组, 默认字符集编码: ENCODING = "UTF-8".<br/>
     * 如果被加密字符串为null 或 长度为0时, 则直接返回 null.
     * 
     * @param str 待转换的字符串.
     * @return byte[] 转换后的二进制数组.
     */
    public static byte[] getBytes(String str) throws UnsupportedEncodingException{
        return getBytes(str, ENCODING);
    }

    /**
     * 将字符串转为二进制数组, 需要指定具体字符集编码.<br/>
     * 如果被加密字符串为null时, 则直接返回 null;<br/>
     * 如果转换指定的字符集编码为null 或 长度为0时, 则直接返回 null.
     * 
     * @param str 待转换的字符串.
     * @param encoding 指定的字符编码.
     * @return byte[] 转换后的二进制数组.
     * @throws UnsupportedEncodingException 
     */
    public static byte[] getBytes(String str, String encoding) throws UnsupportedEncodingException{

        if (null == str || null == encoding || encoding.length() == 0) {
            return null;
        }

        if (str.length() == 0) {
            return EMPTY_BYTES;
        }

        byte[] bytes = str.getBytes(encoding);

        return bytes;
    }

    /**
     * 将二进制数组转为字符串, 默认字符集编码: ENCODING = "UTF-8".
     * 
     * @param bytes 待转换的二进制数组.
     * @return String 转换后的字符串.
     */
    public static String getString(byte[] bytes) throws UnsupportedEncodingException{
        return getString(bytes, ENCODING);
    }

    /**
     * 将二进制数组转为字符串, 需要指定具体字符集编码.<br/>
     * 如果给定的二进制数组为null, 则直接返回 null;<br/>
     * 如果转换指定的字符集编码为null 或 长度为0时, 则直接返回 null.
     * 
     * @param bytes 待转换的二进制数组.
     * @param encoding 指定的字符编码.
     * @return String 转换后的字符串.
     */
    public static String getString(byte[] bytes, String encoding) throws UnsupportedEncodingException{

        if (null == bytes || null == encoding || encoding.length() == 0) {
            return null;
        }

        if (bytes.length == 0) {
            return EMPTY_STR;
        }

        String str = new String(bytes, encoding);

        return str;
    }

    /**
     * 对字符串进行可逆加密计算.<br/>
     * 如果待加密的字符串为null, 则直接返回null.
     * 
     * @param str 待可逆加密的字符串.
     * @return String 可逆加密后的字符串.
     */
    public static String encode(String str) {

        if (null == str) {
            return null;
        }

        if (str.length() == 0) {
            return EMPTY_STR;
        }

        char[] a = str.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        str = new String(a);

        return str;
    }

    /**
     * 对可逆加密后的字符串解密.<br/>
     * 如果待解密的字符串为null, 则直接返回null.
     * 
     * @param str 待解密的字符串.
     * @return String 解密后的字符串.
     */
    public static String decode(String str) {

        char[] a = str.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        str = new String(a);

        return str;
    }
    
    /**
     * 将byte[]转换成base64字符串
     * @param 	待转换字符串
     * @return	字符串
     */
    public static String bytesToBase64(byte[] bytes){
    	BASE64Encoder encoder	= new BASE64Encoder();
    	return encoder.encodeBuffer(bytes).replaceAll("\r|\n", "");
    }
    
    /**
     * 将base64字符串转换成byte[]
     * @param base64
     * @return
     * @throws IOException
     */
    public static byte[] base64ToBytes(String base64) throws IOException{
    	BASE64Decoder decoder	= new BASE64Decoder();
    	return decoder.decodeBuffer(base64);
    }
    
    /**
     * 将byte[]数据转换成hex String
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes){
    	StringBuilder stringBuilder	= new StringBuilder();
    	for(byte by : bytes){
    		String hexString		= Integer.toHexString((by & 0xff));
    		if(hexString.length() < 2){
    			stringBuilder.append("0");
    		}
    		stringBuilder.append(hexString);
    	}
    	return stringBuilder.toString();
    }
    
    /**
     * 将hex String 转换成byte[]
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString){
    	byte[] bytes	= new byte[hexString.length() / 2];
    	for(int i=0;i<hexString.length();i+=2){
    		String str	= hexString.substring(i,i+2);
    		byte by		= (byte)Integer.parseInt(str, 16);
    		bytes[i/2] 	= by;
    	}
    	return bytes;
    }
}