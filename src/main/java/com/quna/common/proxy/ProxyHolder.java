package com.quna.common.proxy;

import java.lang.reflect.Method;
import java.util.Set;

public interface ProxyHolder {
	
	Set<Method> getPointcutMethods();
	
	Set<String> getPointcutMethodNames();
	
	ProxyBefore[] getProxyBefores();
	
	ProxyAfter[] getProxyAfters();
	
	ProxyThrowable[] getProxyThrowables();
}
