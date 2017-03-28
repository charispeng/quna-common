package com.quna.common.proxy;

public interface ProxyFactoryBean {
	/**
	 * 创建
	 * @return
	 */
	<T> T createBean(Class<T> inter,Object object,ProxyHolder proxyGet);
}