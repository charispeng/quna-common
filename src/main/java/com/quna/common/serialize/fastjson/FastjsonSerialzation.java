package com.quna.common.serialize.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quna.common.serialize.Serialization;

public class FastjsonSerialzation implements Serialization {
	
	public static boolean checkZeroArgConstructor(Class<?> clazz){
		try {
			clazz.getDeclaredConstructor();
			return true;
		} catch (NoSuchMethodException | SecurityException e) {
			return false;
		}
	}
	
	@Override
	public byte[] serialize(Object object) {
		return JSONObject.toJSONBytes(object);
	}

	@Override
	public Object deserialize(byte[] bytes) {
		return JSONObject.parse(bytes);
	}

	@Override
	public <T> T deserialize(Class<T> clazz, byte[] bytes) {
		return JSON.parseObject(bytes, clazz);
	}

}
