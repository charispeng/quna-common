package com.quna.common.serialize.protostuff.smile;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.SmileIOUtil;
import com.quna.common.serialize.Serialization;
import com.quna.common.serialize.SerializationException;
import com.quna.common.serialize.protostuff.ProtostuffUtils;

public class ProtostuffSmileSerialization implements Serialization {
	private LinkedBuffer buffer		= LinkedBuffer.allocate(1024);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public byte[] serialize(Object object) throws SerializationException{
        Schema schema 			= ProtostuffUtils.getSchema(object.getClass());
        synchronized (buffer) {
        	byte[] protostuff  	= SmileIOUtil.toByteArray(object, schema, true, buffer);
            buffer.clear();
            return protostuff;
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		throw new SerializationException("This method is not supported!");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException {
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
        try{
	        Schema<T> schema 					= (Schema<T>)ProtostuffUtils.getSchema(clazz);
	        SmileIOUtil.mergeFrom(bytes, instance, schema, true);
	        return instance;
        }catch(IOException e){
        	throw new SerializationException(e);
        }
        
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
