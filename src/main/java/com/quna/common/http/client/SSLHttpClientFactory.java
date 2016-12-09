package com.quna.common.http.client;

import com.quna.common.exception.http.HttpResponseHandleException;
import com.quna.common.http.HttpClient;
import com.quna.common.http.HttpClientFactory;

public class SSLHttpClientFactory implements HttpClientFactory{
	
	private static final SSLHttpClientFactory INSTANCE	= new SSLHttpClientFactory();
	
	public static final SSLHttpClientFactory defaultSSLHttpClientFactory(){
		return INSTANCE;
	}
	@Override
	public HttpClient getHttpClient() throws HttpResponseHandleException{
		return new SSLHttpClient();
	}
}