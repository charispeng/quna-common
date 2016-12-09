package com.quna.common.utils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAUtils {
	/** 指定加密算法为DESede */
	private static String ALGORITHM 		= "RSA";
	/** 指定key的大小 */
	private static int KEYSIZE 				= 2048;
	/** 指定公钥存放文件 */
	public static String PUBLIC_KEY 		= "PublicKey";
	/** 指定私钥存放文件 */
	public static String PRIVATE_KEY 		= "PrivateKey";
	
	public static String DEFAULT_ENCODING	= "UTF-8";

	/**
	 * 生成密钥对
	 */
	public static Map<String,String> generateKeyPair() throws Exception {
		/** RSA算法要求有一个可信任的随机数源 */
		SecureRandom sr 		= new SecureRandom();
		/** 为RSA算法创建一个KeyPairGenerator对象 */
		KeyPairGenerator kpg 	= KeyPairGenerator.getInstance(ALGORITHM);
		/** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
		kpg.initialize(KEYSIZE, sr);		
		/*
		 * 不采用随机数源
		KeyPairGenerator kpg 	= KeyPairGenerator.getInstance(ALGORITHM);
		kpg.initialize(KEYSIZE);
		*/
		
		/** 生成密匙对 */
		KeyPair kp 				= kpg.generateKeyPair();
		/** 得到公钥 */
		Key publicKey 			= kp.getPublic();
		/** 得到私钥 */
		Key privateKey 			= kp.getPrivate();
		
		BASE64Encoder encoder	= new BASE64Encoder();
		String publicKeyStr		= encoder.encodeBuffer(publicKey.getEncoded());
		String privateKeyStr	= encoder.encodeBuffer(privateKey.getEncoded());
		
		Map<String,String> keyMap	= new HashMap<String,String>();
		keyMap.put(PUBLIC_KEY, publicKeyStr.replaceAll("\n|\r", ""));
		keyMap.put(PRIVATE_KEY, privateKeyStr.replaceAll("\n|\r", ""));
		
		return keyMap;
	}
	
	/**
	 * 创建KeyFactory
	 * @return
	 * @throws Exception
	 */
	public static KeyFactory createKeyFactory() throws Exception{
		return KeyFactory.getInstance(ALGORITHM);
	}
	
	/**
	 * 创建公钥
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static Key createPublicKey(String publicKey) throws Exception{
		BASE64Decoder decoder	= new BASE64Decoder();
		byte[] publicKeyBytes	= decoder.decodeBuffer(publicKey);
		
		X509EncodedKeySpec spec	= new X509EncodedKeySpec(publicKeyBytes);
		return createKeyFactory().generatePublic(spec);
	}
	
	/**
	 * 创建私钥
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static Key createPrivateKey(String privateKey) throws Exception{
		BASE64Decoder decoder	= new BASE64Decoder();
		byte[] privateKeyBytes	= decoder.decodeBuffer(privateKey);
		
		PKCS8EncodedKeySpec spec= new PKCS8EncodedKeySpec(privateKeyBytes);		
		return createKeyFactory().generatePrivate(spec);
	}
	
	/**
	 * 用公钥加密
	 * 加密方法 source： 源数据
	 */
	public static String encryptPublicKey(String source,String publicKeyStr) throws Exception {
		Key	publicKey			= createPublicKey(publicKeyStr);
		
		/** 得到Cipher对象来实现对源数据的RSA加密 */
		Cipher cipher 			= Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] b 				= source.getBytes(DEFAULT_ENCODING);
		/** 执行加密操作 */
		byte[] b1				= cipher.doFinal(b);
		
		BASE64Encoder encoder 	= new BASE64Encoder();
		return encoder.encode(b1).replaceAll("\n|\r","");
	}

	/**
	 * 用私钥解密
	 * 解密算法 cryptograph:密文
	 */
	public static String decryptPrivateKey(String cryptograph,String privateKeyStr) throws Exception {
		Key privateKey			= createPrivateKey(privateKeyStr);
		
		/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
		Cipher cipher 			= Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		BASE64Decoder decoder	= new BASE64Decoder();
		byte[] b1				= decoder.decodeBuffer(cryptograph);
		/** 执行解密操作 */
		byte[] b 				= cipher.doFinal(b1);
		return new String(b,DEFAULT_ENCODING);
	}
	
	/**
	 * 用私钥加密
	 * @param cryptograph		待加密
	 * @param privateKeyStr		私钥
	 * @return
	 * @throws Exception
	 */
	public static String encryptPrivateKey(String cryptograph,String privateKeyStr) throws Exception{
		Key privateKey			= createPrivateKey(privateKeyStr);
		
		/** 得到Cipher对象对已用公钥加密的数据进行RSA加密 */
		Cipher cipher			= Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		
		byte[] b1				= cryptograph.getBytes(DEFAULT_ENCODING);
		/** 执行加密操作 */
		byte[] b				= cipher.doFinal(b1);
		
		BASE64Encoder encoder	= new BASE64Encoder();		
		return encoder.encodeBuffer(b).replaceAll("\n|\r","");
	}
	
	/**
	 * 用公钥解密
	 * @param cryptograph		待解密
	 * @param publicKeyStr		公钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptPublicKey(String cryptograph,String publicKeyStr) throws Exception{
		Key publicKey				= createPublicKey(publicKeyStr);
		
		/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
		Cipher cipher				= Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		/**
		 * 执行解密操作
		 */
		BASE64Decoder decoder		= new BASE64Decoder();
		byte[] b1					= decoder.decodeBuffer(cryptograph);
		byte[] b					= cipher.doFinal(b1);
		return new String(b,DEFAULT_ENCODING);
	}
	
	/**
	 * 获取sign
	 * @param cryptograph
	 * @param signatureType
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	public static String signPrivateKey(String cryptograph,String signatureType,String privateKeyStr) throws Exception{
		PrivateKey privateKey		= (PrivateKey)createPrivateKey(privateKeyStr);
		
		Signature signature			= Signature.getInstance(signatureType);
		signature.initSign(privateKey);
		signature.update(cryptograph.getBytes(DEFAULT_ENCODING));
		
		BASE64Encoder encoder		= new BASE64Encoder();
		return encoder.encodeBuffer(signature.sign()).replaceAll("\r|\n", "");
	}
	
	/**
	 * 验证sign是否正确
	 * @param cryptograph
	 * @param signatureType
	 * @param publicKeyStr
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyPublicKey(String cryptograph,String sign,String signatureType,String publicKeyStr) throws Exception{
		PublicKey publicKey			= (PublicKey)createPublicKey(publicKeyStr);
		
		Signature signature			= Signature.getInstance(signatureType);
		signature.initVerify(publicKey);
		signature.update(cryptograph.getBytes(DEFAULT_ENCODING));
		return signature.verify(new BASE64Decoder().decodeBuffer(sign));
	}
	
	
	public static void main(String[] args) throws Exception {
		Map<String,String> keys	= generateKeyPair();
		String privateKey	= keys.get(PRIVATE_KEY);
		String publicKey	= keys.get(PUBLIC_KEY);
		
		System.out.println(privateKey);
		System.out.println(publicKey);
		
		//String privateKey	= "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM4EuLjsrLyrn+eo5d2TF/jyw7hqKc7RD7olkZn1u+ttMo62ss9Ape47fPcj44FXehn0fLKuwQ44x57oVqWFuwUVWII/csZB6zk1Zig0zYwvSSQzTFlCCLd52vTB3kGOcL4ZMhVIJoAJyn7P8uqVTOOgPEX9eMU72Zhy8HLUWAmnAgMBAAECgYAIiOOKLaveSRHfy/xx2HUmyA/ZpPREcB7BrGrCwQ5e63Arv1/a/Cejne1IY8E6C4CwcJN1541x5GJIsAFe7BJaSYaGl82nEv6c/0ZVQO0GiDinrF6xhHyKN5fdQiednj1o2S4zcI1yzzFrjKBY3k0dqkmd9Gzq7QCBbPBqtCBoyQJBAOs7V2ASOOpFAkak+U/cwVY/jsNhW+LDG3y9RRNVpkqI+fgsir5iYQEVVvGizBO+lRN4kOAc0W1pxoBhOsGOcCMCQQDgNRswcbmA+mrNoJpckcxVS5jscRUFsKhUC2hlYC1V6YyhWNX+gkWiAM0n9sdqFDQEHDlOfy8ffIJS8I+eU9atAkA6pxg5PzHrz/sqTDZR4HRgogZh1yPHHej7qGHR2VhWW2Mgq1KI+BTB5WVBaNtDzRB0w2o0R1s80dQJ7LRu0KpLAkAU82FzCW14K+5HALbr54PchI/pqDd6rKNOFzLOJkqWOJi3iwGUIsA2/zQsg9bmhrTFnY2NVD0nCA91iJ0jAlqRAkEA21RRU5JcSu3SmN80/7+Y0LL3Sm05ceiNAW7/eOaAbiU9iBOMEvmWcj4QeuBZKGpOK5ipMrcSt5rzd4eutEHWQQ==";
		//String publicKey	= "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOBLi47Ky8q5/nqOXdkxf48sO4ainO0Q+6JZGZ9bvrbTKOtrLPQKXuO3z3I+OBV3oZ9HyyrsEOOMee6FalhbsFFViCP3LGQes5NWYoNM2ML0kkM0xZQgi3edr0wd5BjnC+GTIVSCaACcp+z/LqlUzjoDxF/XjFO9mYcvBy1FgJpwIDAQAB}q007huRgdYC3feNGryI1NkE3h8y6fni+NopE6fT+Y0a4gCjVeFUfgQY9Bm+LZfJsspUlwhfZZXwaiAq4cGMLixPbRSzZn8PoP2973b7yiJRaI21objmjXHM5zqABXBO/bLtyAcDWxifA8hNqOWaBpA3JahXWK3h8mNbj/29gyZw=";
		
		String source 		= "老妹儿!!!~~~````老妹儿,老妹儿,老妹儿~~~~";					// 要加密的字符串
		System.out.println(source.getBytes().length);
		String cryptograph 	= encryptPublicKey(source, publicKey);					// 生成的密文
		//cryptograph			= cryptograph.replaceAll("\n|\r", "");
		
		System.out.println(cryptograph);
		String target 		= decryptPrivateKey(cryptograph,privateKey);					// 解密密文
		System.out.println(target);
		
		
		String sign			= signPrivateKey(source, "SHA1WithRSA",privateKey);
		System.out.println( " length:" + sign.length() + " sign : " + sign );
		System.out.println(verifyPublicKey(source,sign, "SHA1WithRSA", publicKey));
	}
}