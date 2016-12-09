package com.quna.common.http.client;

import java.io.Serializable;

import com.quna.common.http.HttpTimeOut;

public class BasicHttpTimeout implements HttpTimeOut, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 获取默认的超时设置
	 * @return
	 */
	public static BasicHttpTimeout defaultBasicHttpTimeout(){
		return new BasicHttpTimeout(8000,8000,8000);
	}
	
	private int soTimeOut;
	
	private int connectTimeOut;
	
	private int requestTimeOut;
	
	public BasicHttpTimeout(){
		super();
	}

	public BasicHttpTimeout(int soTimeOut,int connectTimeOut,int requestTimeOut){
		super();
		this.soTimeOut 			= soTimeOut;
		this.connectTimeOut		= connectTimeOut;
		this.requestTimeOut		= requestTimeOut;
	}

	public int getSoTimeOut() {
		return soTimeOut;
	}

	public void setSoTimeOut(int soTimeOut) {
		this.soTimeOut = soTimeOut;
	}

	public int getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	public int getRequestTimeOut() {
		return requestTimeOut;
	}

	public void setRequestTimeOut(int requestTimeOut) {
		this.requestTimeOut = requestTimeOut;
	}

	@Override
	public String toString() {
		return "BasicHttpTimeout [soTimeOut=" + soTimeOut + ", connectTimeOut="
				+ connectTimeOut + ", requestTimeOut=" + requestTimeOut + "]";
	}
}
