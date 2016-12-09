package com.quna.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.quna.common.exception.runtime.QunaRuntimeException;
/**
 * <pre>
 * <b>MD5加、解密辅助工具.</b>
 * <b>Description:</b> 主要提供对字符串进行加密; 对加密后字符串进行解密.
 *     MD5的算法在RFC1321中定义, 给出了Test suite用来检验你的实现是否正确: 
 *     MD5 ("") = d41d8cd98f00b204e9800998ecf8427e 
 *     MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
 *     MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72 
 *     MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0 
 *     MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
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
public abstract class MD5Utils extends _Util {

    /**
     * MD5
     */
    public static final String MD5		= "MD5";
    
    public static final String SHA_1	= "SHA_1";
    
    public static final String SHA_256	= "SHA-256";

    /**
     * 对字符串进行MD5加密.<br/>
     * 如果待加密的字符串为null, 则直接返回null.
     * 默认的编码为UTF-8
     * 
     * @param str 待加密的字符串.
     * @return String 加密后字符串.
     * @throws NoSuchAlgorithmException 
     * @throws UnsupportedEncodingException 
     */
    public static String encodeMD5(String str) throws IOException, NoSuchAlgorithmException {

        byte[] bytes = getBytes(str,ENCODING);

        return encode(bytes);
    }

    /**
     * 对二进制进行MD5加密.<br/>
     * 
     * 如果待加密的二进制为null, 则直接返回null.
     * 
     * @param bytes 待加密的二进制.
     * @return String 加密后二进制.
     * @throws NoSuchAlgorithmException 
     */
    public static String encode(byte[] bytes) throws NoSuchAlgorithmException {

        if (null == bytes) {
            return null;
        }

        String str = null;

        // 每个字节用 16 进制表示的话, 使用两个字符, 所以表示成 16 进制需要 32 个字符
        char chr[] = new char[16 * 2];

        // 表示转换结果中对应的字符位置
        int k = 0;

        //用来将字节转换成 16 进制表示的字符
        MessageDigest md = MessageDigest.getInstance(MD5);
        md.update(bytes);

        // MD5 的计算结果是一个 128 位的长整数, 用字节表示就是 16 个字节
        byte tmp[] = md.digest();
        
        // 从第一个字节开始, 对 MD5 的每一个字节转换成 16 进制字符的转换
        for (int i = 0; i < 16; i++) {
            // 取第 i 个字节
            byte byte0 = tmp[i];            
            // 取字节中高 4 位的数字转换, >>> 为逻辑右移, 将符号位一起右移
            chr[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
            
            // 取字节中低 4 位的数字转换
            chr[k++] = HEX_DIGITS[byte0 & 0xf];
        }

        // 换后的结果转换为字符串
        str = new String(chr);

        return str;
    }
    
    
    /**
     * 加密
     * @param algorithm
     * @param entryString
     * @param encoding
     * @return
     */
    public static byte[] digest(String algorithm,String entryString,String encoding){
    	try {
			MessageDigest md	= MessageDigest.getInstance(algorithm);
			md.update(null == encoding ? entryString.getBytes() : entryString.getBytes(encoding));
			return md.digest();
		}catch (Exception e) {
			throw new QunaRuntimeException(e);
		}
    }
    
    /**
     * 对于加密后返回的byte进行格式化
     * @param bytes
     * @return
     */
    public static String format(byte[] bytes){
    	StringBuilder stringBuilder = new StringBuilder();
    	for(byte bt : bytes){    		
    		stringBuilder.append(HEX_DIGITS[bt >>> 4 & 0xf] ).append(HEX_DIGITS[bt & 0xf]);
    	}
    	return stringBuilder.toString();
    }
    
    /**
     * SHA加密
     * 默认的编码为UTF-8
     * 
     * @param str	字符串
     * @return
     */
    public static String sha(String str){
    	return format(digest(SHA_1,str,ENCODING));
    }
    /**
     * SHA加密
     * @param str		字符串
     * @param encoding	字符编码
     * @return
     */
    public static String sha(String str,String encoding){
    	return format(digest(SHA_1,str,encoding));
    }
    
    /**
     * SHA-256加密
     * 默认的编码为UTF-8
     * 
     * @param str	字符串  
     * @return
     */
    public static String sha256(String str){
    	return format(digest(SHA_256,str,ENCODING));
    }
    /**
     * SHA-256加密
     * 
     * 
     * @param str		字符串
     * @param encoding	字符编码
     * @return
     */
    public static String sha256(String str,String encoding){
    	return format(digest(SHA_256,str,encoding));
    }
    
    /**
     * md5加密
     * 默认的编码为UTF-8
     * 
     * @param str
     * @return
     */
    public static String md5(String str){
    	return format(digest(MD5,str,ENCODING));
    }
    
    /**
     * md5加密
     * 
     * 
     * @param str		被加密串
     * @param encoding	加密编码
     * @return
     */
    public static String md5(String str,String encoding){
    	return format(digest(MD5,str,encoding));
    }
}
