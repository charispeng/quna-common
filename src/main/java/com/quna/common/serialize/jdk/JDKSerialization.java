package com.quna.common.serialize.jdk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.quna.common.serialize.Serialization;
import com.quna.common.serialize.SerializationException;

public class JDKSerialization implements Serialization{

	@Override
	public byte[] serialize(Object object) throws SerializationException {
		ByteArrayOutputStream baos	= null;
		ObjectOutputStream oos		= null;
		try{
			baos					= new ByteArrayOutputStream();
			oos						= new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.flush();
			baos.flush();
			return baos.toByteArray();
		}catch(IOException e){
			throw new SerializationException(e);
		}finally{
			if(null != baos){try{baos.close();}catch(Exception e){}}
			if(null != oos){try{oos.close();}catch(Exception e){}}
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException{
		ByteArrayInputStream bais	= null;
		ObjectInputStream ois		= null;
		try{
			bais					= new ByteArrayInputStream(bytes);
			ois						= new ObjectInputStream(bais);
			return ois.readObject();
		}catch(IOException e){
			throw new SerializationException(e);
		}catch(ClassNotFoundException e){
			throw new SerializationException(e);
		}finally{
			if(null != ois){try{ois.close();}catch(Exception e){}}
			if(null != bais){try{bais.close();}catch(Exception e){}}
		}		
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException{
		Object object	= deserialize(bytes);
		return null != object ? clazz.cast(object) : null;
	}

}
