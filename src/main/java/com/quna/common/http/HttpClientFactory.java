package com.quna.common.http;

public interface HttpClientFactory {	
	/**
	 * 创建http连接客户端
	 * @return
	 */
	HttpClient getHttpClient();
}