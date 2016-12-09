package com.quna.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.quna.common.exception.runtime.QunaRuntimeException;

/**
 * <pre>
 * <b>加密类</b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2015年9月21日 下午3:21:27
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2015年9月21日 下午3:21:27    252054576@qq.com  new file.
 * </pre>
 */
public class EncryUtils {
	private final static int START_POS 		= 33;
	private final static int END_POS 		= 125;
	private final static int XML_DELIMITER 	= 126;
	public static final String MD5			= "MD5";
	public static final String SEPARATE		= "=";
	public static final String AND			= "&";
	public static final String ENCODING		= "utf-8";
    /**
     * HEX 16进制对照字典.
     */
    public static final char HEX_DIGITS[] 	= { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	/**
	 * 将map参数和Key结合生成Sign
	 * @param map		参数
	 * @param key		加盐key
	 * @param encoding	编码
	 * @return
	 */
	public static String md5_sign(Map<String,String> map,String key,String encoding){
		String source	= filter_and_sort(map);
		return md5_sign(source,key,encoding);
	}
	
	/**
	 * 将map参数和Key结合生成Sign
	 * 默认采用utf-8编码
	 * 
	 * @param map	参数
	 * @param key	加盐key
	 * @return
	 */
	public static String md5_sign(Map<String,String> map,String key){
		String source	= filter_and_sort(map);
		return md5_sign(source,key,ENCODING);
	}
	
	/**
	 * 将字符串和Key结合生成Sign
	 * @param source		需加密的字符串
	 * @param key			加盐key
	 * @param encoding		编码
	 * @return
	 */
	public static String md5_sign(String source,String key,String encoding){
		source 			= source + key;
		return md5(source,encoding);
	}
	
	/**
	 * 排序和过滤
	 * @param map
	 * @return
	 */
	public static String filter_and_sort(Map<String,String> map){
		StringBuilder stringBuilder	= new StringBuilder();
		List<String> keys			= new ArrayList<String>();
		keys.addAll(map.keySet());
		Collections.sort(keys);
		for(String key : keys){
			String value	= map.get(key);			
			if(null == value || value.trim().length() == 0){
				continue;
				
			}
			stringBuilder.append(key).append(SEPARATE).append(value).append(AND);
		}
		if(stringBuilder.length() > 0){
			stringBuilder.deleteCharAt( stringBuilder.length() - 1 );
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * 获取字符串的hash值
	 * @param str
	 * @return
	 */
	public static int getHash(String str){
		if(str == null){
			return 0;
		}
		return str.hashCode();
	}
	
	/**
	 * 获取byte数组
	 * @param str			字符串
	 * @param encoding		编码
	 * @return
	 */
	public static byte[] getBytes(String str, String encoding){
        if (null == str || null == encoding || encoding.length() == 0) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[]{};
        }
        byte[] bytes;
		try {
			bytes = str.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			throw new QunaRuntimeException(e);
		}
        return bytes;
    }
	
	
	public static String md5(String str,String encoding){
		try{
			MessageDigest md	= MessageDigest.getInstance(MD5);
			md.update(null == encoding ? str.getBytes() : str.getBytes(encoding));
			byte[] bytes		= md.digest();
			StringBuilder sb	= new StringBuilder();
			for(byte bt : bytes){
				sb.append(HEX_DIGITS[bt >>> 4 & 0xf]).append(HEX_DIGITS[bt & 0xf]);
				//sb.append(Integer.toHexString(bt >>> 4 & 0xf)).append(Integer.toHexString(bt & 0xf));
				/*
				String hexString	= Integer.toHexString(bt & 0xff).toUpperCase();
				if(hexString.length() != 2){
					hexString		= "0" + hexString;
				}
				sb.append(hexString);
				*/
			}			
			return sb.toString();
		}catch(Exception e){
			throw new QunaRuntimeException(e);
		}
	}

	public static String md5(String str){
		return md5(str,ENCODING);
	}
	
	
	
	//======================================其它加密方法=================================================
	/**
	 * Since it returns as a XML format, the following characters need to avoid
	 * & => &amp; < => &lt; > => &gt; " => &quot; ' => &apos;
	 * 
	 * @param input
	 * @param key
	 * @return
	 */
	public static String encrypt(final String input, final String key) {
		if (input == null || key == null) {
			return "";
		}
		String useKey 	= key;
		float value 	= (float) input.length() / (float) key.length();
		if (value > 1.0f) {
			for (float i = 0; i < value; i++) {
				useKey += useKey;
			}
		}

		StringBuffer sb 	= new StringBuffer(input.length());
		for (int i = 0; i < input.length(); i++) {
			char inputChar 	= input.charAt(i);
			char keyChar 	= useKey.charAt(i);
			
			if (inputChar + keyChar > END_POS) {
				int diff1 = inputChar + keyChar - END_POS;
				if (START_POS + diff1 > END_POS) {
					int diff2 = START_POS + diff1 - END_POS;
					sb.append((char) (START_POS + diff2));
				} else {
					sb.append((char) (START_POS + diff1));
				}
			} else {
				sb.append((char) (inputChar + keyChar));
			}
		}
		String output 		= sb.toString();
		return convert2XmlReadable(output);
	}

	/**
	 * 解密
	 * @param input
	 * @param key
	 * @return
	 */
	public static String decrypt(final String input, final String key) {
		if (input == null || key == null) {
			return "";
		}
		String real_input 	= convertFromXml(input);
		String useKey 		= key;
		float value 		= (float) real_input.length() / (float) key.length();
		if (value > 1.0f) {
			for (float i = 0; i < value; i++) {
				useKey += useKey;
			}
		}
		
		StringBuffer sb 	= new StringBuffer(real_input.length());
		for (int i = 0; i < real_input.length(); i++) {
			char inputChar 	= real_input.charAt(i);
			char keyChar 	= useKey.charAt(i);

			if (inputChar - keyChar < START_POS) {
				int diff1 = keyChar - (inputChar - START_POS);
				if (END_POS - diff1 < START_POS) {
					int diff2 = diff1 - (END_POS - START_POS);
					sb.append((char) (END_POS - diff2));
				} else {
					sb.append((char) (END_POS - diff1));
				}
			} else {
				sb.append((char) (inputChar - keyChar));
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	private static String convertFromXml(final String input) {
		if (input == null)
			return "";

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == XML_DELIMITER) {
				char c_plus = input.charAt(i + 1);
				sb.append((char) (c_plus - 1));
				i++;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	private static String convert2XmlReadable(final String input) {
		if (input == null)
			return "";

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (isXmlMarkup(c)) {
				sb.append((char) XML_DELIMITER);
				sb.append((char) (c + 1)); // in ascii table, all those markup + 1 is xml readable
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isXmlMarkup(char c) {
		return c == '&' || c == '<' || c == '>' || c == '"' || c == '\'';
	}
	
	
	public static void main(String[] args){
		/**
		 * {nonce_str=zGAph2JWlnL9cObOMYLwC2VCBXgbWfwZ, op_user_id=1287785301, out_trade_no=160204141609732, out_refund_no=R1454569039889, appid=wxb380caea694fc3f2, total_fee=1, refund_fee=1, sign=55D0085C770CA7581432F10CFF09A1B1, mch_id=1287785301}
		 */
		/*
		Map<String,String> map	= new HashMap<String,String>();
		map.put("nonce_str","zGAph2JWlnL9cObOMYLwC2VCBXgbWfwZ");
		map.put("op_user_id","1287785301");
		map.put("out_trade_no","160204141609732");
		map.put("out_refund_no","R1454569039889");
		map.put("appid","wxb380caea694fc3f2");
		map.put("total_fee","1");
		map.put("refund_fee","1");
		map.put("mch_id","1287785301");
		
		String key	= "&key=ddddddddddnnnnnnnnnrrrrrrrrr2304";
		System.out.println(filter_and_sort(map));
		System.out.println(md5_sign(map, key, null));
		*/
		//B22387AB33F95789F67FD461E1BA5FFF
		//b22387ab33f95789f67fd461e1ba5fff
		//B22387AB33F95789F67FD461E1BA5FFF
		String name	= "我是人";
		System.out.println(MD5Utils.md5(name));
		System.out.println(md5(name));		
	}
}