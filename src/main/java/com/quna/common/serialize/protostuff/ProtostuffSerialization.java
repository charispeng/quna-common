package com.quna.common.serialize.protostuff;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.quna.common.serialize.Serialization;
import com.quna.common.serialize.SerializationException;

public class ProtostuffSerialization implements Serialization {
	private LinkedBuffer buffer		= LinkedBuffer.allocate(1024);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public byte[] serialize(Object object) throws SerializationException{
        Schema schema 			= ProtostuffUtils.getSchema(object.getClass());
        synchronized (buffer) {
        	byte[] protostuff 	= ProtostuffIOUtil.toByteArray(object, schema, buffer);
	   	    buffer.clear();
	   	   return protostuff;
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException{
		throw new SerializationException("This method is not supported!");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException{
        T instance 								= null;
        try {
			instance 							= clazz.newInstance();
        }catch(Exception e){
        	try{
	        	Constructor<T> constructor		= ProtostuffUtils.getConstructor(clazz);
	    		instance						= constructor.newInstance(new Object[0]);
	        } catch (InstantiationException e1) {
				throw new SerializationException(e1.getMessage());
			} catch (IllegalAccessException e1) {
				throw new SerializationException(e1.getMessage());
			} catch (IllegalArgumentException e1) {
				throw new SerializationException(e1.getMessage());
			} catch (InvocationTargetException e1) {
				throw new SerializationException(e1.getMessage());
			} catch (NoSuchMethodException e1) {
				throw new SerializationException(e1.getMessage());
			} catch (SecurityException e1) {
				throw new SerializationException(e1.getMessage());
			}
        }
        Schema<T> schema 						= (Schema<T>)ProtostuffUtils.getSchema(clazz);
        ProtostuffIOUtil.mergeFrom(bytes, instance, schema);
        return instance;
        
        /**
         * //处理List
			if(clazz.isAssignableFrom(Collection.class)){
				Schema<T> schema 	= RuntimeSchema.getSchema(clazz);
		        T result 			= (T)ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(bytes), schema);
		        return result;
			}
         */
	}
}
