package com.quna.common.http.client;

import com.quna.common.http.HttpClient;
import com.quna.common.http.HttpClientFactory;

public class PoolHttpClientFactory implements HttpClientFactory {

	private static PoolHttpClientFactory INSTANCE	= new PoolHttpClientFactory();
	
	public static PoolHttpClientFactory defaultPoolHttpClientFactory(){
		return INSTANCE;
	}
	@Override
	public HttpClient getHttpClient(){
		return new PoolHttpClient();
	}
}
