package com.quna.common.http.client;

import com.quna.common.http.HttpClient;
import com.quna.common.http.HttpClientFactory;

public class SSLHttpClientFactory implements HttpClientFactory{
	
	private static SSLHttpClientFactory INSTANCE	= new SSLHttpClientFactory();
	
	public static SSLHttpClientFactory defaultSSLHttpClientFactory(){
		return INSTANCE;
	}
	@Override
	public HttpClient getHttpClient(){
		return new SSLHttpClient();
	}
}