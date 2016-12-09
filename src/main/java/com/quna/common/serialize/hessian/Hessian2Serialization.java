package com.quna.common.serialize.hessian;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import com.quna.common.serialize.Serialization;

public class Hessian2Serialization implements Serialization {

	@Override
	public byte[] serialize(Object object) throws IOException {
		ByteArrayOutputStream os	= null;
		Hessian2Output ho 			= null;
		try{
			os		= new ByteArrayOutputStream();
			ho		= new Hessian2Output(os);
			ho.setSerializerFactory(SerializerFactory.createDefault());
			ho.writeObject(object);
			ho.flush();
			return os.toByteArray();
		}finally{
			try{ho.close();}catch(Exception e){}
			try{os.close();}catch(Exception e){}
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws IOException {
		ByteArrayInputStream is	= null;
		Hessian2Input hi			= null;
		try{
			is	= new ByteArrayInputStream(bytes);
			hi	= new Hessian2Input(is);
			hi.setSerializerFactory(SerializerFactory.createDefault());
			return hi.readObject();
		}finally{
			try{hi.close();}catch(Exception e){}
			try{is.close();}catch(Exception e){}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException {
		return (T)deserialize(bytes);
	}

}
