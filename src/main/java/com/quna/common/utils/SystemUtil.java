package com.quna.common.utils;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.quna.common.exception.runtime.QunaRuntimeException;

/**
 * 
 * <pre>
 * <b></b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2014-1-1 上午10:00:01
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2014-01-01 10:00:01     252054576@qq.com
 *         new file.
 * </pre>
 */
public class SystemUtil extends _Util{
	/**
	 * 默认编码方法
	 */
	private static final String DEFAULT_ENCODING = "GBK";

	/**
	 * 获取操作系统名称
	 * @return
	 */
	public static String getOS() {
		return System.getProperty("os.name");
	}
	
	/**
	 * 通过CMD命令打包
	 * @param jarPath		保存的文件目录
	 * @param listName		文件列表名称
	 * @param classPath		class文件地址
	 * @return
	 */
	public static String jar(String jarPath, String listName, String classPath) {
		return jar(jarPath,listName,classPath,DEFAULT_ENCODING);
	}
	
	/**
	 * 通过CMD命令打包
	 * @param jarPath		保存的文件目录
	 * @param listName		文件列表名称
	 * @param classPath		class文件地址
	 * @param encoding		系统编码
	 * @return
	 */
	public static String jar(String jarPath, String listName, String classPath,String encoding) {
		//构建命令行
		StringBuffer cmd = new StringBuffer("jar cvfm ").append(jarPath).append(" ").append(listName).append(" ").append("-C").append(" ").append(classPath).append(" ").append(".");
		if(!FileUtils.createFile(new File(jarPath))){
			throw new QunaRuntimeException("不能创建文件!");
		}
		String delJarCMD = "del " + jarPath;
		execSysCMD(delJarCMD, encoding);
		return execSysCMD(cmd.toString(), encoding);
	}

	/**
	 * 执行CMD命令
	 * @param cmd		命令
	 * @param encoding	编码
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String execSysCMD(String cmd, String encoding) {
		String retString = "";
		try {
			cmd = "cmd /c " + cmd;
			System.out.println(cmd.toString());
			Process process = Runtime.getRuntime().exec(cmd);
			retString 		= FileUtils.readInputStreamToString(process.getInputStream(), encoding);
			process.waitFor();
		} catch (Exception e) {
			throw new QunaRuntimeException(e);
		} finally {
			return retString;
		}
	}
	
	/**
	 * 获取系统的所有属性
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> getSystemProperties(){
		Map<String,String> systemProMap	= new HashMap<String,String>();
		Properties props 	= System.getProperties();
        Enumeration keys 	= props.keys();
        while(keys.hasMoreElements()){
            String key	= keys.nextElement().toString();
            String value= props.get(key).toString();
            
            systemProMap.put(key, value);
        }
        return systemProMap;
	}

	public static void main(String[] args) {
		String jarName = "newjar/common-util.jar";
		String listName = "META-INF/MANIFEST.MF";
		String classPath = "target/classes";
		System.out.println(SystemUtil.jar(jarName, listName, classPath));
	}
}
