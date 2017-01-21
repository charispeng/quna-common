package com.quna.common.http.client;

import com.quna.common.exception.http.HttpClientCreateException;
import com.quna.common.http.HttpClients;
import com.quna.common.http.HttpRequest;

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
public class PoolHttpClient extends AbstractHttpClient {
	
	@Override
	public org.apache.http.client.HttpClient getHttpClient(HttpRequest httpRequest) throws HttpClientCreateException{
		return HttpClients.buildClient(httpRequest);
	}
}
