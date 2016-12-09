package com.quna.common.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;

import org.apache.commons.codec.binary.Base64;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import sun.reflect.ReflectionFactory;

import com.quna.common.serialize.Serialization;
import com.quna.common.serialize.fastjson.FastjsonSerialzation;
import com.quna.common.serialize.hessian.Hessian2Serialization;
import com.quna.common.serialize.kryo.KryoSerialization;

public class Main {

	public static void main(String[] args) throws Exception{
		ClassReader reader			= new ClassReader(User.class.getName());
		//TraceClassVisitor tcv		= new TraceClassVisitor(new PrintWriter(System.out));
		//CheckClassAdapter cca		= new CheckClassAdapter(tcv);
		//reader.accept(tcv, 0);
		//tcv.visitEnd();
		ClassWriter cw				= new ClassWriter(0);
		TraceClassVisitor tcv		= new TraceClassVisitor(cw,new PrintWriter(System.out));
		reader.accept(tcv, 0);
		MethodVisitor mv			= tcv.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
		
		tcv.visitEnd();
		
		byte[] bytes				= cw.toByteArray();
		Class<?> clazz				= new MyClassLoader().defindClass(User.class.getName(), bytes);
		//ASMifier.main(new String[]{clazz.getName()});
		
		Object object				= clazz.newInstance();

		for(Constructor conn : object.getClass().getDeclaredConstructors()){
			System.out.println(conn);
		}
		
		Constructor con				= ReflectionFactory.getReflectionFactory().newConstructorForSerialization(User.class, Object.class.getConstructor());
		con.setAccessible(true);
		Object object1				= con.newInstance();
		Class<?> clazz2				= object1.getClass();

		
		System.out.println(object1);
		for(Constructor conn : clazz2.getDeclaredConstructors()){
			System.out.println(conn);
		}
		
		
		jdk.internal.org.objectweb.asm.util.ASMifier.main(new String[]{Main.class.getName()});
	}
	
	static class MyClassLoader extends ClassLoader{
		public Class<?> defindClass(String name,byte[] bytes){
			return super.defineClass(name, bytes, 0, bytes.length);
		}
	}
	
	
	public static void serial() throws Exception{
		Serialization se = new Hessian2Serialization();
		byte[] bytes	 = se.serialize(User.testUser);
		System.out.println(Base64.encodeBase64String(bytes).length());
		System.out.println(se.deserialize(User.class, bytes));
		
		
		
		Serialization se2 = new KryoSerialization();
		byte[] bytes2	 = se2.serialize(User.testUser);
		System.out.println(Base64.encodeBase64String(bytes2).length());
		System.out.println(se2.deserialize(bytes2));
		
		Serialization se3 = new FastjsonSerialzation();
		byte[] bytes3	 = se3.serialize(User.testUser);
		System.out.println(new String(bytes3));
		System.out.println(se3.deserialize(User.class, bytes3));
	}
}
