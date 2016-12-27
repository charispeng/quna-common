package com.quna.common.http;

import org.apache.http.protocol.HttpContext;

import com.quna.common.exception.http.RemoteAccessException;
import com.quna.common.exception.http.HttpClientCreateException;
import com.quna.common.exception.http.HttpResponseHandleException;

public interface HttpClient{
	
	/**
	 * 获取HttpClientContext();
	 * @return
	 */
	HttpContext getHttpContext();
	
	/**
	 * 获取apache http client
	 * @return
	 */
	org.apache.http.client.HttpClient getHttpClient(HttpRequest httpRequest) throws HttpClientCreateException;
	
	/**
	 * 执行http请求
	 * @param httpRequest
	 * @return
	 * @throws HttpResponseHandleException
	 */
	HttpResponse execute(HttpRequest httpRequest) throws RemoteAccessException;
	
	/**
	 * 传入返回处理类
	 * @param request
	 * @param handler
	 * @return
	 * @throws HttpResponseHandleException
	 */
	<T> T execute(HttpRequest request,HttpResponseHandler<T> handler) throws RemoteAccessException;
	
	/**
	 * 将结果执行返回字符串
	 * @param request
	 * @return
	 * @throws HttpResponseHandleException
	 */
	String executeToText(HttpRequest httpRequest) throws RemoteAccessException;	
}