package com.quna.common.http.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.quna.common.http.Certificate;
import com.quna.common.http.CertificateType;

@SuppressWarnings("serial")
public final class BasicCertificate implements Certificate{
	
	private InputStream inputStream;						//证书流
	
	private String password;								//密码
	
	private CertificateType certificateType;				//格式
	
	private File file;										//
	
	public BasicCertificate(URL url,CertificateType certificateType,String password) throws IOException{
		this(url.openStream(),certificateType,password);
	}
	
	public BasicCertificate(String filePath,CertificateType certificateType,String password) {
		this(new File(filePath),certificateType,password);		
	}
	
	public BasicCertificate(File file,CertificateType certificateType,String password){
		super ();
		
		this.file	 			= file;
		this.certificateType	= certificateType;
		this.password			= password;
		
	}
	
	public BasicCertificate(InputStream inputStream,CertificateType certificateType,String password){
		super ();
		
		this.inputStream 		= inputStream;
		this.certificateType	= certificateType;
		this.password			= password;
	}

	public InputStream getContent() throws IOException {
		if(null != inputStream){
			return inputStream;
		}
		if(null != file){
			return new FileInputStream(file);
		}
		throw new RuntimeException("input stream is not be null!");
	}

	
	public String getPassword() {
		return password;
	}

	public File getFile() {
		return file;
	}

	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public CertificateType getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(CertificateType certificateType) {
		this.certificateType = certificateType;
	}

	@Override
	public void close() throws IOException {
		if(null != inputStream){
			inputStream.close();
		}
	}

	@Override
	public String toString() {
		return "BasicCertificate [inputStream=" + inputStream + ", password="
				+ password + ", certificateType=" + certificateType + ", file="
				+ file + "]";
	}
}