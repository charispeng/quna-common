package com.quna.common.serialize;

import java.io.IOException;

public interface Serialization {
	
	byte[] serialize(Object object) throws IOException;
	
	Object deserialize(byte[] bytes) throws IOException,ClassNotFoundException;
	
	<T> T deserialize(byte[] bytes,Class<T> clazz) throws IOException,ClassNotFoundException;
}