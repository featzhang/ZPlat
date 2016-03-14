package com.zys.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	public static String readFile2String(File file) throws IOException {
		InputStream inputStream = new FileInputStream(file);
		String s = readInputStream2String(inputStream);
		return s;
	}

	public static String readInputStream2String(InputStream inputStream) throws IOException {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String s = "";
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			s += line;
		}
		return s;
	}

	public static void writeString2File(String string, File file) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		bufferedWriter.write(string);
	}

	/**
	 * 复制文件
	 * 
	 * @param srcFileName
	 * @param distFileName
	 * @param mod
	 *            模式�?覆盖�?不覆�?
	 */
	public static boolean copyFileToFile(String srcFileName, String distFileName, int mod)
			throws Exception {
		File srcFile = new File(srcFileName);
		File distFile = new File(distFileName);
		if (!srcFile.exists()) {
			throw new Exception("source file missing!");
		}
		if (distFile.exists()) {
			System.err.print("file " + distFileName + " exists! "
					+ (mod == 1 ? "copy fail!" : "overwrite!"));
			if (mod == 1) {
				return false;
			}
		}
		FileInputStream fileInputStream = new FileInputStream(srcFile);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		FileOutputStream fileOutputStream = new FileOutputStream(distFile);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
		int c;
		while ((c = inputStreamReader.read()) != -1) {
			outputStreamWriter.write(c);
		}
		return true;
	}

	public static void copyFileToDir(String srcFileName, String distDir, int mode) throws Exception {
		File srcFile = new File(srcFileName);
		if (!srcFile.exists() || srcFile.isFile()) {
			throw new Exception("resource file missing or is not file!");
		}
		File distDirFile = new File(distDir);
		checkFileDir(distDirFile);
		String srcFileName1 = srcFile.getName();

		copyFileToFile(srcFileName, distDir + File.pathSeparator + srcFileName1, mode);
	}

	public static void checkFileDir(File dir) {
		if (!dir.exists()) {
			checkFileDir(dir.getParentFile());
			dir.mkdir();
		} else {
			return;
		}
	}

	public static void copyDirToDir(String srcFileDir, String distDir, int mode) {

	}

	public static List<File> getFilesInDir(String dirName) {
		File file = new File(dirName);
		return getFilesInDir(file);
	}

	public static List<File> getFilesInDir(File file) {
		if (!file.exists() && !file.isDirectory()) {
			return null;
		}
		List<File> list = new ArrayList<File>();
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				List<File> filesInDir = getFilesInDir(files[i]);
				list.addAll(filesInDir);
			} else {
				list.add(files[i]);
			}
		}
		return null;
	}
}
