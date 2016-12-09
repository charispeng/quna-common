package com.quna.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
/**
 * Bean处理
 * @author 252054576@qq.com(charis)
 */
public class BeanUtils extends _Util{
    static final String GET_PREFIX 	= "get";
    static final String SET_PREFIX 	= "set";
    static final String IS_PREFIX 	= "is";
        
	/**
	 * map转换成Object
	 * @param map
	 * @param obj
	 */
	public static void map2Bean(Map<String,Object> map,Object object) throws Exception{
		BeanInfo beanInfo				= Introspector.getBeanInfo(object.getClass());
		PropertyDescriptor[] properties	= beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor property : properties){
			String name		= property.getName();			
			if(map.containsKey(name)){
				Object value	= map.get(name);
				// 得到property对应的setter方法  
				Method method	= property.getWriteMethod();
				value			= TypeUtils.cast(value, property.getPropertyType());
				method.invoke(object,value);
			}
		}
	}
	
	/**
	 * map转换成Object
	 * @param map
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <A> A map2Bean(Map<String,Object> map,Class<A> clazz) throws Exception{
		BeanInfo beanInfo				= Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] properties	= beanInfo.getPropertyDescriptors();
		A object	= clazz.newInstance();
		for(PropertyDescriptor property : properties){
			String name		= property.getName();
			
			if(map.containsKey(name)){
				Object value	= map.get(name);				
				// 得到property对应的setter方法  
				Method method	= property.getWriteMethod();
				value			= TypeUtils.cast(value, property.getPropertyType());
				method.invoke(object,value);
			}
		}
		return object;
	}
	
	/**
	 * map 转换成Bean
	 * @param map
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static void map2BeanByDeclaredField(Map<String,Object> map,Object object) throws Exception{
		Field[] fields	= object.getClass().getDeclaredFields();
		for(Field field : fields){
			String fieldName	= field.getName();
			
			if(map.containsKey(fieldName)){
				Object value	= map.get(fieldName);
				if(!field.isAccessible()){
					field.setAccessible(true);
				}
				value			= TypeUtils.cast(value, field.getGenericType());
				field.set(object, value);
			}
		}
	}
	
	/**
	 * map转换成Object
	 * @param map
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <A> A map2BeanByDeclaredField(Map<String,Object> map,Class<A> clazz) throws Exception{
		A object		= clazz.newInstance();
		Field[] fields	= clazz.getDeclaredFields();
		for(Field field : fields){
			String fieldName	= field.getName();
			
			if(map.containsKey(fieldName)){
				Object value	= map.get(fieldName);
				if(!field.isAccessible()){
					field.setAccessible(true);
				}
				value			= TypeUtils.cast(value, field.getGenericType());
				field.set(object, value);
			}
		}
		return object;
	}
	
	/**
	 * map转换成Object
	 * @param map
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static <A> A map2BeanByDeclaredMethod(Map<String,Object> map,A object) throws Exception{
		Class<? extends Object> clazz		= object.getClass();
		for(Entry<String,Object> entry : map.entrySet()){
			String key		= entry.getKey();			
			Object value	= entry.getValue();
			key				= key.startsWith(IS_PREFIX) ? key.replaceAll(IS_PREFIX, "") : key;			
			key				= key.substring(0, 1).toUpperCase() + key.substring(1);
			String methodName= SET_PREFIX + key;
			Method[] methods= clazz.getDeclaredMethods();
			for(Method method:methods){
				if(method.getName().equals(methodName)){
					if(!method.isAccessible()){
						method.setAccessible(true);
					}
					value	= TypeUtils.cast(value, method.getGenericParameterTypes().length > 0 ? method.getGenericParameterTypes()[0] : null);
					method.invoke(object, value);
					break;
				}
			}
		}
		return object;
	}
	/**
	 * map转换成Object
	 * @param map
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <A> A map2BeanByDeclaredMethod(Map<String,Object> map,Class<A> clazz) throws Exception{
		A object			= clazz.newInstance();
		for(Entry<String,Object> entry : map.entrySet()){
			String key		= entry.getKey();			
			Object value	= entry.getValue();
			key				= key.startsWith(IS_PREFIX) ? key.replaceAll(IS_PREFIX, "") : key;			
			key				= key.substring(0, 1).toUpperCase() + key.substring(1);
			String methodName= SET_PREFIX + key;
			Method[] methods= clazz.getDeclaredMethods();
			for(Method method:methods){
				if(method.getName().equals(methodName)){
					if(!method.isAccessible()){
						method.setAccessible(true);
					}
					value	= TypeUtils.cast(value, method.getGenericParameterTypes().length > 0 ? method.getGenericParameterTypes()[0] : null);
					method.invoke(object, value);
					break;
				}
			}
		}
		return object;
	}
}
