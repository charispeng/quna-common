package com.quna.common.serialize.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.quna.common.serialize.Serialization;

import de.javakaffee.kryoserializers.KryoReflectionFactorySupport;

public class KryoSerialization implements Serialization {	
	private Kryo KRYO				= new KryoReflectionFactorySupport();	
	@Override
	public byte[] serialize(Object object) throws IOException{ 
		ByteArrayOutputStream os	= null;
		Output output				= null;
		try{
			os						= new ByteArrayOutputStream();
			output					= new Output(os);
			synchronized (KRYO) {
				KRYO.writeClassAndObject(output,object);
				output.flush();
				os.flush();
				return os.toByteArray();
			}
		}finally{
			try{output.close();}catch(Exception e){}
			try{os.close();}catch(Exception e){}
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException{		
		ByteArrayInputStream in	= null;
		Input input				= null;
		try{
			in					= new ByteArrayInputStream(bytes);
			input				= new Input(in);
			synchronized (KRYO) {
				return KRYO.readClassAndObject(input);
			}
		}finally{
			try{input.close();}catch(Exception e){}
			try{in.close();}catch(Exception e){}
		}
	}
	
	@Override
	public <T> T deserialize(byte[] bytes,Class<T> clazz) throws IOException, ClassNotFoundException{
		return clazz.cast(deserialize(bytes));
	}
}
