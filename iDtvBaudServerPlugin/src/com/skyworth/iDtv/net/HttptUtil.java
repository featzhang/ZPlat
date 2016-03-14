package com.skyworth.iDtv.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

public class HttptUtil {
	public static String HttpPost(String url, String content,
			Map<String, String> parameter) {
		URL postUrl;
		String result = null;
		try {
			postUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) postUrl
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(true);
			connection.setInstanceFollowRedirects(true);
			if (parameter != null && parameter.size() > 0) {
				Set<String> keySet = parameter.keySet();
				for (String key : keySet) {
					String value = (String) parameter.get(key);
					connection.setRequestProperty(key, value);
				}
			}
			connection.connect();
			DataOutputStream dataOutputStream = new DataOutputStream(
					connection.getOutputStream());
			if (content != null) {
				dataOutputStream.writeBytes("postContent="
						+ URLEncoder.encode(content));
			}
			dataOutputStream.flush();
			dataOutputStream.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			result = "";
			while ((line = reader.readLine()) != null) {
				result += line;
			}
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String HttpPostWithoutResult(String url, String content,
			Map<String, String> parameter) {
		URL postUrl;
		String result = null;
		try {
			postUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) postUrl
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(true);
			connection.setInstanceFollowRedirects(true);
			if (parameter != null && parameter.size() > 0) {
				Set<String> keySet = parameter.keySet();
				for (String key : keySet) {
					String value = (String) parameter.get(key);
					connection.setRequestProperty(key, value);
				}
			}
			connection.connect();
			DataOutputStream dataOutputStream = new DataOutputStream(
					connection.getOutputStream());
			if (content != null) {
				dataOutputStream.writeBytes("postContent="
						+ URLEncoder.encode(content));
			}
			dataOutputStream.flush();
			dataOutputStream.close();
			connection.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
