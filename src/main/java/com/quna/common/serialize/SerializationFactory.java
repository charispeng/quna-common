package com.quna.common.serialize;

import com.quna.common.serialize.fastjson.FastjsonSerialzation;
import com.quna.common.serialize.hessian.Hessian2Serialization;
import com.quna.common.serialize.jackjson.JackjsonSerialization;
import com.quna.common.serialize.jackjson.smile.JackjsonSmileSerialization;
import com.quna.common.serialize.jdk.JDKSerialization;
import com.quna.common.serialize.kryo.KryoSerialization;
import com.quna.common.serialize.protostuff.ProtostuffSerialization;
import com.quna.common.serialize.protostuff.json.ProtostuffJsonSerialization;
import com.quna.common.serialize.protostuff.smile.ProtostuffSmileSerialization;


public final class SerializationFactory {
	
	private static Serialization _DEFAULT;
	
	public static Serialization defaultSerialization(){
		return _DEFAULT;
	}
	
	static{
		_init();
	}
	
	private static void _init(){
		try{
			Class.forName("com.dyuproject.protostuff.ProtostuffIOUtil");
			_DEFAULT = createSerialization(Serializers.PROTOSTUFF);
		}catch(Exception e){}
		
		if(null == _DEFAULT){
			try{
				Class.forName("com.alibaba.fastjson.JSONObject");
				_DEFAULT = createSerialization(Serializers.FASTJSON);
			}catch(Exception e){}
		}		
		if(null == _DEFAULT){
			try{
				Class.forName("org.codehaus.jackson.map.ObjectMapper");
				_DEFAULT = createSerialization(Serializers.JACKJSON);
			}catch(Exception e){}
		}
		if(null == _DEFAULT){
			try{
				Class.forName("de.javakaffee.kryoserializers.KryoReflectionFactorySupport");
				_DEFAULT = createSerialization(Serializers.KRYO);
			}catch(Exception e){}
		}
		
		if(null == _DEFAULT){
			try{
				Class.forName("com.caucho.hessian.io.SerializerFactory");
				_DEFAULT = createSerialization(Serializers.HESSIAN2);
			}catch(Exception e){}
		}
		if(null == _DEFAULT){
			_DEFAULT	= createSerialization(Serializers.JDK);
		}
	}
	
	
	public static Serialization createSerialization(Serializers serializer){
		if(serializer.equals(Serializers.FASTJSON)){
			return new FastjsonSerialzation();
		}else if(serializer.equals(Serializers.HESSIAN2)){
			return new Hessian2Serialization();
		}else if(serializer.equals(Serializers.KRYO)){
			return new KryoSerialization();
		}else if(serializer.equals(Serializers.PROTOSTUFF)){
			return new ProtostuffSerialization();
		}else if(serializer.equals(Serializers.PROTOSTUFF_JSON)){
			return new ProtostuffJsonSerialization();
		}else if(serializer.equals(Serializers.PROTOSTUFF_SMILE)){
			return new ProtostuffSmileSerialization();
		}else if(serializer.equals(Serializers.JACKJSON)){
			return new JackjsonSerialization();
		}else if(serializer.equals(Serializers.JACKJSON_SMILE)){
			return new JackjsonSmileSerialization();
		}
		return new JDKSerialization();
	}
	
}