package com.quna.common.http;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public interface Certificate extends Closeable,Serializable{
	/**
	 * 获取证书流
	 * @return
	 */
	InputStream getContent() throws IOException;
	
	/**
	 * 获取证书类型
	 * @return
	 */
	CertificateType getCertificateType();
	
	/**
	 * 获取证书对应的密码
	 * @return
	 */
	String getPassword();
}
