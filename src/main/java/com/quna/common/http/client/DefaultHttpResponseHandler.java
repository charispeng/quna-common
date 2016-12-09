package com.quna.common.http.client;

import com.quna.common.exception.http.HttpResponseHandlerException;
import com.quna.common.http.HttpResponse;
import com.quna.common.http.HttpResponseHandler;

public class DefaultHttpResponseHandler implements HttpResponseHandler<String>{
	
	public static final DefaultHttpResponseHandler INSTANCE	= new DefaultHttpResponseHandler();

	public static DefaultHttpResponseHandler defaultHttpResponseHandler(){
		return INSTANCE;
	}
	
	@Override
	public String handle(HttpResponse httpResponse) throws HttpResponseHandlerException {
		try{
			return httpResponse.getText();
		}catch(Exception e){
			throw new HttpResponseHandlerException("处理返回的httpResponse异常",e);
		}finally{
			try{
				httpResponse.close();
			}catch(Exception e){}
		}
	}

}
