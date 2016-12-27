package com.quna.common.serialize;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import sun.reflect.ReflectionFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;

public class FieldDeserializer implements ObjectDeserializer{

	private Object createInstance(DefaultJSONParser parser, Type type){
		System.out.println(type.getClass());
		
		Class<?> clazz	= (Class<?>) type;
		try{
			return clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
		}catch(Exception e){
			try{
				ReflectionFactory feflectionFactory	= ReflectionFactory.getReflectionFactory();
				Constructor constructor				= feflectionFactory.newConstructorForSerialization(User.class, Object.class.getDeclaredConstructor(new Class[]{}));
				constructor.setAccessible(true);
				return constructor.newInstance();
			}catch(Exception ex){
				return null;
			}
		}
	}
	
	private JsonResolver jsonResolver;
	
	public FieldDeserializer(){
		this(new MapJsonResolver());
	}
	
	public FieldDeserializer(JsonResolver jsonResolver){
		this.jsonResolver	= jsonResolver;
	}
	
	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type,Object fieldName) {
		String input			= parser.getInput();
		System.out.println(input);
		Map<String,String> map	= (Map<String,String>)this.jsonResolver.resolve(input);
		System.out.println(map);
		JSONLexer jsonLexer		= parser.lexer;
		while(true){
			int token			= jsonLexer.next();
			System.out.println(token);
			if(token == JSONToken.EOF){
				break;
			}
			jsonLexer.nextToken();
		}
		return (T)map;
    }

	@Override
	public int getFastMatchToken() {
	    return JSONToken.LBRACE;
	}

}
