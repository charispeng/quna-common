package com.quna.common.http;

import org.apache.http.protocol.HttpContext;

import com.quna.common.exception.http.HttpResponseHandlerException;

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
	org.apache.http.client.HttpClient getHttpClient(HttpRequest httpRequest) throws HttpResponseHandlerException;
	
	/**
	 * 执行http请求
	 * @param httpRequest
	 * @return
	 * @throws HttpResponseHandlerException
	 */
	HttpResponse execute(HttpRequest httpRequest) throws HttpResponseHandlerException;
	
	/**
	 * 传入返回处理类
	 * @param request
	 * @param handler
	 * @return
	 * @throws HttpResponseHandlerException
	 */
	<T> T execute(HttpRequest request,HttpResponseHandler<T> handler) throws HttpResponseHandlerException;
	
	/**
	 * 将结果执行返回字符串
	 * @param request
	 * @return
	 * @throws HttpResponseHandlerException
	 */
	String executeToText(HttpRequest httpRequest) throws HttpResponseHandlerException;	
}