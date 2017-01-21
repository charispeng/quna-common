package com.quna.common.exception.http;

@SuppressWarnings("serial")
public class RemoteAccessException extends Exception {
	private int code;	
	private String error;
	public static RemoteAccessException CREATE_REQUEST_ERROR		= new RemoteAccessException(101,"构建http请求错误");
	public static RemoteAccessException HTTP_NOT_ACCESS				= new RemoteAccessException(102,"远程地址不能访问");
	public static RemoteAccessException HTTP_STATUS_NOT_200			= new RemoteAccessException(103,"远程访问返回状态不为200");
	public static RemoteAccessException HTTP_RESPONSE_HANDLE_ERROR	= new RemoteAccessException(104,"远程返回处理错误");
	public static RemoteAccessException HTTP_PROTOCOL_ERROR			= new RemoteAccessException(105,"http协议错误");
	public static RemoteAccessException HTTP_CLIENT_ERROR			= new RemoteAccessException(106,"创建httpclient错误");
	public static RemoteAccessException HTTP_READ_TIMEOUT			= new RemoteAccessException(107,"read time out");
	
	private static final String SPACING_CHARACTER					= "|"; 
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	public RemoteAccessException(RemoteAccessException e,Throwable ex) {
		super(e.getCode() + SPACING_CHARACTER + e.getError(),ex);
		
		this.code 	= e.getCode();
		this.error 	= e.getError();
	}
	public RemoteAccessException(int code, String error) {
		super(code + SPACING_CHARACTER + error);
		
		this.code = code;
		this.error = error;
	}
	public RemoteAccessException(int code, String error,Throwable e) {
		this(new RemoteAccessException(code, error),e);
	}
	
	public RemoteAccessException() {
		super();
	}
	
	public RemoteAccessException(String message) {
		super(message);
	}
	
	public RemoteAccessException(String message,Throwable e) {
		super(message,e);
	}
	
	public RemoteAccessException(Throwable e) {
		super(e);
	}
}
