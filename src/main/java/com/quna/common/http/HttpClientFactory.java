package com.quna.common.http;

import com.quna.common.exception.http.HttpResponseHandleException;

public interface HttpClientFactory {
	
	/**
	 * 创建http连接客户端
	 * @return
	 */
	HttpClient getHttpClient() throws HttpResponseHandleException;
}