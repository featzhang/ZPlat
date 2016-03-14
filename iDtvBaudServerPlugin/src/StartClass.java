import java.awt.EventQueue;

import javax.swing.UIManager;

import com.skyworth.iDtv.dao.UserStatue;
import com.skyworth.iDtv.ui.MainFrame;
import com.skyworth.iDtv.ui.resource.ZResourceUtils;

public class StartClass {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setIconImage(ZResourceUtils.getAppImage());
					frame.setTitle("Test start class");
					UserStatue.login("Aaron", "abc.123");
					frame.doLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
