package com.ayto.multas.as400;

import java.io.*;
import java.util.*;

import com.ayto.multas.util.*;

public class ConfiguracionAS400 {

	private String ip;
	
	private String user;
	
	private String password;
	
	public ConfiguracionAS400() throws IOException {
		
		Properties properties = new Properties();
		
		properties.load(ClassUtil.INSTANCE.getResourceFromClasspath("as400.properties"));
		
		ip = properties.getProperty("ip");
		
		user = properties.getProperty("user");
		
		password = properties.getProperty("password");
	}

	public String getIp() {
		return ip;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
}
