package com.quna.common.serialize.protostuff;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class ProtostuffUtils {
	private static Map<Class<?>,Schema<?>> serializationMap	= new ConcurrentHashMap<Class<?>,Schema<?>>();
	private static LinkedBuffer linkedBuffer				= LinkedBuffer.allocate(1024 * 1024);

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
	
	public static LinkedBuffer getLinkedBuffer(){
		return linkedBuffer;
	} 
}
