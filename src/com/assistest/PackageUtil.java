package com.assistest;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageUtil {
	
	public static void main(String[] args) {
		
		try {
			JarFile jarFile = new JarFile("D:\\workspace\\workspace2\\JavassistTest\\lib\\qdazzle_native_sdk_release.jar");
			Enumeration<JarEntry> enumeration=jarFile.entries();
			while (enumeration.hasMoreElements()) {
				JarEntry jarEntry=(JarEntry)enumeration.nextElement();
				String nameString=jarEntry.getName().replaceAll("/", ".");
				
				if(nameString.endsWith(".class")&&!nameString.contains("$")&&!nameString.contains("Callback")){
					System.out.println("classname:"+nameString);
				}				
			}
			jarFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
