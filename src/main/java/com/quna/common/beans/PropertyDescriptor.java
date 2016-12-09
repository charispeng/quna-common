package com.quna.common.beans;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyDescriptor {
	
	private Field field;
	private Method writeMethod;
	private Method readMethod;
	
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public Method getWriteMethod() {
		return writeMethod;
	}
	public void setWriteMethod(Method writeMethod) {
		this.writeMethod = writeMethod;
	}
	public Method getReadMethod() {
		return readMethod;
	}
	public void setReadMethod(Method readMethod) {
		this.readMethod = readMethod;
	}
	@Override
	public String toString() {
		return "Property [field=" + field + ", writeMethod=" + writeMethod
				+ ", readMethod=" + readMethod + "]";
	}
}
