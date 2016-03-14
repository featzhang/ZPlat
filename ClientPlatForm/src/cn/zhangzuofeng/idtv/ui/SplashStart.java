package cn.zhangzuofeng.idtv.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.UIManager;

public class SplashStart {

	private ArrayBlockingQueue<Runnable> queue;
	private int process;

	public SplashStart() {
	}

	public void start() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		final SplashDialog splashWindow = new SplashDialog("iDTVResClient.png");

		new Thread() {
			public void run() {
				List<LoadTask> loadTasks = new ArrayList<LoadTask>();
				loadTasks.add(new LoadTask(LoadTask.MAIN_TASK));
				loadTasks.add(new LoadTask(LoadTask.UPDATE_TASK));
				queue = new ArrayBlockingQueue<Runnable>(loadTasks.size());
				ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 5,
						TimeUnit.MINUTES, queue,
						new ThreadPoolExecutor.CallerRunsPolicy());
				for (LoadTask loadTask : loadTasks) {
					executor.execute(loadTask);
				}
				executor.shutdown();
				while (!executor.isTerminated()) {
					try {
						String string = catString(loadTasks);
						System.out.println(string);
						splashWindow.updateProcess(string, process);
						Thread.sleep(1000);
					} catch (Exception e) {
					}
				}
				splashWindow.setVisible(false);
				System.exit(0);
			};
		}.start();

		splashWindow.setVisible(true);
	}

	private String catString(List<LoadTask> loadTasks) {
		String string = "<html><body>";
		process = 0;
		int count = 0;
		int brcount = 0;
		for (int i = 0; i < loadTasks.size(); i++) {
			LoadTask loadTask = loadTasks.get(i);
			String message = loadTask.getMessage();
			int process2 = loadTask.getProcess();
			process+=process2;
			if ((process2 != 100)) {
				if (count >= brcount) {
					string += "<br/>";
					brcount++;
				}
				string += loadTask.getName() + message;
				count++;
			}

		}
		string += "</body></html>";
		process/=loadTasks.size();
		return string;

	}
}

class LoadTask implements Runnable {
	private String name;
	private String message = "loading...";
	private int process = 0;

	public int getProcess() {
		return process;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static final String MAIN_TASK = "main";
	public static final String UPDATE_TASK = "update";

	public LoadTask(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void run() {
		if (name.equals(MAIN_TASK)) {
			int i = 0;
			while (i++ < 20) {
				process = i * 100 / 20;
				System.out.println(name + ": " + i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} else {
			int i = 0;
			while (i++ < 10) {
				process = i * 100 / 10;
				System.out.println(name + ": " + i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		message = "finish.";
	}
}
