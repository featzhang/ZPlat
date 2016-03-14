package com.skyworth.iDtv.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Calendar;
import java.util.List;

import com.skyworth.iDtv.entity.UserData;

public class JSONAssist extends ReflectionBase {

	public static <T> String toJSONAssistString(List<T> list) {
		Class configClass = getJSONClass();
		Method method = null;
		try {
			method = configClass.getMethod("parseArray",
					new Class[] { List.class });
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		String invoke = null;
		try {
			invoke = (String) method.invoke(configClass, new Object[] { list });
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return invoke;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> parseArray(String httpPost, Class<T> class1) {
		Class configClass = getJSONClass();
		Method method = null;
		try {
			method = configClass.getMethod("parseArray", new Class[] {
					String.class, Class.class });
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		List<T> invoke = null;
		try {
			invoke = (List<T>) method.invoke(configClass, new Object[] {
					httpPost, class1 });
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return invoke;
	}

	public static <T> String toJSONString(T t) {
		Class configClass = getJSONClass();
		Method method = null;
		try {
			method = configClass.getMethod("toJSONString",
					new Class[] { Object.class });
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		String invoke = null;
		try {
			invoke = (String) method.invoke(configClass, t);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return invoke;
	}

	private static Class getJSONClass() {
		String userDir = System.getProperty("user.dir");
		userDir += File.separator + "plugin";
		userDir += File.separator + "lib";
		userDir += File.separator + "fastjson-1.1.10.jar";
		System.out.println(userDir);
		File file = new File(userDir);
		URL url = null;
		try {
			url = file.toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.print("file \'" + file + "\' converse to url error!");
		}
		URLClassLoader loader = new URLClassLoader(new URL[] { url });
		Class<?> configClass = null;
		try {
			configClass = loader.loadClass("com.alibaba.fastjson.JSON");
		} catch (ClassNotFoundException e) {
			System.err.println("class \'Config\'not found in file \'" + file
					+ "\'");
		}
		return configClass;
	}

	public static <T> T parseObject(String string, Class<T> class1) {

		Class configClass = getJSONClass();
		Method method = null;
		try {
			method = configClass.getMethod("parseObject", new Class[] {
					String.class, Class.class });
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		T invoke = null;
		try {
			invoke = (T) method.invoke(configClass, new Object[] { string,
					class1 });
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println(invoke);
		return invoke;
	}

	public static void main(String[] args) {
		UserData t = new UserData();
		JSONAssist.toJSONString(t);
	}
}
