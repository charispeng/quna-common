package com.quna.common.http;

import com.quna.common.exception.http.HttpResponseHandleException;

/** 
 * <pre>
 * <b>返回结果处理接口</b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2015年12月14日 下午2:06:49
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2015年12月14日 下午2:06:49    252054576@qq.com  new file.
 * </pre>
 */
public interface HttpResponseHandler<T> {
	
	/**
	 * 处理返回的
	 * @param response
	 * @return
	 * @throws HttpResponseHandleException
	 */
	T handle(HttpResponse httpResponse) throws HttpResponseHandleException;
}