package com.quna.common.http;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.TypePath;

public class Main2 {
	public static void main(String[] args) throws Exception{
		byte[] aa	= new byte[]{1,2,3,4,5,6,7};
		byte[] bb	= new byte[]{4,5,6,7};
		int length	= bb.length;
		bb			= Arrays.copyOf(bb, bb.length + aa.length);
		System.out.println(Arrays.toString(bb));
		System.arraycopy(aa, 0, bb, length, aa.length);
		
		System.out.println(Arrays.toString(aa));
		System.out.println(Arrays.toString(bb));
		
		//ASMifier.main(new String[]{Main2.class.getName()});		
		
		MyVisit myvisit		= new MyVisit();
		ClassReader reader	= new ClassReader(Main2.class.getName());
		reader.accept(myvisit, 0);
		myvisit.visitEnd();
		
	}
	
	public static class MyVisit extends ClassVisitor{

		public MyVisit() {
			super(Opcodes.ASM5);
		}

		@Override
		public MethodVisitor visitMethod(int arg0, String arg1, String arg2,
				String arg3, String[] arg4) {
			
			//return new MyMethodVisit(arg1,arg2);
			
			return super.visitMethod(arg0, arg1, arg2, arg3, arg4);
		}
		
		@Override
		public FieldVisitor visitField(int arg0, String arg1, String arg2,
				String arg3, Object arg4) {
			
			return new MyFieldVisit();
		}
		
		
		
	}
	
	public static class MyFieldVisit extends FieldVisitor{
		public MyFieldVisit() {
			super(Opcodes.ASM5);
		}
		@Override
		public void visitEnd() {
			System.out.println("here!");
			super.visitEnd();
		}
		@Override
		public void visitAttribute(Attribute arg0) {
			System.out.println(arg0);
			super.visitAttribute(arg0);
		}
		
		@Override
		public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
			System.out.println(arg0 +"~" + arg1);
			return super.visitAnnotation(arg0, arg1);
		}
		
		@Override
		public AnnotationVisitor visitTypeAnnotation(int arg0, TypePath arg1,
				String arg2, boolean arg3) {
			System.out.println(arg0 +"~" + arg1 + "~" + arg2 + "~" + arg3);
			return super.visitTypeAnnotation(arg0, arg1, arg2, arg3);
		}
		
	}
	
	public static class MyMethodVisit extends MethodVisitor{
		Map<String,List<String>> map	= null;
		String name						= null;
		String desc						= null;
		Type[] types					= null;
		public MyMethodVisit(String name,String desc) {			
			super(Opcodes.ASM5);
			this.name	= name;
			this.desc	= desc;
			map	= new HashMap<String,List<String>>();
			map.put(name, new ArrayList<String>());
			this.types	= Type.getArgumentTypes(desc);
			
			System.out.println(Arrays.toString(types));
		}
		@Override
		public void visitLocalVariable(String arg0, String arg1, String arg2,Label arg3, Label arg4, int arg5) {
			System.out.println(arg0+"~" + arg1 + "~" + arg2 + "~" +  arg3 + "~" + arg4 +"~" + arg5);
			map.get(name).add(arg0);
			super.visitLocalVariable(arg0, arg1, arg2, arg3, arg4, arg5);
		}
		
		@Override
		public void visitEnd() {
			System.out.println(map);
			super.visitEnd();
		}
	}
	
	private @Deprecated @JSONField String zzzzz= "1";
	
	public void test(long abcd,String abbb,String cccc){
		System.out.println(abcd);
	}
}
