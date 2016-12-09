package com.quna.common.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.quna.common.beans.BeanInfo;
import com.quna.common.beans.PropertyDescriptor;
import com.quna.common.exception.runtime.QunaRuntimeException;

/**
 * 
 * <pre>
 * <b>类型处理</b>
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
public class TypeUtils extends _Util{
	/**
	 * 默认日期格式
	 */
	public static final String DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DEFAULT_TYPE_KEY     = "@type";
	/**
	 * 将类转换成String类型
	 * @param obj
	 * @return
	 */
	public static String castToString(Object obj){
		if(null == obj){
			return null;
		}
		if(obj instanceof String[] && ((String[]) obj).length == 1){
			return castToString(((String[]) obj)[0]);
		}
		return obj.toString();
	}
	
	public static Byte castToByte(Object obj) throws QunaRuntimeException{
		if(null == obj){
			return null;
		}
		if(obj instanceof Number){
			return ((Number) obj).byteValue();
		}
		if(obj instanceof String){
			String strVal = (String) obj;
            
            if (strVal.length() == 0) {
                return null;
            }
            
            if ("null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
			return Byte.parseByte(obj.toString());
		}
		throw new QunaRuntimeException("不能将" + obj  + "转换成Byte类型");
	}
	
	public static Integer castToInteger(Object obj) throws QunaRuntimeException{
		if(null == obj){
			return null;
		}
		if(obj instanceof Number){
			return ((Number) obj).intValue();
		}
		if(obj instanceof String){
			String strVal = (String) obj;
            
            if (strVal.length() == 0) {
                return null;
            }
            
            if ("null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
			return Integer.parseInt(obj.toString());
		}
		throw new QunaRuntimeException("不能将" + obj  + "转换成Integer类型");
	}
	
	public static Long castToLong(Object obj) throws QunaRuntimeException{
		if(null == obj){
			return null;
		}
		if(obj instanceof Number){
			return ((Number) obj).longValue();
		}
		if(obj instanceof String){
			String strVal = (String) obj;
            
            if (strVal.length() == 0) {
                return null;
            }
            
            if ("null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
			return Long.parseLong(obj.toString());
		}
		throw new QunaRuntimeException("不能将" + obj + "转换成Long类型");
	}
	
	public static Double castToDouble(Object obj) throws QunaRuntimeException{
		if(null == obj){
			return null;
		}
		if(obj instanceof Number){
			return ((Number) obj).doubleValue();
		}
		if(obj instanceof String){
			String strVal = (String) obj;
            
            if (strVal.length() == 0) {
                return null;
            }
            
            if ("null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
			return Double.parseDouble(obj.toString());
		}
		throw new QunaRuntimeException("不能将" + obj + "转换成Double类型");
	}
	
	public static final BigDecimal castToBigDecimal(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}

		if (value instanceof BigInteger) {
			return new BigDecimal((BigInteger) value);
		}

		String strVal = value.toString();
		if (strVal.length() == 0) {
			return null;
		}

		return new BigDecimal(strVal);
	}

	public static final BigInteger castToBigInteger(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof BigInteger) {
			return (BigInteger) value;
		}

		if (value instanceof Float || value instanceof Double) {
			return BigInteger.valueOf(((Number) value).longValue());
		}

		String strVal = value.toString();
		if (strVal.length() == 0) {
			return null;
		}

		return new BigInteger(strVal);
	}
	
    public static final Float castToFloat(Object value) throws QunaRuntimeException{
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }

        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0) {
                return null;
            }
            
            if ("null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }

            return Float.parseFloat(strVal);
        }

        throw new QunaRuntimeException("can not cast to float, value : " + value);
    }

    public static final Date castToDate(Object value) throws QunaRuntimeException{
        if (value == null) {
            return null;
        }

        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }

        if (value instanceof Date) {
            return (Date) value;
        }

        long longValue = -1;

        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
            return new Date(longValue);
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            if (strVal.indexOf('-') != -1) {
                String format;
                if (strVal.length() == DEFFAULT_DATE_FORMAT.length()) {
                    format = DEFFAULT_DATE_FORMAT;
                } else if (strVal.length() == 10) {
                    format = "yyyy-MM-dd";
                } else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                    format = "yyyy-MM-dd HH:mm:ss";
                } else {
                    format = "yyyy-MM-dd HH:mm:ss.SSS";
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                try {
                    return (Date) dateFormat.parse(strVal);
                } catch (ParseException e) {
                    throw new QunaRuntimeException("can not cast to Date, value : " + strVal);
                }
            }
            longValue = Long.parseLong(strVal);
        }

        if (longValue < 0) {
            throw new QunaRuntimeException("can not cast to Date, value : " + value);
        }

        return new Date(longValue);
    }
    
    
    public static final java.sql.Date castToSqlDate(Object value) throws QunaRuntimeException{
        if (value == null) {
            return null;
        }

        if (value instanceof Calendar) {
            return new java.sql.Date(((Calendar) value).getTimeInMillis());
        }

        if (value instanceof java.sql.Date) {
            return (java.sql.Date) value;
        }

        if (value instanceof java.util.Date) {
            return new java.sql.Date(((java.util.Date) value).getTime());
        }

        long longValue = 0;

        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            if (strVal.indexOf('-') != -1) {
                String format;
                if (strVal.length() == DEFFAULT_DATE_FORMAT.length()) {
                    format = DEFFAULT_DATE_FORMAT;
                } else if (strVal.length() == 10) {
                    format = "yyyy-MM-dd";
                } else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                    format = "yyyy-MM-dd HH:mm:ss";
                } else {
                    format = "yyyy-MM-dd HH:mm:ss.SSS";
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                try {
                    return new java.sql.Date(dateFormat.parse(strVal).getTime());
                } catch (ParseException e) {
                    throw new QunaRuntimeException("can not cast to java.sql.Date, value : " + strVal);
                }
            }
            longValue = Long.parseLong(strVal);
        }

        if (longValue <= 0) {
            throw new QunaRuntimeException("can not cast to java.sql.Date, value : " + value);
        }

        return new java.sql.Date(longValue);
    }
    
    
    public static final java.sql.Timestamp castToTimestamp(Object value) throws QunaRuntimeException{
        if (value == null) {
            return null;
        }

        if (value instanceof Calendar) {
            return new java.sql.Timestamp(((Calendar) value).getTimeInMillis());
        }

        if (value instanceof java.sql.Timestamp) {
            return (java.sql.Timestamp) value;
        }

        if (value instanceof java.util.Date) {
            return new java.sql.Timestamp(((java.util.Date) value).getTime());
        }

        long longValue = 0;

        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            if (strVal.indexOf('-') != -1) {
                String format;
                if (strVal.length() == DEFFAULT_DATE_FORMAT.length()) {
                    format = DEFFAULT_DATE_FORMAT;
                } else if (strVal.length() == 10) {
                    format = "yyyy-MM-dd";
                } else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                    format = "yyyy-MM-dd HH:mm:ss";
                } else {
                    format = "yyyy-MM-dd HH:mm:ss.SSS";
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                try {
                    return new java.sql.Timestamp(dateFormat.parse(strVal).getTime());
                } catch (ParseException e) {
                    throw new QunaRuntimeException("can not cast to java.sql.Timestamp, value : " + strVal);
                }
            }
            longValue = Long.parseLong(strVal);
        }

        if (longValue <= 0) {
            throw new QunaRuntimeException("can not cast to Timestamp, value : " + value);
        }

        return new java.sql.Timestamp(longValue);
    }
    
    
    public static final byte[] castToBytes(Object value) throws QunaRuntimeException {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }

        if (value instanceof String) {
            return ((String) value).getBytes();
        }
        throw new QunaRuntimeException("can not cast to byte, value : " + value);
    }

    public static final Boolean castToBoolean(Object value) throws QunaRuntimeException {
        if (value == null) {
            return null;
        }

        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue() == 1;
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.length() == 0) {
                return null;
            }

            if ("true".equalsIgnoreCase(strVal)) {
                return Boolean.TRUE;
            }
            if ("false".equalsIgnoreCase(strVal)) {
                return Boolean.FALSE;
            }

            if ("1".equals(strVal)) {
                return Boolean.TRUE;
            }
            
            if ("0".equals(strVal)) {
                return Boolean.FALSE;
            }
            
            if ("null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
        }

        throw new QunaRuntimeException("can not cast to boolean, value : " + value);
    }
    
    public static final Short castToShort(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).shortValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;
            
            if (strVal.length() == 0) {
                return null;
            }
            
            if ("null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            
            return Short.parseShort(strVal);
        }

        throw new QunaRuntimeException("can not cast to short, value : " + value);
    }
    
    public static final <T> T castToJavaBean(Object obj, Class<T> clazz) {
    	return cast(obj, clazz);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static final <T> T cast(Object obj, Class<T> clazz) {
        if (obj == null) {
            return null;
        }

        if (clazz == null) {
            throw new IllegalArgumentException("clazz is null");
        }

        if (clazz == obj.getClass()) {
            return (T) obj;
        }

        if (obj instanceof Map) {
            if (clazz == Map.class) {
                return (T) obj;
            }

            Map map = (Map) obj;
            if (clazz == Object.class && !map.containsKey(DEFAULT_TYPE_KEY)) {
                return (T) obj;
            }

            return castToJavaBean((Map<String, Object>) obj, clazz);
        }

        if (clazz.isArray()) {
        	//集合强转
            if (obj instanceof Collection) {
                Collection collection = (Collection) obj;
                int index = 0;
                Object array = Array.newInstance(clazz.getComponentType(), collection.size());
                for (Object item : collection) {
                    Object value = cast(item, clazz.getComponentType());
                    Array.set(array, index, value);
                    index++;
                }
                return (T) array;
            }
            //数组强转
            if(obj.getClass().isArray()){
            	Object[] objs	= (Object[]) obj;
            	int index		= 0;
            	Object array 	= Array.newInstance(clazz.getComponentType(), objs.length);
            	for(Object item : objs){
            		Object value = cast(item, clazz.getComponentType());
                    Array.set(array, index, value);
                    index++;
            	}
            	return (T) array;
            }
            //非数据强转数组，把非数据当成一个只有一个内容的数据进行强转
            Object array 	= Array.newInstance(clazz.getComponentType(), 1);
            Object value	= cast(obj,clazz.getComponentType());
            Array.set(array, 0, value);
            return (T) array;
        }

        if (clazz.isAssignableFrom(obj.getClass())) {
            return (T) obj;
        }

        if (clazz == boolean.class || clazz == Boolean.class) {
            return (T) castToBoolean(obj);
        }

        if (clazz == byte.class || clazz == Byte.class) {
            return (T) castToByte(obj);
        }

        // if (clazz == char.class || clazz == Character.class) {
        // return (T) castToCharacter(obj);
        // }

        if (clazz == short.class || clazz == Short.class) {
            return (T) castToShort(obj);
        }

        if (clazz == int.class || clazz == Integer.class) {
            return (T) castToInteger(obj);
        }

        if (clazz == long.class || clazz == Long.class) {
            return (T) castToLong(obj);
        }

        if (clazz == float.class || clazz == Float.class) {
            return (T) castToFloat(obj);
        }

        if (clazz == double.class || clazz == Double.class) {
            return (T) castToDouble(obj);
        }

        if (clazz == String.class) {
            return (T) castToString(obj);
        }

        if (clazz == BigDecimal.class) {
            return (T) castToBigDecimal(obj);
        }

        if (clazz == BigInteger.class) {
            return (T) castToBigInteger(obj);
        }

        if (clazz == Date.class) {
            return (T) castToDate(obj);
        }

        if (clazz == java.sql.Date.class) {
            return (T) castToSqlDate(obj);
        }

        if (clazz == java.sql.Timestamp.class) {
            return (T) castToTimestamp(obj);
        }

        if (clazz.isEnum()) {
            return (T) castToEnum(obj, clazz);
        }

        if (Calendar.class.isAssignableFrom(clazz)) {
            Date date = castToDate(obj);
            Calendar calendar;
            if (clazz == Calendar.class) {
                calendar = Calendar.getInstance();
            } else {
                try {
                    calendar = (Calendar) clazz.newInstance();
                } catch (Exception e) {
                    throw new QunaRuntimeException("can not cast to : " + clazz.getName(), e);
                }
            }
            calendar.setTime(date);
            return (T) calendar;
        }
        throw new QunaRuntimeException("can not cast to : " + clazz.getName());
    }
    
    
    @SuppressWarnings("unchecked")
    public static final <T> T cast(Object obj, Type type) {
        if (obj == null) {
            return null;
        }

        if (type instanceof Class) {
            return (T) cast(obj, (Class<T>) type);
        }

        if (type instanceof ParameterizedType) {
            return (T) cast(obj, (ParameterizedType) type);
        }

        if (obj instanceof String) {
            String strVal = (String) obj;
            if (strVal.length() == 0) {
                return null;
            }
        }

        if (type instanceof TypeVariable) {
            return (T) obj;
        }

        throw new QunaRuntimeException("can not cast to : " + type);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static final <T> T castToEnum(Object obj, Class<T> clazz) {
        try {
            if (obj instanceof String) {
                String name = (String) obj;
                if (name.length() == 0) {
                    return null;
                }

                return (T) Enum.valueOf((Class<? extends Enum>) clazz, name);
            }

            if (obj instanceof Number) {
                int ordinal 	= ((Number) obj).intValue();

                Method method 	= clazz.getMethod("values");
                Object[] values = (Object[]) method.invoke(null);
                for (Object value : values) {
                    Enum e 		= (Enum) value;
                    if (e.ordinal() == ordinal) {
                        return (T) e;
                    }
                }
            }
        } catch (Exception ex) {
            throw new QunaRuntimeException("can not cast to : " + clazz.getName(), ex);
        }

        throw new QunaRuntimeException("can not cast to : " + clazz.getName());
    }
    
    @SuppressWarnings({ "unchecked" })
    public static final <T> T castToJavaBean(Map<String, Object> map, Class<T> clazz) {
        try {
            if (clazz == StackTraceElement.class) {
                String declaringClass = (String) map.get("className");
                String methodName = (String) map.get("methodName");
                String fileName = (String) map.get("fileName");
                int lineNumber;
                {
                    Number value = (Number) map.get("lineNumber");
                    if (value == null) {
                        lineNumber = 0;
                    } else {
                        lineNumber = value.intValue();
                    }
                }

                return (T) new StackTraceElement(declaringClass, methodName, fileName, lineNumber);
            }

            {
                Object iClassObject = map.get(DEFAULT_TYPE_KEY);
                if (iClassObject instanceof String) {
                    String className = (String) iClassObject;

                    Class<?> loadClazz = (Class<T>) loadClass(className);

                    if (loadClazz == null) {
                        throw new ClassNotFoundException(className + " not found");
                    }

                    if (!loadClazz.equals(clazz)) {
                        return (T) castToJavaBean(map, loadClazz);
                    }
                }
            }
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            T object 					= constructor.newInstance();
            BeanInfo beanInfo			= BeanInfo.getInstance(clazz);
            PropertyDescriptor[] propertyDescriptors	= beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor propertyDescriptor : propertyDescriptors){
            	Object value			= map.get(propertyDescriptor.getField().getName());
            	if(null == value){
            		continue;
            	}
            	Type type				= propertyDescriptor.getField().getGenericType();
        		value					= cast(value,type);
            	if(null != propertyDescriptor.getWriteMethod()){
            		if(!propertyDescriptor.getWriteMethod().isAccessible()){
            			propertyDescriptor.getWriteMethod().setAccessible(true);
            		}
            		propertyDescriptor.getWriteMethod().invoke(object, new Object[]{value});
            	}else{
            		if(!propertyDescriptor.getField().isAccessible()){
            			propertyDescriptor.getField().setAccessible(true);
            		}
            		propertyDescriptor.getField().set(object,value);
            	}
            }            
            return object;
        } catch (Exception e) {
            throw new QunaRuntimeException(e.getMessage(), e);
        }
    }    
    
    
    private static ConcurrentMap<String, Class<?>> mappings = new ConcurrentHashMap<String, Class<?>>();
    static {
        addBaseClassMappings();
    }

    public static void addClassMapping(String className, Class<?> clazz) {
        if (className == null) {
            className = clazz.getName();
        }

        mappings.put(className, clazz);
    }

    public static void addBaseClassMappings() {
        mappings.put("byte", byte.class);
        mappings.put("short", short.class);
        mappings.put("int", int.class);
        mappings.put("long", long.class);
        mappings.put("float", float.class);
        mappings.put("double", double.class);
        mappings.put("boolean", boolean.class);
        mappings.put("char", char.class);

        mappings.put("[byte", byte[].class);
        mappings.put("[short", short[].class);
        mappings.put("[int", int[].class);
        mappings.put("[long", long[].class);
        mappings.put("[float", float[].class);
        mappings.put("[double", double[].class);
        mappings.put("[boolean", boolean[].class);
        mappings.put("[char", char[].class);

        mappings.put(HashMap.class.getName(), HashMap.class);
    }

    public static void clearClassMapping() {
        mappings.clear();
        addBaseClassMappings();
    }
    public static Class<?> loadClass(String className) {
        if (className == null || className.length() == 0) {
            return null;
        }

        Class<?> clazz = mappings.get(className);

        if (clazz != null) {
            return clazz;
        }

        if (className.charAt(0) == '[') {
            Class<?> componentType = loadClass(className.substring(1));
            return Array.newInstance(componentType, 0).getClass();
        }

        if (className.startsWith("L") && className.endsWith(";")) {
            String newClassName = className.substring(1, className.length() - 1);
            return loadClass(newClassName);
        }

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            if (classLoader != null) {
                clazz = classLoader.loadClass(className);

                addClassMapping(className, clazz);

                return clazz;
            }
        } catch (Throwable e) {
            // skip
        }

        try {
            clazz = Class.forName(className);

            addClassMapping(className, clazz);

            return clazz;
        } catch (Throwable e) {
            // skip
        }

        return clazz;
    }
    public static Class<?> getClass(Type type) {
        if (type.getClass() == Class.class) {
            return (Class<?>) type;
        }

        if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        }

        return Object.class;
    }
    

    public static Field getField(Class<?> clazz, String fieldName) {
        for (Field field : clazz.getDeclaredFields()) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }
        
        Class<?> superClass = clazz.getSuperclass();
        if(superClass != null && superClass != Object.class) {
            return getField(superClass, fieldName);
        }

        return null;
    }
    
    public static Method getMethod(Class<?> clazz ,String methodName){
    	for(Method method:clazz.getDeclaredMethods()){
    		if(methodName.equals(method.getName())){
    			return method;
    		}
    	}
    	Class<?> superClass = clazz.getSuperclass();
        if(superClass != null && superClass != Object.class) {
            return getMethod(superClass, methodName);
        }
        return null;
    }
	public static void main(String[] args){
		System.out.println(TypeUtils.castToString(1));
	}	
}
