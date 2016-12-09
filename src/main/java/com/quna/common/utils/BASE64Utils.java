package com.quna.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.quna.common.exception.runtime.QunaRuntimeException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public abstract class BASE64Utils extends _Util {

    /**
     * BASE64 加密实例
     */
    public static final BASE64Encoder encoder = new BASE64Encoder();

    /**
     * BASE64 解密实例
     */
    public static final BASE64Decoder decoder = new BASE64Decoder();
    
    
    public static String encodeBASE64(String str,String encoding) throws QunaRuntimeException, UnsupportedEncodingException{
    	if(StringUtils.isEmpty(str) || !StringUtils.hasText(encoding)){
    		throw new QunaRuntimeException("param str or encoding is not null!");
    	}
    	byte[] bytes	= getBytes(str, encoding);
    	return encode(bytes);
    }
    /**
     * 对字符串进行BASE64 加密.<br/>
     * 
     * 如果待加密的字符串为null, 则直接返回null.
     * 
     * @param str 待加密的字符串
     * @return String 加密后字符串
     * @throws UnsupportedEncodingException 
     */
    public static String encodeBASE64(String str) throws UnsupportedEncodingException{
        byte[] bytes = getBytes(str, ENCODING);
        return encode(bytes);
    }

    /**
     * 对二进制进行BASE64 加密.<br/>
     * 
     * 如果待加密的二进制为null, 则直接返回 null.
     * 
     * @param bytes 待加密字符串的二进制数组
     * @return String 加密后字符串
     */
    public static String encode(byte[] bytes) {

        if (null == bytes) {
            return null;
        }

        if (bytes.length == 0) {
            return EMPTY_STR;
        }

        return encoder.encodeBuffer(bytes);
    }

    /**
     * 对字符串进行BASE64 解密.
     * 
     * 如果待解密的字符串为null, 则直接返回 null.
     * 
     * @param str 待解密的字符串.
     * @return String 解密后的字符串.
     * @throws IOException
     */
    public static String decodeBASE64(String str) throws IOException{

        byte[] bytes = decode2Bytes(str);

        String _dstr = getString(bytes, ENCODING);

        return _dstr;
    }

    /**
     * 对字符串进行BASE64 解密, 直接返回二进制.<br/>
     * 
     * 如果待解密的字符串为null, 则直接返回 null.
     * 
     * @param str 待解密的字符串.
     * @return byte[] 解密后的二进制.
     * @throws IOException 
     */
    public static byte[] decode2Bytes(String str) throws IOException {
    	
        if (null == str) {
            return null;
        }

        if (str.length() == 0) {
            return EMPTY_BYTES;
        }

        byte[] bytes = decoder.decodeBuffer(str);
        return bytes;
    }

}
