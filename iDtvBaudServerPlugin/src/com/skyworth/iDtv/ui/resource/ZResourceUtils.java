package com.skyworth.iDtv.ui.resource;

import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;

public class ZResourceUtils {

	private static final String LABEL_SUFFIX = ".label";
	private static ResourceBundle resources;
	private final static String imageSuffix = ".image";

	static {
		try {
			resources = ResourceBundle
					.getBundle("com.skyworth.iDtv.ui.resource.client",
							Locale.getDefault());
		} catch (MissingResourceException mre) {
			System.err
					.println("cn/zhangzuofeng/AddressList.properties not found");
			System.exit(1);
		}
	}

	private static String getString(String key) {
		String str;
		try {
			str = resources.getString(key);
		} catch (MissingResourceException mre) {
			str = null;
			mre.printStackTrace();
		}
		return str;
	}

	public static String getLabel(String key) {
		String string = getString(key + LABEL_SUFFIX);
		return string;
	}

	public URL getResourceURI(String key) {
		String name = getString(key);
		if (name != null) {
			return this.getClass().getResource(name);
		}
		return null;
	}

	public static ImageIcon getImageIcon(String cmd) {
		URL url = new ZResourceUtils().getResourceURI(cmd + imageSuffix);
		if (url != null) {
			return new ImageIcon(url);
		}
		System.out.println("file " + cmd + imageSuffix + " missing !");
		return null;
	}

	public static java.awt.Image getImage(String cmd) {

		ImageIcon imageIcon = getImageIcon(cmd);
		if (imageIcon != null)
			return imageIcon.getImage();
		return null;
	}

	public static ImageIcon getAppImageIcon() {
		return getImageIcon("Application");
	}

	public static java.awt.Image getAppImage() {
		return getAppImageIcon().getImage();
	}

	public static ImageIcon getWelcomeImage() {
		return getImageIcon("welcome");
	}

	public static ImageIcon getWallpaper() {
		return getImageIcon("wallpaper");
	}

	public static void main(String[] args) {
		ZResourceUtils.getImage("app");
	}

}
