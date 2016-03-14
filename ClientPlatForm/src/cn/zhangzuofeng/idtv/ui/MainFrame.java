package cn.zhangzuofeng.idtv.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import cn.zhangzuofeng.idtv.ui.resource.ZResourceUtils;

import com.zys.plugin.Config;
import com.zys.plugin.Plugin;
import com.zys.utils.ui.ZPanel;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -4218082519502558819L;
	private JTabbedPane tabbedPane;
	private JLabel statueLabel;
	private ZPanel mainPanel;
	private PluginManager pluginManager;

	public MainFrame() {
		initGUI();
		createMainPanel();
		initMenu();
		loadApplications();
		loadAction();
	}

	private void loadAction() {

	}

	private void initGUI() {
		ZPanel zPanel = new ZPanel();
		zPanel.setIcon(ZResourceUtils.getImageIcon("appBackground"));
		zPanel.setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		zPanel.add(tabbedPane, BorderLayout.CENTER);
		statueLabel = new JLabel();
		statueLabel.setText("statueLabel///");
		zPanel.add(statueLabel, BorderLayout.SOUTH);
		setContentPane(zPanel);
	}

	private void createMainPanel() {
		mainPanel = new ZPanel();
		mainPanel
				.setIcon(ZResourceUtils.getImageIcon("mainAppPanelBackground"));
		mainPanel.setName(ZResourceUtils.getLabel("allApplications"));
		tabbedPane.add(mainPanel, 0);
	}

	private void loadApplications() {
		mainPanel.removeAll();
		if (pluginManager == null) {
			pluginManager = new PluginManager();
		}
		List<Config> pluginConfigs = pluginManager.loadPluginConfig();
		if (pluginConfigs == null || pluginConfigs.size() < 1) {
			return;
		}
		double mainPanelWidth = mainPanel.getSize().getWidth();
		double mainPanelHeight = mainPanel.getSize().getHeight();
		mainPanelWidth = (mainPanelWidth < 400) ? 400 : mainPanelWidth;
		mainPanelHeight = (mainPanelHeight < 400) ? 400 : mainPanelHeight;
		int columns = 4;
		int rows = pluginConfigs.size() / columns + 1;
		rows = rows < 2 ? 2 : rows;
		GridLayout gridLayout = new GridLayout(rows, columns);
		mainPanel.setLayout(gridLayout);
		for (Config config : pluginConfigs) {
			PluginIconButton pluginIconButton = createPluginIconButton(config);
			mainPanel.add(pluginIconButton);
		}
		for (int i = pluginConfigs.size(); i < rows * columns; i++) {
			JPanel pluginIconButton = new JPanel();
			pluginIconButton.setOpaque(false);
			pluginIconButton.setEnabled(false);
			mainPanel.add(pluginIconButton);
		}
	}

	private PluginIconButton createPluginIconButton(final Config config) {
		PluginIconButton button = new PluginIconButton();
		// button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setText(config.getAppName());
		Icon icon = resizeIcon(config.getIcon());
		button.setIcon(icon);
		button.setPadding(40);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String appType = config.getAppType();
				if (appType.equals(Config.EMBEDDED_TYPE)) {
					addPlugin2Tab(config);
				}
			}
		});
		return button;
	}

	private Icon resizeIcon(Icon icon) {
		int iconWidth;
		int iconHeight;

		Image image = ((ImageIcon) icon).getImage();
		iconWidth = image.getWidth(null);
		iconHeight = image.getHeight(null);
		if (iconWidth > 40) {
			iconWidth = 40;
		}
		if (iconHeight > 40) {
			iconHeight = 40;
		}
		BufferedImage bufferedImage = new BufferedImage(iconWidth, iconHeight,
				BufferedImage.TYPE_INT_ARGB);
		bufferedImage.getGraphics().drawImage(image, 0, 0, iconWidth,
				iconHeight, null);
		Icon re = new ImageIcon(bufferedImage);
		return re;
	}

	private HashMap<Config, Integer> pluginMap = new HashMap<Config, Integer>();

	protected void addPlugin2Tab(Config config) {
		if (pluginMap.containsKey(config)) {
			tabbedPane.setSelectedIndex(pluginMap.get(config));
		} else {
			Plugin plugin = config.getPlugin();

			ZPanel init = plugin.init(MainFrame.this);
			if (init != null) {
				tabbedPane.add(init, config.getAppName());
				int componentCount = tabbedPane.getComponentCount();
				pluginMap.put(config, componentCount - 1);
				tabbedPane.setSelectedIndex(componentCount - 1);
			}
		}
	}

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu();
		fileMenu.setText(ZResourceUtils.getLabel("file") + "(F)");
		fileMenu.setMnemonic('F');
		JMenu helpmMenu = new JMenu();
		helpmMenu.setText(ZResourceUtils.getLabel("help") + "(H)");
		helpmMenu.setMnemonic('H');

		JMenuItem exitMenuItem = new JMenuItem();
		exitMenuItem.setText(ZResourceUtils.getLabel("exit"));
		fileMenu.add(exitMenuItem);
		exitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenuItem themeMenuItem = new JMenuItem();
		themeMenuItem.setText(ZResourceUtils.getLabel("theme"));
		helpmMenu.add(themeMenuItem);

		helpmMenu.addSeparator();

		JMenuItem aboutMenuItem = new JMenuItem();
		aboutMenuItem.setText(ZResourceUtils.getLabel("about"));
		helpmMenu.add(aboutMenuItem);

		menuBar.add(fileMenu);
		menuBar.add(helpmMenu);
		setJMenuBar(menuBar);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		MainFrame mainFrame = new MainFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setSize(screenSize.width * 2 / 3, screenSize.height * 2 / 3);
		mainFrame.setLocationRelativeTo(null);
		// mainFrame.setAlwaysOnTop(true);
		mainFrame.setVisible(true);
	}

}
