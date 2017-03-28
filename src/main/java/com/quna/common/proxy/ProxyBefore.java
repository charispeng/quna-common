package com.quna.common.proxy;

import java.lang.reflect.Method;

public interface ProxyBefore {

	/**
	 * 
	 * @param proxy
	 * @param method
	 * @param args
	 * @param target
	 */
	void before(Object proxy,Method method,Object[] args,Object target);
	
}
