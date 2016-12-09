package com.quna.common.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.quna.common.exception.runtime.QunaRuntimeException;

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
public abstract class FileUtils extends _Util{
	/**
	 * 创建文件
	 * @param filePath
	 * @throws IOException 
	 */
	public static boolean createFile(File file){
		try{
			//文件已存在
			if(file.exists()){
				return true;
			}
			
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			return file.createNewFile();
		}catch(Exception e){
			throw new QunaRuntimeException("新建文件异常",e);
		}
	}
	
	/**
	 * 将流读取成为字符串
	 * @param inputStream	输入流
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String readInputStreamToString(InputStream inputStream,String encoding){
		ReadableByteChannel channel	= null;
		try {
			StringBuilder builder		= new StringBuilder();
			channel						= Channels.newChannel(inputStream);
			ByteBuffer byteBuffer		= ByteBuffer.allocate(1024);
			
			int read	= -1;
			while( -1 != (read = channel.read(byteBuffer))){
				int position	= byteBuffer.position();
				byte[] bytes= new byte[position];
				
				byteBuffer.flip();
				byteBuffer.get(bytes);
				byteBuffer.clear();
				
				builder.append(new String(bytes,encoding));
			}
			
			return builder.toString();
		}catch (Exception e) {
			throw new QunaRuntimeException("将inputStream保存为字符串");
		}finally{
			close(channel);
		}
	}
	
	/**
	 * 将输入流按指定编码成字符串
	 * @param inputStream
	 * @param encoding
	 * @return
	 */
	public static String readInputStreamToString(InputStream inputStream){
		return readInputStreamToString(inputStream,ENCODING);
	}
	
	/**
	 * 把流保存成文件
	 * @param inputStream	输入流
	 * @param filePath		文件路径
	 */
	public static void inputStreamToFile(InputStream inputStream,String filePath){
		OutputStream outputStream = null;
		try {
			File file		= new File(filePath);
			if(!createFile(file)){
				return ;
			}
			outputStream	= new FileOutputStream(filePath);
			int bufferSize	= 1024;
			byte[] buffer	= new byte[bufferSize];
			int size		= 0;
			while( (-1 != (size = inputStream.read(buffer, 0, bufferSize) ))){
				outputStream.write(buffer,0,size);
			}
		}catch (Exception e) {
			throw new QunaRuntimeException("将inputStream保存为文件");
		}finally{
			close(outputStream);
			close(inputStream);
		}
	}
	
	/**
	 * 关闭流
	 * @param closeable
	 */
	public static void close(Closeable...closeables){
		if(null == closeables || closeables.length == 0){
			return ;
		}
		for(Closeable closeable : closeables){
			try{
				closeable.close();
			}catch(Exception e){}
		}
		
	}
	
	/**
	 * 把文件读取成流
	 * @param file
	 * @return
	 */
	public static InputStream readInputStreamFromFile(File file){
		try{
			return new FileInputStream(file);
		}catch(FileNotFoundException e){
			throw new QunaRuntimeException("未找到文件!");
		}
	}	
	
	/**
	 * 获取工程目录
	 */
	public static String getProjectPath(){
		return System.getProperty("user.dir") + "";
	}
	
	/**
	 * 获取Class目录
	 */
	public static String getClassPath(){
		try {
			return URLDecoder.decode(FileUtils.class.getClassLoader().getResource("").getPath(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			return System.getProperty("user.dir") + "/classes";
		}
	}
	/**
	 * 获取临时目录
	 * @return 
	 */
	public static String getTmpDir(){
		return System.getProperty("java.io.tmpdir");
	}
	
	
	/**
	 * 读取文件入流
	 * @return
	 */
	public static InputStream readFileToInputStream(File file){
		if(null == file){
			throw new QunaRuntimeException("文件不能为空!");
		}
		if(!file.exists()){
			throw new QunaRuntimeException("文件不存在!");
		}
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new QunaRuntimeException(e);
		}	
	}
	
	/**
	 * 读取文件成字符串
	 * @return
	 */
	public static String readFileToString(File file){
		InputStream inputStream = readFileToInputStream(file);
		try{
			return readInputStreamToString(inputStream);
		}finally{
			close(inputStream);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args){
		 java.util.Properties props 	= System.getProperties();
	        java.util.Enumeration keys 	= props.keys();
	        String key = null;
	        while(keys.hasMoreElements()){
	            key = keys.nextElement().toString();
	            System.out.println(key + "=" + props.get(key));
	        }
	        System.out.println(getClassPath());
		System.out.println(getProjectPath());
		//InputStream is	= ReadInputStreamFromFile("F:/a.jpg");
		//InputStreamSaveFile(is,"F:/b.jpg");
		System.out.println(System.currentTimeMillis());
	}
}
