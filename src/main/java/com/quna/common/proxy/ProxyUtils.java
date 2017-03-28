package com.quna.common.proxy;

import java.lang.reflect.Method;
import java.util.Set;

public class ProxyUtils {
	
	public static boolean pointcut(ProxyHolder proxyHolder,Method method){
		Set<Method> pointcutMethods			= proxyHolder.getPointcutMethods();
		Set<String> pointcutMethodNames		= proxyHolder.getPointcutMethodNames();
		if(pointcutMethods == null && pointcutMethodNames == null){
			return true;
		}
		if(null != pointcutMethodNames && pointcutMethodNames.contains(method.getName())){
			return true;
		}
		if(null != pointcutMethods && pointcutMethods.contains(method)){
			return true;
		}
		return false;
	}
}
