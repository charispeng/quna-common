package com.quna.common.http.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import com.quna.common.http.HttpResponse;

/**
 * <pre>
 * <b>返回的结果对象</b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2015年12月14日 下午2:06:24
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2015年12月14日 下午2:06:24    252054576@qq.com  new file.
 * </pre>
 */
@SuppressWarnings("serial")
public class BasicHttpResponse implements HttpResponse{
	/**
	 * 默认编码格式
	 */
	private static final String DEFAULT_CHARSET	= "utf-8"; 
	
	/**
	 * 
	 */
	private org.apache.http.HttpResponse httpResponse;
	
	/**
	 * 
	 * @param response
	 */
	public BasicHttpResponse(org.apache.http.HttpResponse httpResponse){
		super();
		
		this.httpResponse	= httpResponse;
	}

	/**
	 * 
	 * @return
	 */
	public Header[] getHeaders() {
		return httpResponse.getAllHeaders();
	}

	/**
	 * 
	 * @return
	 */
	public HttpEntity getEntity() {
		return httpResponse.getEntity();
	}	

	/**
	 * 
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 */
	public String getText() throws UnsupportedOperationException, IOException {
		return getText(Charset.forName(DEFAULT_CHARSET));
	}
	
	/**
	 * 
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 */
	public String getText(Charset charset) throws UnsupportedOperationException, IOException {
		try{
			if(null == this.getEntity() || httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) return null;
			StringBuilder sb		= new StringBuilder();
			InputStream is			= this.getEntity().getContent();
			ReadableByteChannel readableByteChannel = Channels.newChannel(is);
			ByteBuffer byteBuffer	= ByteBuffer.allocate(1024);
			while(readableByteChannel.read(byteBuffer) != -1){
				//int position		= byteBuffer.position();
				//byte[] bytes		= new byte[position];
				byteBuffer.flip();
				//byteBuffer.get(bytes);
				//sb.append(new String(bytes));
				sb.append(charset.decode(byteBuffer).toString());
				byteBuffer.clear();
			}
			return sb.toString();
		}finally{
			this.close();
		}
	}
	
	/**
	 * 返回状态
	 * @return
	 */
	public int getStatus() {
		return httpResponse.getStatusLine().getStatusCode();
	}
	
	/**
	 * 
	 * @return
	 */
	public org.apache.http.HttpResponse getHttpResponse() {
		return httpResponse;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedOperationException 
	 */
	public InputStream getInputStream() throws UnsupportedOperationException, IOException{
		return this.httpResponse.getEntity().getContent();
	}
	
	/**
	 * 关闭
	 */
	@Override
	public void close() {
		if(httpResponse.getEntity() != null){
			try{
				EntityUtils.consume(httpResponse.getEntity());
			}catch(Exception e){}
		}
	}

	@Override
	public String toString() {
		return "Response [response=" + httpResponse + "]";
	}
}
