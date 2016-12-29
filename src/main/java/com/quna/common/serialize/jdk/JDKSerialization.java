package com.quna.common.serialize.jdk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.quna.common.serialize.Serialization;

public class JDKSerialization implements Serialization{

	@Override
	public byte[] serialize(Object object) throws IOException {
		ByteArrayOutputStream baos	= null;
		ObjectOutputStream oos		= null;
		try{
			baos					= new ByteArrayOutputStream();
			oos						= new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.flush();
			baos.flush();
			return baos.toByteArray();
		}finally{
			if(null != baos){baos.close();}
			if(null != oos){oos.close();}
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws IOException,ClassNotFoundException {
		ByteArrayInputStream bais	= null;
		ObjectInputStream ois		= null;
		try{
			bais					= new ByteArrayInputStream(bytes);
			ois						= new ObjectInputStream(bais);
			return ois.readObject();
		}finally{
			if(null != ois){ois.close();}
			if(null != bais){bais.close();}
		}		
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException,ClassNotFoundException {
		Object object	= deserialize(bytes);
		if(null != object){
			return clazz.cast(object);
		}
		throw new ClassNotFoundException();
	}

}
