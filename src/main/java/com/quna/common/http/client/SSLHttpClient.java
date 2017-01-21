package com.quna.common.http.client;

import org.apache.http.client.HttpClient;

import com.quna.common.exception.http.HttpClientCreateException;
import com.quna.common.http.HttpClients;
import com.quna.common.http.HttpRequest;

public class SSLHttpClient extends AbstractHttpClient{
	
	@Override
	public HttpClient getHttpClient(HttpRequest httpRequest) throws HttpClientCreateException {
		try{
			return HttpClients.buildSSLClient(httpRequest);
		}catch(Exception e){
			throw new HttpClientCreateException(e);
		}
	}
}