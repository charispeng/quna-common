package com.quna.common.exception.http;

/**
 * <pre>
 * <b>远程访问异常</b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2015年12月4日 下午3:49:13
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2015年12月4日 下午3:49:13    252054576@qq.com  new file.
 * </pre>
 */

@SuppressWarnings("serial")
public class HttpResponseHandlerException extends Exception {

	public HttpResponseHandlerException(){
		super();
	}
	
	public HttpResponseHandlerException(String message){
		super(message);
	}
	
	public HttpResponseHandlerException(Throwable cause){
       super(cause);
    }
	
	public HttpResponseHandlerException(String message, Throwable cause){
        super(message, cause);
    }
}
