package com.quna.common.http.client;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quna.common.exception.http.RemoteAccessException;
import com.quna.common.exception.http.HttpClientCreateException;
import com.quna.common.exception.http.HttpResponseHandleException;
import com.quna.common.http.HttpClient;
import com.quna.common.http.HttpRequest;
import com.quna.common.http.HttpResponse;
import com.quna.common.http.HttpResponseHandler;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2015年12月14日 上午10:53:57
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2015年12月14日 上午10:53:57    252054576@qq.com  new file.
 * </pre>
 */
public abstract class AbstractHttpClient implements HttpClient {

	protected static final Logger LOGGER	= LoggerFactory.getLogger(AbstractHttpClient.class);
	
	private HttpContext httpContext;
	
	public HttpContext getHttpContext() {
		return httpContext;
	}

	public void setHttpContext(HttpContext httpContext) {
		this.httpContext = httpContext;
	}
	
	public AbstractHttpClient(){
		this(new BasicHttpContext());
	}
	
	public AbstractHttpClient(HttpContext httpContext){
		super();		
		this.httpContext		= httpContext;
	}
	@Override
	public HttpResponse execute(HttpRequest httpRequest) throws RemoteAccessException {
		try{
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("请求远程信息:===>>" + httpRequest);
			}
			org.apache.http.HttpResponse httpResponse	= getHttpClient(httpRequest).execute(httpRequest.getHttpRequestBase());
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("请求信息:"+ httpRequest +",远程返回信息:<<===" + httpResponse);
			}
			return new BasicHttpResponse(httpResponse);
		}catch (ClientProtocolException e) {
			throw new RemoteAccessException(RemoteAccessException.HTTP_PROTOCOL_ERROR,e);
		} catch (IOException e) {
			throw new RemoteAccessException("",e);
		} catch (HttpClientCreateException e) {
			throw new RemoteAccessException(RemoteAccessException.HTTP_CLIENT_ERROR,e);
		}
	}

	@Override
	public <T> T execute(HttpRequest httpRequest, HttpResponseHandler<T> handler) throws RemoteAccessException {
		HttpResponse httpResponse	= execute(httpRequest);
		try{
			return handler.handle(httpResponse);
		}catch (HttpResponseHandleException e) {
			throw new RemoteAccessException(RemoteAccessException.HTTP_RESPONSE_HANDLE_ERROR,e);
		}
	}
	
	@Override
	public String executeToText(HttpRequest httpRequest) throws RemoteAccessException{
		HttpResponse httpResponse			= execute(httpRequest);
		DefaultHttpResponseHandler handler	= DefaultHttpResponseHandler.defaultHttpResponseHandler();
		try{
			return handler.handle(httpResponse);
		}catch (HttpResponseHandleException e) {
			throw new RemoteAccessException(RemoteAccessException.HTTP_RESPONSE_HANDLE_ERROR,e);
		}
	}
	
}
