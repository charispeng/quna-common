package com.quna.common.utils;

import java.util.Arrays;
import java.util.HashSet;
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
}
