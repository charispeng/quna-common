package com.quna.common.proxy.jdk;

import java.lang.reflect.Proxy;

import com.quna.common.proxy.ProxyFactoryBean;
import com.quna.common.proxy.ProxyHolder;

public class JdkProxyFactoryBean implements ProxyFactoryBean {
	
	public <T> T createBean(Class<T> inter,Object target,ProxyHolder proxyGet) {
		if(!inter.isInterface()){
			throw new IllegalArgumentException("inter参数必需为接口");
		}
		return inter.cast(Proxy.newProxyInstance(inter.getClassLoader(), new Class[]{inter}, new BasicInvocationHandler(target, proxyGet)));
	}
}