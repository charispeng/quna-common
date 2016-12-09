package com.quna.common.http.client;

import com.quna.common.exception.http.HttpResponseHandlerException;
import com.quna.common.http.HttpClient;
import com.quna.common.http.HttpClientFactory;

public class PoolHttpClientFactory implements HttpClientFactory {

	private static final PoolHttpClientFactory INSTANCE	= new PoolHttpClientFactory();
	
	public static final PoolHttpClientFactory defaultPoolHttpClientFactory(){
		return INSTANCE;
	}
	
	@Override
	public HttpClient getHttpClient() throws HttpResponseHandlerException {
		return new PoolHttpClient();
	}
}
