package com.quna.common.serialize;

import java.io.IOException;

public interface Serialization {
	
	byte[] serialize(Object object) throws IOException;
	
	Object deserialize(byte[] bytes) throws IOException,ClassNotFoundException;
	
	<T> T deserialize(Class<T> clazz,byte[] bytes) throws IOException,ClassNotFoundException;
}
