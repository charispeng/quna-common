package com.quna.common.exception;

@SuppressWarnings("serial")
public class QunaException extends Throwable{
	
	public QunaException(){
		super();
	}
	
	public QunaException(String message){
		super(message);
	}
	
	public QunaException(Throwable cause){
       super(cause);
    }
	
	public QunaException(String message, Throwable cause){
        super(message, cause);
    }
	 protected QunaException(String message, Throwable cause,
             boolean enableSuppression,
             boolean writableStackTrace) {
		 super(message, cause, enableSuppression, writableStackTrace);
	 }
}
