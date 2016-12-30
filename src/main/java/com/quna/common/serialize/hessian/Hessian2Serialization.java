package com.quna.common.serialize.hessian;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import com.quna.common.serialize.Serialization;
import com.quna.common.serialize.SerializationException;

public class Hessian2Serialization implements Serialization {

	private static SerializerFactory SERIALZER_FACTORY	= SerializerFactory.createDefault();
	
	@Override
	public byte[] serialize(Object object) throws SerializationException {
		ByteArrayOutputStream os	= null;
		Hessian2Output ho 			= null;
		try{
			os		= new ByteArrayOutputStream();
			ho		= new Hessian2Output(os);
			ho.setSerializerFactory(SERIALZER_FACTORY);
			ho.writeObject(object);
			ho.flush();
			return os.toByteArray();
		}catch(IOException e){
			throw new SerializationException(e);
		}finally{
			try{ho.close();}catch(Exception e){}
			try{os.close();}catch(Exception e){}
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException{
		ByteArrayInputStream is	= null;
		Hessian2Input hi			= null;
		try{
			is	= new ByteArrayInputStream(bytes);
			hi	= new Hessian2Input(is);
			hi.setSerializerFactory(SERIALZER_FACTORY);
			return hi.readObject();
		}catch(IOException e){
			throw new SerializationException(e);
		}finally{
			try{hi.close();}catch(Exception e){}
			try{is.close();}catch(Exception e){}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes,Class<T> clazz) throws SerializationException {
		return (T)deserialize(bytes);
	}

}
