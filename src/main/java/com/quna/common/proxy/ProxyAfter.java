package com.quna.common.proxy;

import java.lang.reflect.Method;

public interface ProxyAfter {
	/**
	 * 
	 * @param proxy
	 * @param method
	 * @param args
	 * @param target
	 * @param result
	 */
	void after(Object proxy,Method method,Object[] args,Object target,Object result);
}