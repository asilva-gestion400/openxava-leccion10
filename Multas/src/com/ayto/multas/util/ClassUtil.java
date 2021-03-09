package com.ayto.multas.util;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.*;

public enum ClassUtil {
	
	INSTANCE;
	
	private static final Logger log = Logger.getLogger(ClassUtil.class.getCanonicalName());
	
	public InputStream getResourceFromClasspath(String relativePath) throws IOException {
		
		try {
			
			return new FileInputStream(getPathClass(relativePath));
			
		} catch (IOException e) {
			
			return Thread.currentThread().getContextClassLoader().getResourceAsStream(relativePath);
		}
	}
	
	public File getPathClass(String relativePath) throws IOException {
		
		File file = getDefaultPathClass(relativePath);
		
		Enumeration<URL> en = Thread.currentThread().getContextClassLoader().getResources("");
		
		while (!file.exists() && en.hasMoreElements()) {
			
			try {
				
				file = getFile(en.nextElement(), relativePath);
				
			} catch (URISyntaxException ex) {
				
				log.warning(ex.getMessage());
			}
		}
		
		return file;
	}
	
	public File getDefaultPathClass(String relativePath) {
		
		return new File(relativePath);
	}
	
	public String getPackage(Class<?> type) {
		
		return type.getPackage().getName();
	}
	
	private File getFile(URL url, String relativePath) throws URISyntaxException {
		
		URI uri = url.toURI();
		
		String path = uri.getPath();
		
		String newPath = path + relativePath;
		
		return Paths.get(uri.resolve(newPath)).toFile();
	}
}
