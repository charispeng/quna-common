package com.quna.common.serialize;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;

@SuppressWarnings("serial")
@JSONType(deserializer=JavaBeanDeserializer.class)
public class User implements Serializable{
	private final String name;
	private final String age;
	public User(String name, String age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public String getAge() {
		return age;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + "]";
	}
}
