package com.quna.common.serialize;

public interface JsonResolver {
	public static final String OBJECT_DEFAULT_START_TOKEN	= "{";
	public static final String OBJECT_DEFAULT_END_TOKEN		= "}";
	public static final String JIANGE						= ",";
	public static final String COLON						= ":";
	public static final String QUOTATION_MARK				= "\"";
	
	
	public Object resolve(String json);
}