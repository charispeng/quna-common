package com.quna.common.http;

public interface HttpTimeOut {

	/**
	 * 获取读取超时
	 * @return
	 */
	int getSoTimeOut();
	
	/**
	 * 获取连接超时时间
	 * @return
	 */
	int getConnectTimeOut();
	
	/**
	 * 获取请求超时
	 * @return
	 */
	int getRequestTimeOut();
	
}
