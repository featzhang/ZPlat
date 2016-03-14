package com.zys.utils;

public class SystemInfo {
	public static String OS_WINDOWS_7 = "Windows 7";
	public static String OS_WINDOWS_XP = "Windows XP";

	public static String getSytemStyle() {
		String property = System.getProperty("os.name");
//		System.out.println(property);
		return property;
	}
}
