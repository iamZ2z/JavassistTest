package com.assistest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

public class AssistTest {

	public static void main(String[] args) {
		//获取类的所有方法，并在结构体加一行代码
		/*String packname = "com.assistest";

		try {
			List<String> classNames = getClassName(packname);
			for (String srcpackname : classNames) {
				System.out.println(srcpackname);

				ClassPool pool = ClassPool.getDefault();
				CtClass ctClass = pool.get(srcpackname);
				// 添加无参构造体
				CtConstructor ctConstructor = ctClass.getConstructors()[0];
				ctConstructor
						.insertBeforeBody("{String newstr=\"Assist新加的内容\";}");

				ctClass.writeFile("D:\\workspace\\workspace2\\JavassistTest\\lib");

				System.out.println("生成成功");
			}
		} catch (CannotCompileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}*/
		
		
		//已存在无参构造
		/*String srcpackname = "cn.qdazzle.sdk.common.utils.DebugHelper";
		try {
			ClassPool pool = ClassPool.getDefault();
			pool.insertClassPath(srcpackname);
			CtClass ctClass = pool.get(srcpackname);
			// 获取构造体
			CtConstructor ctConstructor = ctClass.getConstructors()[0];
			//ctConstructor.setBody("{String newstr=\"Assist新加的内容\";}");
			//ctClass.addConstructor(ctConstructor);

			ctClass.writeFile("D:\\workspace\\workspace2\\JavassistTest\\lib");
			System.out.println("生成成功"+ctConstructor);	
		} catch (CannotCompileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}*/
		
		
		//jar包list
		String srcpackname = "D:\\workspace\\workspace2\\JavassistTest\\lib\\qdazzle_native_sdk_release.jar";
		try {
			JarFile jarFile = new JarFile(srcpackname);
			Enumeration<JarEntry> enumeration=jarFile.entries();
			while (enumeration.hasMoreElements()) {
				JarEntry jarEntry=(JarEntry)enumeration.nextElement();
				String nameString=jarEntry.getName().replaceAll("/", ".");
				
				ClassPool pool = ClassPool.getDefault();
				pool.insertClassPath(srcpackname);
				CtClass ctClass;
				
				if(nameString.endsWith(".class")&&!nameString.contains("$")&&!nameString.contains("Callback")&&!nameString.contains("Dialog")
						&&!nameString.contains("View")&&!nameString.contains("Layout") &&!nameString.contains("Listener") 
						&&!nameString.contains("DBHelper")){
					nameString=nameString.replaceAll(".class", "");
					System.out.println("路径:"+nameString);
					
					ctClass = pool.get(nameString);
					// 获取构造体
					CtConstructor ctConstructor = ctClass.getConstructors()[0];
					ctConstructor.insertBeforeBody("{String newstr=\"Assist新加的内容\";}");					
					ctClass.writeFile("D:\\workspace\\workspace2\\JavassistTest\\lib");

					System.out.println("生成成功"+ctConstructor);
				}else if(nameString.endsWith(".class")){
					nameString=nameString.replaceAll(".class", "");
					ctClass = pool.get(nameString);
					ctClass.writeFile("D:\\workspace\\workspace2\\JavassistTest\\lib");					
					System.out.println("无修改"+nameString);					
				}
				
			}
			jarFile.close();		
		} catch (CannotCompileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

	private static List<String> getClassName(String packageName) {
		String filePath = ClassLoader.getSystemResource("").getPath()
				+ packageName.replace(".", "\\");
		List<String> fileNames = getClassName(filePath, null);
		return fileNames;
	}

	private static List<String> getClassName(String filePath,
			List<String> className) {
		List<String> myClassName = new ArrayList<String>();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		for (File childFile : childFiles) {
			if (childFile.isDirectory()) {
				myClassName.addAll(getClassName(childFile.getPath(),
						myClassName));
			} else {
				String childFilePath = childFile.getPath();
				childFilePath = childFilePath.substring(
						childFilePath.indexOf("\\classes") + 43,
						childFilePath.lastIndexOf("."));
				childFilePath = childFilePath.replace("\\", ".");
				myClassName.add(childFilePath);
			}
		}
		return myClassName;
	}
}
