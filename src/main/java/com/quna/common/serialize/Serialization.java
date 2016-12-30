package com.quna.common.serialize;

public interface Serialization {
	byte[] serialize(Object object) throws SerializationException;
	
	Object deserialize(byte[] bytes) throws SerializationException;
	
	<T> T deserialize(byte[] bytes,Class<T> clazz) throws SerializationException;
}