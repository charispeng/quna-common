package com.quna.common.beans;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.quna.common.utils.StringUtils;
import com.quna.common.utils.TypeUtils;

public class BeanInfo {
	static final String GET_PREFIX 	= "get";
    static final String SET_PREFIX 	= "set";
    static final String IS_PREFIX 	= "is";
    
	public static BeanInfo getInstance(Class<?> clazz){
		return new BeanInfo(clazz);
	}
	//对象的class属性
	private Class<?> clazz;
	//所有的字段类
	private PropertyDescriptor[] propertyDescriptors;
	//所有的字段类
	private List<PropertyDescriptor> propertyDescriptorList;
	//字段对应的读取方法
	private Map<String,Method> fieldReadMethod;
	//字段对应的写入方法
	private Map<String,Method> fieldWriteMethod;
	
	public BeanInfo(Class<?> clazz){
		this.clazz	= clazz;
		this.fieldReadMethod		= new HashMap<String,Method>();
		this.fieldWriteMethod		= new HashMap<String,Method>();
		this.propertyDescriptorList	= new ArrayList<PropertyDescriptor>();
		
		//加载字段对应的设置和读取方法
		this.setPropertyDescriptors();
	}

	private void setPropertyDescriptors() {
		//添加子类和父类的所有字段
		Class<?> cls	= this.clazz;
		while(null != cls){
			addPropertyDescriptors(cls);
			cls			= cls.getSuperclass();
		}		
		this.propertyDescriptors	= propertyDescriptorList.toArray(new PropertyDescriptor[propertyDescriptorList.size()]);
	}	
	
	private void addPropertyDescriptors(Class<?> clazz) {
		//设置字段
		Field[] fields	= clazz.getDeclaredFields();
		for(Field field : fields){
			//如果子类里存在某个字段,父类的字段不再添加
			String fieldName	= field.getName();
			if(fieldReadMethod.containsKey(fieldName) || fieldWriteMethod.containsKey(fieldName)){
				continue;
			}
			
			PropertyDescriptor propertyDescriptor	= new PropertyDescriptor();
			propertyDescriptor.setField(field);
			propertyDescriptorList.add(propertyDescriptor);
		}
		
		//设置方法
		for(PropertyDescriptor propertyDescriptor : propertyDescriptorList){
			Field field				= propertyDescriptor.getField();
			String fieldName		= field.getName();
			Type fieldType			= field.getGenericType();
			String writeMethodName	= SET_PREFIX + StringUtils.capitalize(fieldName);
			String readMethodName	= GET_PREFIX + StringUtils.capitalize(fieldName);
			
			Method[] methods		= clazz.getDeclaredMethods();
			for(Method method : methods){
				String methodName	= method.getName();
				if(methodName.equals(writeMethodName)){
					Type paramType	= method.getGenericParameterTypes().length > 0 ? method.getGenericParameterTypes()[0] : null;
					if(fieldType == paramType){
						if(!method.isAccessible()){
							method.setAccessible(true);
						}
						propertyDescriptor.setWriteMethod(method);
					}
				}else if(methodName.equals(readMethodName)){
					Type returnType	= method.getGenericReturnType();
					if(fieldType == returnType){
						if(!method.isAccessible()){
							method.setAccessible(true);
						}
						propertyDescriptor.setReadMethod(method);
					}
				}
			}
			
			//Set方法不存在的情况,如boolean 类型的 is开头的属性
			if(propertyDescriptor.getWriteMethod() == null && fieldName.startsWith(IS_PREFIX) && (fieldType == Boolean.class || fieldType == boolean.class)){
				writeMethodName			= SET_PREFIX + StringUtils.capitalize(fieldName).replaceFirst(IS_PREFIX, "");		//isTrue setTrue;
				for(Method method : methods){
					String methodName	= method.getName();
					Type paramType		= method.getGenericParameterTypes().length > 0 ? method.getGenericParameterTypes()[0] : null;
					if(methodName.equals(writeMethodName) && (paramType == Boolean.class || paramType == boolean.class)){
						propertyDescriptor.setWriteMethod(method);
					}
				}
			}
			
			//将读取方法设置入map
			fieldReadMethod.put(propertyDescriptor.getField().getName(), propertyDescriptor.getReadMethod());
			//将写入方法设置入map
			fieldWriteMethod.put(propertyDescriptor.getField().getName(), propertyDescriptor.getWriteMethod());
		}
	}
	
	/**
	 * 获取所有字段
	 * @return
	 */
	public PropertyDescriptor[] getPropertyDescriptors() {
		return propertyDescriptors;
	}
	/**
	 * 获取所有字段名称对应的读取方法
	 * @return
	 */
	public Map<String, Method> getFieldReadMethod() {
		return fieldReadMethod;
	}
	/**
	 * 获取所有字段名称对应的写入方法
	 * @return
	 */
	public Map<String, Method> getFieldWriteMethod() {
		return fieldWriteMethod;
	}
	
	/**
	 * 设置某个字段入对象
	 * 
	 * @param object
	 * @param fieldName
	 * @param values
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void setFieldValue(Object object,String fieldName,Object...values) throws Exception{
		if(object.getClass() != clazz){
			throw new Exception("object class not equal!");
		}
		Method writeMethod	= getFieldWriteMethod().get(fieldName);
		if(null == writeMethod){return ;}
		
		//=========================================
		int paramCount		= writeMethod.getParameterTypes().length;
		int valueLength		= null == values ? 0 : values.length;		
		if(valueLength != paramCount){
			throw new Exception("params length is not equal!");
		}
		
		//转换类型
		if(paramCount > 0){
			//JDK8时才有用
			//Parameter[] params	= writeMethod.getParameters();
			Class<?>[] classes		= writeMethod.getParameterTypes();
			for(int i=0;i<paramCount;i++){
				//Parameter param	= params[i];
				//Class<?> clazz	= param.getType();
				Class<?> clazz	= classes[i];
				Object value	= values[i];
				
				if(getAllClass(value.getClass()).contains(clazz)){
					continue;
				}else{
					values[i]	= TypeUtils.cast(value, clazz);
				}
			}
		}
		writeMethod.invoke(object, values);
	}
	/**
	 * 读取某个字段的值
	 * @param object
	 * @param fieldName
	 * @param values
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Object getFieldValue(Object object,String fieldName,Object ... values) throws Exception{
		if(object.getClass() != clazz){
			throw new Exception("object class not equal!");
		}
		Method readMethod	= getFieldReadMethod().get(fieldName);
		if(null == readMethod){return null;}
		
		//======================================================
		int paramCount		= readMethod.getParameterTypes().length;
		int valueLength		= null == values ? 0 : values.length;		
		if(valueLength != paramCount){
			throw new Exception("params length is not equal!");
		}
		//转换类型
		if(paramCount > 0){
			//Parameter[] params	= readMethod.getParameters();
			Class<?>[] classes		= readMethod.getParameterTypes();
			for(int i=0;i<paramCount;i++){
				//Parameter param	= params[i];
				//Class<?> clazz	= param.getType();
				Class<?> clazz	= classes[i];
				Object value	= values[i];
				
				if(getAllClass(value.getClass()).contains(clazz)){
					continue;
				}else{
					values[i]	= TypeUtils.cast(value, clazz);
				}
			}
		}		
		return readMethod.invoke(object, values);
	}

	/**
	 * 获取class的所有超类和接口
	 * @param clazz
	 * @return
	 */
	public static HashSet<Class<?>> getAllClass(Class<?> clazz){
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
	
	@Override
	public String toString() {
		return "BeanInfo [clazz=" + clazz + ", propertyDescriptors="
				+ Arrays.toString(propertyDescriptors) + "]";
	}
}
