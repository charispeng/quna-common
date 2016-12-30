package com.quna.common.serialize;

@SuppressWarnings("serial")
public class SerializationException extends Exception {
	public SerializationException() {
		super();
	}
	
	public SerializationException(String message) {
		super(message);
	}
	
	public SerializationException(String message,Throwable e) {
		super(message,e);
	}
	
	public SerializationException(Throwable e) {
		super(e);
	}
}
