package com.quna.common.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

import com.quna.common.proxy.ProxyFactoryBean;
import com.quna.common.proxy.ProxyHolder;

public class CglibProxyFactoryBean implements ProxyFactoryBean {
	
	public <T> T createBean(Class<T> inter,Object target,ProxyHolder proxyGet) {
		Enhancer enhancer 	= new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(new BasicMethodInterceptor(target,proxyGet));
		return inter.cast(enhancer.create());
	}
}