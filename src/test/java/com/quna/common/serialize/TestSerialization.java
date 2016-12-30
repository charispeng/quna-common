package com.quna.common.serialize;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import sun.reflect.ReflectionFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.quna.common.serialize.kryo.KryoSerialization;

public class TestSerialization {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SerializationException{
		User user	= new User("username","100");
		String json	= JSONObject.toJSONString(user);
		System.out.println(json);
		
		ReflectionFactory feflectionFactory	= ReflectionFactory.getReflectionFactory();
		Constructor constructor				= feflectionFactory.newConstructorForSerialization(User.class, Object.class.getDeclaredConstructor(new Class[]{}));
		constructor.setAccessible(true);
		Object object						= constructor.newInstance();
		
//		ParserConfig parserConfig				= ParserConfig.global;
//		ObjectDeserializer objectDeserializer	= parserConfig.createJavaBeanDeserializer(User.class, String.class);
//		parserConfig.getDerializers().put(User.class, objectDeserializer);
		
		Object object2							= JSONObject.parseObject(json, User.class);
		System.out.println(object2);
		
		
		Serialization serialzation			= new KryoSerialization();
		byte[] bytes						= serialzation.serialize(user);
		User user2							= serialzation.deserialize(bytes,User.class);
		System.out.println(user2);
	}
}
