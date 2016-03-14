package cn.zhangzuofeng.idtv.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.zys.plugin.Config;
import com.zys.plugin.Plugin;

public class PluginManager {
	private String pluginPath = "plugin";
	private String pluginFilePostfix = "jar";

	public List<URL> loadLibNameList() {
		List<URL> files = null;
		String userDir = System.getProperty("user.dir");
		userDir += File.separator + pluginPath;
		File file = new File(userDir);
		if (file.exists() && file.isDirectory()) {
			files = new ArrayList<URL>();
			File[] listFiles = file.listFiles();
			for (File file2 : listFiles) {
				if (file2.getAbsolutePath().endsWith(pluginFilePostfix)) {
					if (file2.isFile()) {
						try {
							files.add(file2.toURL());
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} else {
			return null;
		}
		return files;
	}

	public List<Plugin> loadPlugins() {
		ArrayList<Plugin> plugins = new ArrayList<Plugin>();

		List<File> jarFiles = findJarFiles();

		for (int i = 0; i < jarFiles.size(); i++) {
			File file = jarFiles.get(i);
			URL url = null;
			try {
				url = file.toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
				System.err
						.print("file \'" + file + "\' converse to url error!");
				continue;
			}
			List<URL> loadLibNameList = loadLibNameList();
			URL[] urls = new URL[loadLibNameList.size() + 1];
			for (int ii = 0; ii < loadLibNameList.size(); ii++) {
				urls[ii] = loadLibNameList.get(ii);
			}
			urls[loadLibNameList.size()] = url;
			URLClassLoader loader = new URLClassLoader(urls);
			Class configClass = null;
			try {
				configClass = loader.loadClass("Config");
			} catch (ClassNotFoundException e) {
				System.err.println("class \'Config\'not found in file \'"
						+ file + "\'");
				continue;
			}
			Config config = null;
			try {
				config = (Config) configClass.newInstance();
			} catch (InstantiationException e) {
				System.err.println("config class instantiation error!");
				continue;
			} catch (IllegalAccessException e) {
				System.err.println("config class illegal access error!");
				continue;
			}
			Plugin plugin = config.getPlugin();
			// plugin.init();
			plugins.add(plugin);
		}
		return plugins;
	}

	public List<Config> loadPluginConfig() {

		ArrayList<Config> pluginConfigs = new ArrayList<Config>();

		List<File> jarFiles = findJarFiles();

		for (int i = 0; i < jarFiles.size(); i++) {
			File file = jarFiles.get(i);
			URL url = null;
			try {
				url = file.toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
				System.err
						.print("file \'" + file + "\' converse to url error!");
				continue;
			}
			URLClassLoader loader = new URLClassLoader(new URL[] { url });
			Class configClass = null;
			try {
				configClass = loader.loadClass("Config");
			} catch (ClassNotFoundException e) {
				System.err.println("class \'Config\'not found in file \'"
						+ file + "\'");
				continue;
			}
			Config pluginConfig = null;
			try {
				pluginConfig = (Config) configClass.newInstance();
				pluginConfigs.add(pluginConfig);
			} catch (InstantiationException e) {
				System.err.println("config class instantiation error!");
				continue;
			} catch (IllegalAccessException e) {
				System.err.println("config class illegal access error!");
				continue;
			}
			// Plugin plugin = config.getPlugin();
			// plugin.init();

		}
		return pluginConfigs;
	}

	private List<File> findJarFiles() {
		List<File> files = null;
		String userDir = System.getProperty("user.dir");
		userDir += File.separator + pluginPath;
		File file = new File(userDir);
		if (file.exists() && file.isDirectory()) {
			files = new ArrayList<File>();
			File[] listFiles = file.listFiles();
			for (File file2 : listFiles) {
				if (file2.getAbsolutePath().endsWith(pluginFilePostfix)) {
					if (file2.isFile()) {
						files.add(file2);
					}
				}
			}
		} else {
			return null;
		}
		return files;
	}

}
