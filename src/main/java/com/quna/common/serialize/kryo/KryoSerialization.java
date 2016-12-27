package com.quna.common.serialize.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.KryoObjectInput;
import com.esotericsoftware.kryo.io.KryoObjectOutput;
import com.esotericsoftware.kryo.io.Output;
import com.quna.common.serialize.Serialization;

import de.javakaffee.kryoserializers.KryoReflectionFactorySupport;

public class KryoSerialization implements Serialization {
	
	private static final Kryo KRYO	= new KryoReflectionFactorySupport();
	
	@Override
	public byte[] serialize(Object object) throws IOException {
		ByteArrayOutputStream os	= null;
		Output output				= null;
		KryoObjectOutput ko 		= null;
		try{
			os						= new ByteArrayOutputStream();
			output					= new Output(os);	
			ko						= new KryoObjectOutput(KRYO,output);
			ko.writeObject(object);
			ko.flush();			
			return os.toByteArray();
		}finally{
			try{output.close();}catch(Exception e){}
			try{ko.close();}catch(Exception e){}
			try{os.close();}catch(Exception e){}
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		KryoObjectInput ki		= null;
		ByteArrayInputStream in	= null;
		Input input				= null;
		try{
			in					= new ByteArrayInputStream(bytes);
			input				= new Input(in);
			ki					= new KryoObjectInput(KRYO,input);
			return ki.readObject();
		}finally{
			try{ki.close();}catch(Exception e){}
			try{input.close();}catch(Exception e){}
			try{in.close();}catch(Exception e){}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes,Class<T> clazz) throws IOException, ClassNotFoundException {
		return (T)deserialize(bytes);
	}
}
