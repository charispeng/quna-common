package com.quna.common.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
/**
 * 
 * 
 * <pre>
 * <b></b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2015年9月23日 下午12:07:56
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2015年9月23日 下午12:07:56    252054576@qq.com  new file.
 * </pre>
 */
public class ClassUtils {
	/**
	 * 获取class自身的超类和接口
	 * @param clazz
	 * @return
	 */
	public static HashSet<Class<?>> getSelfInterfaceAndSuperClass(Class<?> clazz){
		HashSet<Class<?>> ret	= new HashSet<Class<?>>();
		if(clazz == null) return ret;
		
		ret.add(clazz);
		
		if(null != clazz.getSuperclass()){
			ret.add(clazz.getSuperclass());
		}
		
		if(null != clazz.getInterfaces() && clazz.getInterfaces().length > 0){
			ret.addAll(Arrays.asList(clazz.getInterfaces()));
		}
		return ret;
	}
	
	/**
	 * 获取class的所有实现的超类和接口
	 * @param clazz
	 * @return
	 */
	public static HashSet<Class<?>> getAllInterfaceAndSuperClass(Class<?> clazz){
		HashSet<Class<?>> ret	= new HashSet<Class<?>>();
		if(clazz == null) return ret;
		
		ret.add(clazz);
		
		if(null != clazz.getInterfaces() && clazz.getInterfaces().length > 0){
			Class<?>[] interfaces	= clazz.getInterfaces();
			ret.addAll(Arrays.asList(interfaces));
			for(Class<?> _interface : interfaces){
				ret.addAll(getAllInterfaceAndSuperClass(_interface));
			}
		}
		
		if(null != clazz.getSuperclass()){
			Class<?> superClass	= clazz.getSuperclass();
			ret.add(superClass);
			
			ret.addAll(getAllInterfaceAndSuperClass(superClass));
		}
		return ret;
	}
	
	public static Set<Class<?>> getAllClass(String pkName) throws IOException{
		Set<Class<?>> classes	= new HashSet<Class<?>>();
		Set<String> classNames	= getAllClassName(pkName);
		for(String className : classNames){
			try{
				Class<?> clazz	= Class.forName(className);
				classes.add(clazz);
			}catch(Throwable e){
				System.err.println(String.format("%s不能转成class对象,%s", className,e));
			}
		}
		return classes;
	}
	
	public static Set<Class<?>> getAllClass(Class<?> clazz) throws IOException{
		return getAllClass(clazz.getPackage().getName());
	}
	
	public static Set<String> getAllClassName(Class<?> clazz) throws IOException{
		return getAllClassName(clazz.getPackage().getName());
	}
	
	public static Set<String> getAllClassName(String pkName) throws IOException{
		try{
			Class<?> clazz		= Class.forName(pkName);
			pkName				= clazz.getPackage().getName();
		}catch(Throwable e){}
		
		Set<String> classes		= new HashSet<String>();	
		pkName					= pkName.replaceAll("\\.", "/");
		Enumeration<URL> dirs	= Thread.currentThread().getContextClassLoader().getResources(pkName);
		while(dirs.hasMoreElements()){
			URL url				= dirs.nextElement();
			String protocol		= url.getProtocol();
			if(protocol.equals("file")){
				String filePath	= url.getPath();
				classes.addAll(getAllClassNameByFileProtocol(filePath,pkName));
			}else if(protocol.equals("jar")){
				String filePath	= url.getPath().replaceAll("file:", "");
				classes.addAll(getAllClassNameByJarProtocol(filePath,pkName));
			}
		}
		
		return classes;
	}
	
	public static Set<String> getAllClassNameByJarProtocol(String filePath,String pkName) throws IOException{
		Set<String> classes		= new HashSet<String>();
		filePath				= filePath.substring(0,filePath.indexOf("!"));
		JarFile jarFile			= new JarFile(new File(filePath));
		Enumeration<JarEntry> entries	= jarFile.entries();
		while(entries.hasMoreElements()){
			JarEntry entry 		= entries.nextElement();
			String className	= entry.getName();
			if(className.startsWith(pkName) && className.endsWith(".class")){
				className		= className.replaceFirst("\\.class", "").replaceAll("/", ".");
				classes.add(className);
			}
		}
		jarFile.close();
		return classes;
	}
	
	public static Set<String> getAllClassNameByFileProtocol(String filePath,String pkName){
		Set<String> classes		= new HashSet<String>();		
		String osName			= System.getProperty("os.name");
		String classPath		= filePath.substring(0,filePath.length() - pkName.length());
		try{
			if(osName.indexOf("Windows") != -1 && classPath.startsWith("/")){
				classPath		= classPath.substring(1);
			}
		}catch(Exception e){
			String javaClassPath= System.getProperty("java.class.path");
			if(javaClassPath.indexOf(";") != -1){
				classPath		= javaClassPath.substring(0,javaClassPath.indexOf(";"));
			}
		}
		String packagePath		= classPath + "/" + pkName;
		File file				= new File(packagePath);
		List<String> classFiles	= getAllClassName(classPath,file);
		for(String className : classFiles){
			classes.add(className);
		}
		return classes;
	}
	public static List<String> getAllClassName(String classPath,File dic){
		Path tmpPath			= Paths.get(classPath);
		if(!dic.isDirectory()){return new ArrayList<String>();}
		List<String> ret		= new ArrayList<String>();
		File[] files			= dic.listFiles();
		for(File file: files){
			if(file.isDirectory()){
				ret.addAll(getAllClassName(classPath,file));
			}else if(file.getName().endsWith(".class")){
				Path path		= Paths.get(file.getPath());
				ret.add(tmpPath.relativize(path).toString().replaceFirst("\\.class", "").replaceAll("\\\\", "."));
			}
		}
		return ret;
	}
}
