package com.quna.common.serialize.jackjson;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.quna.common.serialize.Serialization;


public class JackjsonSerialization implements Serialization {

	private static ObjectMapper objectMapper	= new ObjectMapper();
	
	@Override
	public byte[] serialize(Object object) throws IOException {
		return objectMapper.writeValueAsBytes(object);
	}

	@Override
	public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		//return objectMapper.readValue(bytes, Object.class);
		throw new ClassNotFoundException();
	}

	@Override
	public <T> T deserialize(byte[] bytes,Class<T> clazz) throws IOException, ClassNotFoundException {
		return objectMapper.readValue(bytes,clazz);
	}

}