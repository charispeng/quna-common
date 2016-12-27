package com.quna.common.serialize;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapJsonResolver implements JsonResolver {
	
	public Map<String,String> resolve(String json){
		Map<String,String> retMap	= new HashMap<String,String>();
		String[] fields				= json.split(JIANGE);
		if(fields.length == 0){return Collections.emptyMap();}
		int index					= 0;
		for(int i=0;i<fields.length;i++,index++){
			String field			= fields[i];
			if(index == 0){
				field				= field.replace(new StringBuilder(OBJECT_DEFAULT_START_TOKEN), "");
			}			
			if(index == fields.length -1){
				int last			= field.lastIndexOf(OBJECT_DEFAULT_END_TOKEN);
				field				= field.substring(0,last) + field.substring(last + 1);
			}
			
			int colonIndex			= field.indexOf(COLON);
			String fieldName		= field.substring(0,colonIndex);
			fieldName				= fieldName.startsWith(QUOTATION_MARK) ? fieldName.substring(1, fieldName.length() -1) : fieldName;
			String fieldValue		= field.substring(colonIndex + 1);
			fieldValue				= fieldValue.startsWith(QUOTATION_MARK) ? fieldValue.substring(1,fieldValue.length() -1 ) : fieldValue;
			
			retMap.put(fieldName, fieldValue);
		}
		
		return retMap;
	}
		
}
