import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.skyworth.iDtv.ui.resource.ZResourceUtils;
import com.zys.plugin.Plugin;

public class Config implements com.zys.plugin.Config {

	@Override
	public String getAppName() {
		return "server manager";
	}

	@Override
	public String getAppType() {
		return EMBEDDED_TYPE;
	}

	@Override
	public String getResourcePath() {
		return null;
	}

	@Override
	public Plugin getPlugin() {
		return new com.skyworth.iDtv.DataRateServerManager();
	}

	@Override
	public Icon getIcon() {
		System.out.println("getIcon");
		ImageIcon appImage = ZResourceUtils.getAppImageIcon();
		return appImage;
	}

	@Override
	public int getVision() {
		return 1408041620;
	}
}
