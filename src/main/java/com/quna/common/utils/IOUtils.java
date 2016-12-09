package com.quna.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.Selector;
/**
 * <pre>
 * <b>IO 辅助工具.</b>
 * <b>Description:</b> 主要提供在java IO中close是一个很常见的操作, 用完一个
 *     Stream或者Reader或者Writer后都需要将它关闭, 而且每次关闭时还需先判断
 *     它是否为null, 从而保证不抛出NullPointerException, 还需check Exception.
 *     
 *     而closeQuietly则将检查是否为null和忽略Exception都在一个方法里完成, 
 *     从而省略了检查null和catch IOException, 从而缩短代码长度.
 *     
 *     另外toString方法是将一个InputStream转成指定encoding的String.
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
public abstract class IOUtils extends _Util {

    /**
     * 将对象二进制序列化, 然后进行BASE64加密.
     * 
     * @param object 对象.
     * @return String
     * @throws IOException
     */
    public static String toBinary(Object object) throws IOException {

        ByteArrayOutputStream buf = null;
        ObjectOutputStream out = null;

        try {
            buf = new ByteArrayOutputStream();
            out = new ObjectOutputStream(buf);

            out.writeObject(object);

            byte[] bytes = buf.toByteArray();
            return BASE64Utils.encode(bytes);

        } finally {
            IOUtils.close(out);
        }
    }

    /**
     * 将BASE64加密后的二进制反序列化成对象.
     * 
     * @param str
     * @return Object
     * @throws Exception
     */
    public static Object toObject(String str) throws Exception {

        ByteArrayInputStream bais = null;
        ObjectInputStream in = null;

        try {
            byte[] bytes = BASE64Utils.decode2Bytes(str);

            bais = new ByteArrayInputStream(bytes);
            in = new ObjectInputStream(bais);

            return in.readObject();

        } finally {
            IOUtils.close(in);
        }
    }

    /**
     * 将输入流转为字符串.
     * 
     * @param intput 输入流.
     * @return String
     * @throws IOException 
     */
    public static String toString(InputStream intput) throws IOException {

        return toString(intput, ENCODING);
    }

    /**
     * 将输入流转为指定编码的字符串.<br/>
     * 如果装换过程中异常, 则则直接返回 null.
     * 
     * @param intput 输入流.
     * @param encoding 转换编码.
     * @return String
     * @throws IOException 
     */
    public static String toString(InputStream intput, String encoding) throws IOException {

        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();

        int c;
        try {
            reader = new BufferedReader(new InputStreamReader(intput, encoding));
            c = reader.read();

            while (c != -1) {
                sb.append((char) c);
                c = reader.read();
            }
        } finally {
            IOUtils.close(reader);
        }

        return sb.toString();
    }

    /**
     * 静默关闭实现 Closeable 接口的对象.<br/>
     * 具体有: Nio Channel、 IO InputStream、 IO OutputStream、 IO Reader、 IO Writer
     * 
     * @param closeables 实现 Closeable 接口的对象.
     */
    public static void close(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable clob : closeables) {
                try {
                    if (null != clob) {
                        clob.close();
                    }
                } catch (Throwable e) {
                }
            }
        }
    }

    // /**
    // * 静默刷新输出流缓存. <暂时不提供>
    // *
    // * @param flushables .
    // */
    // @Deprecated
    // public static void close(Flushable... flushables) {
    //
    // if (flsbs != null) {
    // for (Flushable flsb : flushables) {
    // try {
    // if (null != flsb) {
    // flsb.flush();
    // }
    // } catch (Throwable e) {
    // }
    // }
    // }
    // }

    // ---------- socket 部分 ---------- //

    /**
     * 静默关闭 Socket.
     * 
     * @param sockets
     */
    public static void close(Socket... sockets) {

        if (sockets != null) {
            for (Socket socket : sockets) {
                try {
                    if (null != socket) {
                        socket.close();
                    }
                } catch (Throwable e) {
                }
            }
        }
    }

    /**
     * 静默关闭 Socket （Selector）.
     * 
     * @param selectors
     */
    public static void close(Selector... selectors) {

        if (selectors != null) {
            for (Selector selector : selectors) {
                try {
                    if (null != selector) {
                        selector.close();
                    }
                } catch (Throwable e) {
                }
            }
        }
    }
}
