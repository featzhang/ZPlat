package com.skyworth.iDtv.net;

public class NetConfig {
	public static String getServerIP() {
		return "http://localhost:8080";
	}

	public static String getAppName() {
		return "ResourceServer";
	}

	public static String getResourceServer() {
		return getServerIP() + "/ResourceServer/";
	}
}
