package com.quna.common.proxy;

import java.lang.reflect.Method;

public interface ProxyThrowable {
	/**
	 * 
	 * @param proxy
	 * @param method
	 * @param args
	 * @param target
	 */
	void throwAfter(Object proxy,Method method,Object[] args,Object target);
}
