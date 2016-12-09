package com.quna.common.exception.http;

@SuppressWarnings("serial")
public class HttpClientCreateException extends Exception{
	public HttpClientCreateException(){
		super();
	}
	
	public HttpClientCreateException(String message){
		super(message);
	}
	
	public HttpClientCreateException(Throwable cause){
       super(cause);
    }
	
	public HttpClientCreateException(String message, Throwable cause){
        super(message, cause);
    }
}
