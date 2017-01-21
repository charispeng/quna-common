package com.quna.common.http;

import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.quna.common.http.client.BasicHttpRequest;
import com.quna.common.http.client.PoolHttpClientFactory;

public class HttpTest{
	
	public static void main(String[] args) throws Exception{
		//for(int i=0;i<100;i++){
			HttpClientFactory factory	= new PoolHttpClientFactory();
			BasicHttpRequest request	= new BasicHttpRequest("http://www.baidu.com", HttpMethod.GET);
			HttpClient httpClient		= factory.getHttpClient();
			System.out.println(httpClient.executeToText(request));
			System.out.println(HttpClients.getPoolStats());
		//}
		
		
		HttpGet get	= new HttpGet();
		get.setURI(new URI("http://www.baidu.com"));
		CloseableHttpResponse response	= org.apache.http.impl.client.HttpClients.createDefault().execute(get);
		InputStream inputStream			= response.getEntity().getContent();
		ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
		ByteBuffer byteBuffer			= ByteBuffer.allocate(1024);
		StringBuilder sb				= new StringBuilder();
		while(readableByteChannel.read(byteBuffer) != -1){
			//int position				= byteBuffer.position();
			//byte[] bytes				= new byte[position];
			byteBuffer.flip();
			//byteBuffer.get(bytes);
			//sb.append(new String(bytes));
			sb.append(Charset.defaultCharset().decode(byteBuffer).toString());
			byteBuffer.clear();
		}
		System.out.println(sb.toString());
	}
}
