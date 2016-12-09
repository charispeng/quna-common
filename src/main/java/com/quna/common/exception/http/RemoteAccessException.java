package com.quna.common.exception.http;

/**
 * http请求访问异常
 *
 *
 * @author 252054576@qq.com
 * @date 2016年12月9日  下午3:20:20
 */
@SuppressWarnings("serial")
public class RemoteAccessException extends Exception{

	public RemoteAccessException(){
		super();
	}
	
	public RemoteAccessException(String message){
		super(message);
	}
	
	public RemoteAccessException(Throwable cause){
       super(cause);
    }
	
	public RemoteAccessException(String message, Throwable cause){
        super(message, cause);
    }
}
