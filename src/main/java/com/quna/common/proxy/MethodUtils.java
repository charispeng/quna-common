package com.quna.common.proxy;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MethodUtils {
	
	public static Set<Method> getAllMethods(Class<?> clazz){
		Set<Method> ret		= new HashSet<Method>();
		Method[] methods	= clazz.getMethods();
		Collections.addAll(ret, methods);		
		while(clazz.getSuperclass() != null){
			ret.addAll(getAllMethods(clazz.getSuperclass()));
		}
		return ret;
	}
}
