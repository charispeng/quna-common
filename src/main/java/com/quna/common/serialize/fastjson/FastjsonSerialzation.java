package com.quna.common.serialize.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quna.common.serialize.SerializationException;
import com.quna.common.serialize.Serialization;

public class FastjsonSerialzation implements Serialization {
	
	@Override
	public byte[] serialize(Object object) throws SerializationException{
		return JSONObject.toJSONBytes(object);
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException{
		throw new SerializationException("This method is not supported!");
	}

	@Override
	public <T> T deserialize(byte[] bytes,Class<T> clazz) throws SerializationException{
		return JSON.parseObject(bytes, clazz);
	}

}
