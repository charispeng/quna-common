package com.quna.common.http;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable{

	public static User testUser	= new User("我是","中国人");
	
	private String name;
	private String key;
	
	/*
	public User(){
		super();
	}
	*/
	
	public User(String username,String key){
		super();
		
		this.name	= username;
		this.key		= key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", key=" + key + "]";
	}
	
	
}
