package com.quna.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import com.quna.common.exception.runtime.QunaRuntimeException;

/**
 * <pre>
 * <b>Object对象辅助工具.</b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> liuuhong@yeah.net
 * <b>Date:</b> 2014-1-1 上午10:00:01
 * <b>Copyright:</b> Copyright &copy;2006-2014 onefly.org Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *    1.0   2014-01-01 10:00:01    liuuhong@yeah.net
 *          new file.
 * </pre>
 */
public abstract class ObjectUtils extends _Util {

    /**
     * 对象hash code的16进制字符串.
     * 
     * @param object 对象.
     * @return String 对象hash code的16进制字符串.
     */
    public static String getHex(Object object) {
        return Integer.toHexString(System.identityHashCode(object));
    }

    /**
     * 返回某个对象的元素个数.<br/>
     * 如果该对象 == null, 则返回0;<br/>
     * 如果对象类型为集合对象（Collection、Map）, 则返回集合的 size;<br/>
     * 如果对象类型为为数组, 则返回数组的 length;<br/>
     * 如果对象为其他类型, 则返回1.<br/>
     * 
     * @param object 值对象
     * @return int 对象的元素个数
     */
    public static int getSize(Object object) {

        // 如果对象为空, 则返回0
        if (isNull(object)) {
            return 0;

        } else if (object instanceof Collection<?>) {
            // 对象的类型为Collection, 则返回size
            return ((Collection<?>) object).size();

        } else if (object instanceof Map<?, ?>) {
            // 对象的类型为Map, 则返回size
            return ((Map<?, ?>) object).size();

        } else if (object.getClass().isArray()) {
            // 对象的类型为数组, 则返回数组的length
            return ((Object[]) object).length;

        } else {
            // 其他类型, 则返回1
            return 1;
        }
    }

    /**
     * 将一个对象加入到已经存在的对象数组中（尾部）.<br/>
     * 如果对象数组为 null, 则创建一个新的对象数组;<br/>
     * 如果加入的对象元素为 null, 则直接返回对象数组.
     * 
     * @param array 已经存在的数组 (可以为null)
     * @param objects 需要加入数组的对象元素
     * @return Object[] 新的对象数组(一定不是null)
     */
    public static Object[] add2Arr(Object[] array, Object... objects) {

        if (null == objects || objects.length == 0) {
            return array;
        }

        Class<?> eleType = Object.class;
        int length = objects.length;
        if (null != array) {
            eleType = array.getClass().getComponentType();
            length = array.length + objects.length;

        } else {
            eleType = objects[0].getClass();
        }

        Object[] _array = (Object[]) Array.newInstance(eleType, length);
        if (null != array) {
            System.arraycopy(array, 0, _array, 0, array.length);
        }

        for (int i = 0; i < objects.length; i++) {
            _array[length - objects.length + i] = objects[i];
        }

        return _array;
    }

    /**
     * 安全判断两个对象 相同（invoke equals）.<br/>
     * 如果两个对象都是 null, 则返回 true;<br/>
     * 如果只有一个对象是 null, 则返回 false.<br/>
     * 注意: 要定制比较的细节必须对第一个对象重载equals()方法方可, 默认使用调用Object.equals().
     * 
     * @param object1 第一个对象.
     * @param object2 第二个对象.
     * @return boolean 两个对象是否相同.
     */
    public static boolean equals(Object object1, Object object2) {
        return (object1 == object2 || (null != object1 && object1.equals(object2)));
    }

    /**
     * 深度克隆对象自己, 并返回具体的类型.
     * 
     * @param <T>
     * @param object Object
     * @param clazz
     * @return T
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(Object object, Class<T> clazz) throws ClassNotFoundException, IOException {
        return (T) clone(object);
    }

    /**
     * 深度克隆对象自己.
     * 
     * @param object Object
     * @return Object
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public static Object clone(Object object) throws ClassNotFoundException, IOException {

        Object _object 				= null;
        ByteArrayOutputStream bo 	= new ByteArrayOutputStream();
        ObjectOutputStream oo 		= null;
        ByteArrayInputStream bi 	= null;
        ObjectInputStream oi 		= null;
        try {
            oo = new ObjectOutputStream(bo);
            // 源对象
            oo.writeObject(object);
            bi = new ByteArrayInputStream(bo.toByteArray());
            oi = new ObjectInputStream(bi);
            // 目标对象
            _object = oi.readObject();
        } finally {
            IOUtils.close(oi, bi, oo, bo);
        }

        return _object;
    }

    /**
     * 判断对象是否为Null.<br/>
     * 如果给出的对象为null 或 "&lt;null&gt;", 则返回true.<br/>
     * 例如: <br/>
     * ObjectUtil.isNull(null) == true;<br/>
     * ObjectUtil.isNull("< null>")== true;<br/>
     * ObjectUtil.isNull("abc") == false;<br/>
     * ObjectUtil.isNull(12345) == false;
     * 
     * @param object 对象实例.
     * @return boolean 是否为Null.
     */
    public static boolean isNull(Object object) {
        return (null == object || (object instanceof String && String.valueOf(object).equals("<null>")));
    }

    /**
     * 判断一个Bean对象为空.<br/>
     * Bean对象为 == null, 则返回 true.
     * 
     * @param object 需要检查的Bean对象.
     * @return boolean 是否为空.
     */
    public static boolean isEmpty(Object object) {
        return (null == object);
    }

    /**
     * 判断一个String对象为空.<br/>
     * String对象为 == null 或者 == "" 或者 == " " 或者 == "&lt;null&gt;", 则返回 true.<br/>
     * String对象为 == "a" 或者 == " a" 或者 == "a ", 则返回 false.
     * 
     * @param string 需要检查的String对象.
     * @return boolean 是否为空.
     */
    public static boolean isEmpty(String string) {
        return (isNull(string) || "".equals(string.trim()));
    }

    /**
     * 判断一个对象数组为空.<br/>
     * 对象数组为 == null 或者长度为 == 0, 则返回 true.
     * 
     * @param array 需要检查的对象数组.
     * @return boolean 是否为空.
     */
    public static boolean isEmpty(Object[] array) {
        return (null == array || array.length == 0);
    }

    /**
     * 判断一个Properties对象为空.<br/>
     * 对象数组为 == null 或者长度为 == 0, 则返回 true.
     * 
     * @param props 需要检查的Properties对象.
     * @return boolean 是否为空.
     */
    public static boolean isEmpty(Properties props) {
        return (null == props || props.size() == 0);
    }

    /**
     * 判断一个Map对象为空.<br/>
     * 对象数组为 == null 或者长度为 == 0, 则返回 true.
     * 
     * @param map 需要检查的Map对象.
     * @return boolean 是否为空.
     */
    public static boolean isEmpty(Map<? extends Object, ? extends Object> map) {
        return (null == map || map.size() == 0);
    }

    /**
     * 判断一个集合对象为空.<br/>
     * 集合对象为 == null 或者长度为 == 0, 则返回 true.
     * 
     * @param collection 需要检查的集合对象.
     * @return boolean 是否为空.
     */
    public static boolean isEmpty(Collection<? extends Object> collection) {
        return (null == collection || collection.size() == 0);
    }

    /**
     * 将对象转换成String类型.<br/>
     * 如果object对象 == null, 则返回 null.
     * 
     * @param object 原始对象.
     * @return String String类型值.
     */
    public static String toString(Object object) {
        return toString(object, null);
    }

    /**
     * 将对象转换成String类型.<br/>
     * 如果object对象 == null, 则返回 defaultValue.
     * 
     * @param object 原始对象.
     * @param defaultValue 缺省值.
     * @return String String类型值.
     */
    public static String toString(Object object, String defaultValue) {
        return isNull(object) ? defaultValue : String.valueOf(object);
    }

    /**
     * 将对象转换成Character类型.<br/>
     * 如果object对象 == null, 则返回 null.
     * 
     * @param object 原始对象.
     * @return Character Character类型值.
     */
    public static Character toChar(Object object) {
        return toChar(object, null);
    }

    /**
     * 将对象转换成Character类型.<br/>
     * 如果object对象 == null, 则返回 defaultValue.
     * 
     * @param object 原始对象.
     * @param defaultValue 缺省值.
     * @return Character Character类型值.
     */
    public static Character toChar(Object object, Character defaultValue) {

        String str = String.valueOf(object);
        if (isNull(object) || !StringUtils.hasLength(str)) {
            return defaultValue;

        } else {
            return str.charAt(0);
        }
    }

    /**
     * 将对象转换成 Boolean类型.<br/>
     * 如果object对象 == null 或者为 "", 则返回 null;<br/>
     * 如果object对象是 Boolean 类型, 则直接返回 obj;<br/>
     * 如果object对象是 数字类型并且 >0, 则返回 true;<br/>
     * 如果object为其他类型, 则将会转为字符串, 然后判断等于 "true" 或者 "1", 则返回 true.
     * 
     * @param object 原始对象.
     * @return Boolean Boolean类型值.
     */
    public static Boolean toBoolean(Object object) {
        return toBoolean(object, null);
    }

    /**
     * 将对象转换成 Boolean类型.<br/>
     * 如果object对象 == null 或者为 "", 则返回 defaultValue;<br/>
     * 如果object对象是 Boolean 类型, 则直接返回 obj;<br/>
     * 如果object对象是 数字类型并且 >0, 则返回 true;<br/>
     * 如果object为其他类型, 则将会转为字符串, 然后判断等于 "true" 或者 "1", 则返回 true.
     * 
     * @param object 原始对象.
     * @return Boolean Boolean类型值.
     */
    public static Boolean toBoolean(Object object, Boolean defaultValue) {

        if (isNull(object)) {
            return defaultValue;
        }

        String str = String.valueOf(object);
        if (object instanceof Boolean) {
            return (Boolean) object;

        } else if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double) {
            return Double.valueOf(str) > 0;

        } else {
            return ("true".equalsIgnoreCase(str) || str.equals("1"));
        }
    }

    /**
     * 将对象转换成Byte类型.<br/>
     * 如果object == null 或者 非byte类型, 则返回 (byte)0.
     * 
     * @param object 原始对象.
     * @return Byte Byte类型值.
     * @throws QunaRuntimeException 
     */
    public static Byte toByte(Object object) throws QunaRuntimeException {
        return toByte(object, (byte) 0);
    }

    /**
     * 将对象转换成Byte类型.<br/>
     * 如果object == null 或者 非byte类型, 则返回 defaultValue.
     * 
     * @param object 原始对象.
     * @param defaultValue 缺省值.
     * @return Byte Byte类型值.
     */
    public static Byte toByte(Object object, byte defaultValue) throws QunaRuntimeException{

        if (!isNull(object)) {

            String str = String.valueOf(object);
            try {
                return Byte.valueOf(str);
            } catch (Throwable e) {
               throw new QunaRuntimeException("object is not Byte type!");
            }
        }

        return defaultValue;
    }

    /**
     * 将对象转换成Int类型.<br/>
     * 如果 object == null 或者 非整数时, 则返回 0.
     * 
     * @param object 原始对象.
     * @return Integer Integer类型值.
     * @throws QunaRuntimeException 
     */
    public static int toInt(Object object) throws QunaRuntimeException {
        return toInt(object, 0);
    }

    /**
     * 将对象转换成Int类型.<br/>
     * 如果 object == null 或者 非整数时, 则返回 defaultValue.
     * 
     * @param object 原始对象.
     * @param defaultValue 缺省值.
     * @return Integer int类型值.
     */
    public static int toInt(Object object, int defaultValue) throws QunaRuntimeException{

        if (!isNull(object)) {

            String str = String.valueOf(object);
            try {
                return Integer.parseInt(str);

            } catch (Throwable e) {
                throw new QunaRuntimeException("Object is not Int type!");
            }
        }

        return defaultValue;
    }

    /**
     * 将对象转换成Integer类型.<br/>
     * 如果 object == null 或者 非整数时, 则返回 null.
     * 
     * @param object 原始对象.
     * @return Integer Integer类型值.
     * @throws QunaRuntimeException 
     */
    public static Integer toInteger(Object object) throws QunaRuntimeException {
        return toInteger(object, null);
    }

    /**
     * 将对象转换成Integer类型.<br/>
     * 如果 object == null 或者 非整数时, 则返回 defaultValue.
     * 
     * @param object 原始对象.
     * @param defaultValue 缺省值.
     * @return Integer Integer类型值.
     */
    public static Integer toInteger(Object object, Integer defaultValue) throws QunaRuntimeException{

        if (!isNull(object)) {

            String str 	= String.valueOf(object);
            int index 	= str.indexOf('.');
            if (index >= 0) {
                str = str.substring(0, str.indexOf('.'));
            }
            try {
                return Integer.valueOf(str);
            } catch (Throwable e) {
                throw new QunaRuntimeException("Object is not Integer type!");
            }
        }

        return defaultValue;
    }

    /**
     * 将对象转换成Long类型.<br/>
     * 如果 object == null 或者 非长整数时, 则返回 null.
     * 
     * @param object 原始对象.
     * @return Long Long类型值.
     * @throws QunaRuntimeException 
     */
    public static Long toLong(Object object) throws QunaRuntimeException {
        return toLong(object, null);
    }

    /**
     * 将对象转换成Long类型.<br/>
     * 如果 object == null 或者 非长整数时, 则返回 defaultValue.
     * 
     * @param object 原始对象.
     * @param defaultValue 缺省值.
     * @return Long Long类型值.
     */
    public static Long toLong(Object object, Long defaultValue) throws QunaRuntimeException{

        if (!isNull(object)) {

            String str = String.valueOf(object);
            int index = str.indexOf('.');
            if (index >= 0) {
                str = str.substring(0, str.indexOf('.'));
            }

            try {
                return Long.valueOf(str);

            } catch (Throwable e) {
               throw new QunaRuntimeException("object is not Long type!");
            }
        }

        return defaultValue;
    }

    /**
     * 将对象转换成Float类型.<br/>
     * 如果 object == null 或者 非单精度数字时, 则返回 null.
     * 
     * @param object 原始对象.
     * @return Float Float类型值.
     * @throws QunaRuntimeException 
     */
    public static Float toFloat(Object object) throws QunaRuntimeException {
        return toFloat(object, null);
    }

    /**
     * 将对象转换成Float类型.<br/>
     * 如果 object == null 或者 非单精度数字时, 则返回 defaultValue.
     * 
     * @param object 原始对象.
     * @param defaultValue 缺省值.
     * @return Float Float类型值.
     */
    public static Float toFloat(Object object, Float defaultValue) throws QunaRuntimeException {

        if (!isNull(object)) {

            String str = String.valueOf(object);
            try {
                return Float.valueOf(str);

            } catch (Throwable e) {
                throw new QunaRuntimeException("object is not Float type!");
            }
        }

        return defaultValue;
    }

    /**
     * 将对象转换成Double类型.<br/>
     * 如果 object == null 或者 非双精度数字时, 则返回 null.
     * 
     * @param object 原始对象.
     * @return Double Double类型值.
     * @throws QunaRuntimeException 
     */
    public static Double toDouble(Object object) throws QunaRuntimeException {
        return toDouble(object, null);
    }

    /**
     * 将对象转换成Double类型.<br/>
     * 如果 object == null 或者 非双精度数字时, 则返回 defaultValue.
     * 
     * @param object 原始对象.<br/>
     * @param defaultValue 缺省值.<br/>
     * @return Double Double类型值.<br/>
     */
    public static Double toDouble(Object object, Double defaultValue) throws QunaRuntimeException {

        if (!isNull(object)) {

            String str = String.valueOf(object);
            try {
                return Double.valueOf(str);

            } catch (Throwable e) {
            	throw new QunaRuntimeException("object is not Double type!");
            }
        }

        return defaultValue;
    }

    /**
     * 将对象转换成Date类型.<br/>
     * 如果 object == null 或者为 "", 则返回 null;<br/>
     * 如果 object 有效, 则系统将采用默认的时间格式 yyyy-MM-dd HH:mm:ss.SSS 转换为Date.
     * 
     * @param object 原始对象.
     * @return Date Date类型值.
     * @throws QunaRuntimeException 
     */
    public static Date toDate(Object object) throws QunaRuntimeException {
        return toDate(object, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * 将对象转换成Date类型.<br/>
     * 如果 object == null 或者为 "", 则返回 null.<br/>
     * 如果 format == null 或者为 "" 或者为 " ", 则返回 null;
     * 
     * @param object 原始对象.
     * @param format 格式字符串, 如: yyyy-MM-dd HH:mm:ss.SSS.
     * @return Date Date类型值.
     * @throws QunaRuntimeException 
     */
    public static Date toDate(Object object, String format) throws QunaRuntimeException {

        if (isNull(object) || StringUtils.isEmpty(format)) {
            return null;
        }

        return toDate(object, new SimpleDateFormat(format));
    }

    /**
     * 将对象转换成Date类型.<br/>
     * 如果 object == null 或者为 "", 则返回 null.<br/>
     * 如果 dateFormat == null 或者为 "", 则返回 null;
     * 
     * @param object 原始对象.
     * @param dateFormat 格式对象.
     * @return Date Date类型值.
     */
    public static Date toDate(Object object, DateFormat dateFormat) throws QunaRuntimeException{

        String str = String.valueOf(object);
        if (StringUtils.hasText(str)) {

            try {
                return dateFormat.parse(str);

            } catch (Throwable e) {
                throw new QunaRuntimeException("object is not Date type!");
            }
        }

        return null;
    }
}
