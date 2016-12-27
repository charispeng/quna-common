package com.quna.common.serialize.protostuff;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import sun.reflect.ReflectionFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.quna.common.serialize.Serialization;

public class ProtostuffSerialization implements Serialization {
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public byte[] serialize(Object object) throws IOException {	
        Schema schema 		= ProtostuffUtils.getSchema(object.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        byte[] protostuff 	= null;
        try {
            protostuff 		= ProtostuffIOUtil.toByteArray(object, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("序列化(" + object.getClass() + ")对象(" + object + ")发生异常!", e);
        } finally {
            buffer.clear();
        }
        return protostuff;
	}

	@Override
	public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
       throw new ClassNotFoundException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        T instance 								= null;
        try {
			instance 							= clazz.newInstance();
        }catch(Exception e){
        	ReflectionFactory reflectionFactory	= ReflectionFactory.getReflectionFactory();
    		try {
    			Constructor<T>  constructor		= (Constructor<T>)reflectionFactory.newConstructorForSerialization(clazz, Object.class.getDeclaredConstructor());
				instance						= constructor.newInstance();
			} catch (InstantiationException e1) {
				throw new ClassNotFoundException(e1.getMessage());
			} catch (IllegalAccessException e1) {
				throw new ClassNotFoundException(e1.getMessage());
			} catch (IllegalArgumentException e1) {
				throw new ClassNotFoundException(e1.getMessage());
			} catch (InvocationTargetException e1) {
				throw new ClassNotFoundException(e1.getMessage());
			} catch (NoSuchMethodException e1) {
				throw new ClassNotFoundException(e1.getMessage());
			} catch (SecurityException e1) {
				throw new ClassNotFoundException(e1.getMessage());
			}
        }
        Schema<T> schema 	= (Schema<T>)ProtostuffUtils.getSchema(clazz);
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