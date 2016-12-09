package com.quna.common.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <pre>
 * <b>AES加密</b>
 * <b>Description:AES加密
 * 其中KEY_SIZE只能使用128位,由于美国出口限制,如果要使用192或是256位
 * 需要 替换${java_home}/jre/lib/security/ 下面的local_policy.jar和US_export_policy.jar
 * 这两个包
 * 
 *  一般情况下,128位已经够使用
 * </b>
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2016年10月19日 上午11:12:23
 * <b>Copyright:</b> Copyright &copy;2006-2016 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2016年10月19日 上午11:12:23    252054576@qq.com  new file.
 * </pre>
 */
public class AESUtils extends _Util{

	private final static String  ALGORITHM 	= "AES";
	
	private static int KEY_SIZE				= 128;		//128,192,256;如果要使用192和256,需要替换${java_home}/jre/lib/security/ 下面的local_policy.jar和US_export_policy.jar
	
	private static String DEFAULT_ENCODING	= "UTF-8";
	
	/**
	 * 创建SPEC
	 * @param password		加密密码
	 * @return
	 * @throws Exception
	 */
	public static SecretKeySpec createSpec(String password) throws Exception{
		KeyGenerator keyGenerator		= KeyGenerator.getInstance(ALGORITHM);
		keyGenerator.init(KEY_SIZE, new SecureRandom(password.getBytes(DEFAULT_ENCODING)));
		SecretKey key					= keyGenerator.generateKey();
		SecretKeySpec spec				= new SecretKeySpec(key.getEncoded(), ALGORITHM);
		return spec;
	}
	
	/**
	 * 创建Cipher
	 * @param mode		加密|解密
	 * @param password	加密密码
	 * @return
	 * @throws Exception
	 */
	public static Cipher createCipher(int mode,String password) throws Exception{
		Cipher cipher					= Cipher.getInstance(ALGORITHM);
		cipher.init(mode, createSpec(password));		
		return cipher;
	}
	
	/**
	 * 加密
	 * @param bytes			待加密byte[]
	 * @param password		加密密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] bytes,String password) throws Exception{		
		byte[] b1						= createCipher(Cipher.ENCRYPT_MODE,password).doFinal(bytes);
		return b1;		
	}
	
	/**
	 * 解密
	 * @param bytes				待解密byte[]
	 * @param password			解密密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] bytes,String password) throws Exception{
		byte[] b1						= createCipher(Cipher.DECRYPT_MODE,password).doFinal(bytes);
		return b1;
	}
	
	/**
	 * 将字符串加密成base64
	 * @param beEncrypt
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String encryptToBase64(String beEncrypt,String password) throws Exception{
		return encryptToBase64(beEncrypt,DEFAULT_ENCODING,password);
	}
	/**
	 * 将字符串加密成base64
	 * @param beEncrypt
	 * @param encoding
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String encryptToBase64(String beEncrypt,String encoding,String password) throws Exception{
		byte[] bytes	= encrypt(beEncrypt.getBytes(encoding), password);
		BASE64Encoder encoder	= new BASE64Encoder();
		return encoder.encodeBuffer(bytes);
	}
	
	/**
	 * 将base64解密成字符串格式
	 * @param beDecryption
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String decryptFromBase64(String beDecryption,String password) throws Exception{
		return decryptFromBase64(beDecryption,DEFAULT_ENCODING,password);
	}
	
	/**
	 * 将base64解密成字符串格式
	 * @param beDecryption
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String decryptFromBase64(String beDecryption,String encoding,String password) throws Exception{
		BASE64Decoder decoder	= new BASE64Decoder();
		byte[] bytes			= decoder.decodeBuffer(beDecryption);
		byte[] b1				= decrypt(bytes, password);
		return new String(b1,encoding);
	}
	
	/**
	 * 将加密后字符串转成hex String
	 * @param beEncrypt
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String encryptToHexString(String beEncrypt,String password) throws Exception{
		return encryptToHexString(beEncrypt,DEFAULT_ENCODING,password);
	}
	/**
	 * 将加密后字符串转成hex String
	 * @param beEncrypt
	 * @param encoding
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String encryptToHexString(String beEncrypt,String encoding,String password) throws Exception{
		byte[] bytes	= encrypt(beEncrypt.getBytes(encoding), password);
		return new BigInteger(1,bytes).toString(16);
		//return bytesToHexString(bytes);
	}
	
	/**
	 *  将hex string 字符串解密
	 * @param beDecryption
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String decryptFromHexString(String beDecryption,String password) throws Exception{
		return decryptFromHexString(beDecryption,DEFAULT_ENCODING,password);
	}
	/**
	 * 将hex string 字符串解密
	 * @param beDecryption
	 * @param encoding
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String decryptFromHexString(String beDecryption,String encoding,String password) throws Exception{
		//byte[] bytes	= hexStringToBytes(beDecryption);
		BigInteger bi	= new BigInteger(beDecryption,16);
		byte[] array 	= bi.toByteArray();
		if (array[0] == 0) {
		    byte[] tmp = new byte[array.length - 1];
		    System.arraycopy(array, 1, tmp, 0, tmp.length);
		    array = tmp;
		}
		byte[] bytes	= array;
		byte[] b1		= decrypt(bytes, password);
		return new String(b1,encoding);
	}
	
	
	public static void main(String[] args) throws Exception{
		String beEncrypt	= "我叫彭超我叫彭超";
		String password		= "1234567812345678";
		
		//KmDTavbOLAWfo6yBrcCmSA==
		//2TWwg5aJuZdb7tKOvRu49Q==
		
		String base64	= encryptToBase64(beEncrypt, password);
		System.out.println(base64);
		
		System.out.println(decryptFromBase64(base64, password));
		
		
		String hexString= encryptToHexString(beEncrypt, password);
		System.out.println(hexString);
		
		System.out.println(decryptFromHexString(hexString, password));
		
		//2a60d36af6ce2c059fa3ac81adc0a648
		//2a60d36af6ce2c059fa3ac81adc0a648
		//b283a5cdcc3478e61edc547d6f448ece3263c090e9c7690f4d8e9ae819f46cdf
		//b283a5cdcc3478e61edc547d6f448ece3263c090e9c7690f4d8e9ae819f46cdf
	}
	
}
