package com.quna.common.http;

import org.apache.http.HttpHost;

public interface ProxyRoute {

	/**
	 * 
	 * @return
	 */
	HttpHost getHttpPost();
	
	/**
	 * 获取代理ip地址
	 * @return
	 */
	String getHost();
	
	/**
	 * 获取端口
	 * @return
	 */
	int getPort();
	
	/**
	 * 返回代理是否可用
	 * @return
	 */
	boolean isValid();
}
