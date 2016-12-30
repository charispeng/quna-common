package com.quna.common.serialize.jackjson;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.quna.common.serialize.Serialization;
import com.quna.common.serialize.SerializationException;

public class JackjsonSerialization implements Serialization {

	protected static ObjectMapper objectMapper	= new ObjectMapper();
	
	protected synchronized static void setObjectMapper(ObjectMapper objectMapper){
		JackjsonSerialization.objectMapper		= objectMapper;
	}
	
	@Override
	public byte[] serialize(Object object) throws SerializationException{
		try {
			return objectMapper.writeValueAsBytes(object);
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		throw new SerializationException("This method is not supported!");
	}

	@Override
	public <T> T deserialize(byte[] bytes,Class<T> clazz) throws SerializationException {
		try {
			return objectMapper.readValue(bytes,clazz);
		}catch (IOException e) {
			throw new SerializationException(e);
		}
	}
}
