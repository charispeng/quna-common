package com.quna.common.serialize.protostuff;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sun.reflect.ReflectionFactory;

import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class ProtostuffUtils {
	private static Map<Class<?>,Schema<?>> serializationMap		= new ConcurrentHashMap<Class<?>,Schema<?>>();
	private static Map<Class<?>,Constructor<?>> constructorMap	= new ConcurrentHashMap<Class<?>,Constructor<?>>();
	private static ReflectionFactory reflectionFactory			= ReflectionFactory.getReflectionFactory();
	
	public static Schema<?> getSchema(Class<?> clazz){
		Schema<?> schema	= serializationMap.get(clazz);
		if(null == schema){
			synchronized (serializationMap) {
				 schema		= serializationMap.get(clazz);
				 if(null == schema){
					 schema	= (Schema<?>) RuntimeSchema.getSchema(clazz);
					 serializationMap.put(clazz, schema);
					 return schema;
				 }
			}
		}
		return schema;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> getConstructor(Class<T> clazz) throws NoSuchMethodException, SecurityException{
		Constructor<?> constructor	= constructorMap.get(clazz);
		if(null == constructor){
			synchronized (constructorMap) {
				constructor			= constructorMap.get(clazz);
				 if(null == constructor){
					 constructor	= reflectionFactory.newConstructorForSerialization(clazz, Object.class.getDeclaredConstructor(new Class<?>[0]));
					 constructorMap.put(clazz, constructor);
					 return (Constructor<T>)constructor;
				 }
			}
		}
		return ((Constructor<T>)constructor);
	}
}
