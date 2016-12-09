package com.quna.common.exception.runtime;
/**
 * 
 * <pre>
 * <b></b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2014-1-1 上午10:00:01
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2014-01-01 10:00:01     252054576@qq.com
 *         new file.
 * </pre>
 */
public class QunaRuntimeException extends RuntimeException{
	
	private static final long serialVersionUID = 2142729801559033254L;
	
	public QunaRuntimeException(){
		super();
	}
	
	public QunaRuntimeException(String message){
		super(message);
	}
	
	public QunaRuntimeException(Throwable cause){
       super(cause);
    }
	
	public QunaRuntimeException(String message, Throwable cause){
        super(message, cause);
    }
}
