package com.quna.common.serialize.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quna.common.serialize.Serialization;

public class FastjsonSerialzation implements Serialization {
	
	public static boolean checkZeroArgConstructor(Class<?> clazz){
		try {
			clazz.getDeclaredConstructor();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public byte[] serialize(Object object) {
		return JSONObject.toJSONBytes(object);
	}

	@Override
	public Object deserialize(byte[] bytes) throws ClassNotFoundException {
		//return JSONObject.parse(bytes);
		throw new ClassNotFoundException();
	}

	@Override
	public <T> T deserialize(byte[] bytes,Class<T> clazz) {
		return JSON.parseObject(bytes, clazz);
	}

}
