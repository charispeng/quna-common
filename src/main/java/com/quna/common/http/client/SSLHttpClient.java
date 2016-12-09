package com.quna.common.http.client;

import org.apache.http.client.HttpClient;

import com.quna.common.exception.http.HttpClientCreateException;
import com.quna.common.http.HttpRequest;
import com.quna.common.http.utils.HttpClients;

public class SSLHttpClient extends AbstractHttpClient{
	
	@Override
	public HttpClient getHttpClient(HttpRequest httpRequest) throws HttpClientCreateException {
		return HttpClients.buildSSLClient(httpRequest);
	}
}