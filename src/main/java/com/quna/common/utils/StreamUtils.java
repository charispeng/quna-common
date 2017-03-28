package com.quna.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class StreamUtils {
	
	public static final int BUFFER_SIZE = 4096;
	/**
	 * input stream reader读取流通
	 * @param inputStream
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String copyToString(InputStream inputStream,Charset charset) throws IOException{
		InputStreamReader isr	= new InputStreamReader(inputStream, charset);
		StringBuilder sb		= new StringBuilder();
		char[] chars			= new char[BUFFER_SIZE];
		int read				= -1;
		while( -1 != (read = isr.read(chars))){
			sb.append(chars, 0, read);
		}
		return sb.toString();
	}
	/**
	 * 通过Buffer方法读取流
	 * @param inputStream
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String copyToStringByBuffer(InputStream inputStream,Charset charset) throws IOException{
		ReadableByteChannel rbc	= Channels.newChannel(inputStream);
		Reader reader			= Channels.newReader(rbc, charset.newDecoder(), -1);
		CharBuffer charBuffer	= CharBuffer.allocate(BUFFER_SIZE);
		int read				= -1;
		StringBuilder sb		= new StringBuilder();
		while(-1 != (read		= reader.read(charBuffer))){
			char[] chars		= new char[read];
			charBuffer.flip();
			charBuffer.get(chars);
			charBuffer.clear();
			sb.append(chars);
		}		
		return sb.toString();
	}
	
	/**
	 * 
	 * @param fileInputStream
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String copyToStringByDirectBuffer(FileInputStream fileInputStream,Charset charset) throws IOException{
		if(fileInputStream.available() <= 0){
			throw new IOException();
		}
		ReadableByteChannel rbc				= Channels.newChannel(fileInputStream);
		ByteBuffer byteBuffer				= ByteBuffer.allocateDirect(fileInputStream.available());
		rbc.read(byteBuffer);		
		byteBuffer.flip();
		
		CharBuffer charBuffer				= charset.decode(byteBuffer);
		int limit							= charBuffer.limit();
		char[] chars						= new char[limit];
		charBuffer.get(chars);
		((sun.nio.ch.DirectBuffer)byteBuffer).cleaner().clean();
		return new String(chars);
	}
	
	public static ByteArrayOutputStream copyToByteArray(InputStream inputStream) throws IOException{
		ByteArrayOutputStream baos			= new ByteArrayOutputStream();
		byte[] bytes						= new byte[BUFFER_SIZE];
		int read							= -1;
		if(-1 != (read = inputStream.read(bytes))){
			baos.write(bytes,0,read);
		}
		return baos;
	}
	
	public static void copyToFile(InputStream inputStream,File file,StandardCopyOption copyOption) throws IOException{
		Files.copy(inputStream, Paths.get(file.getAbsolutePath()),copyOption);
	}
}
