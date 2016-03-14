package com.skyworth.iDtv.ui.userManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import com.skyworth.iDtv.dao.UserManager;
import com.skyworth.iDtv.dao.UserStyle;
import com.skyworth.iDtv.entity.UserData;
import com.skyworth.iDtv.entity.UserTypeData;
import com.skyworth.iDtv.net.ClientRequest;
import com.skyworth.iDtv.ui.resource.ZResourceUtils;
import com.zys.utils.ui.ZDialog;

public class UserManagerDialog extends ZDialog {

	private static final long serialVersionUID = -5521667292543504354L;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		JFrame frame = new JFrame();
		UserManagerDialog userManagerFrame = new UserManagerDialog(frame);
		userManagerFrame.setLocationByPlatform(true);
		userManagerFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		userManagerFrame.setSize(400, 400);
		userManagerFrame.setVisible(true);
	}

	private UserManagerTable table;
	private UserManagerTableModel tableModel;
	private JButton addButton;
	private JButton deleteButton;
	private JButton cancelButton;

	private JButton sureButton;
	private JComboBox comboBoxCellEditor;

	public UserManagerDialog() {
		initComponents();
		loadAction();
		loadData();
	}

	public UserManagerDialog(Dialog owner) {
		super(owner);
		initComponents();
		loadAction();
		loadData();
	}

	public UserManagerDialog(Dialog owner, boolean modal) {
		super(owner, modal);
		initComponents();
		loadAction();
		loadData();
	}

	public UserManagerDialog(Frame owner) {
		super(owner);
		initComponents();
		loadAction();
		loadData();
	}

	public UserManagerDialog(Frame owner, boolean modal) {
		super(owner, modal);
		initComponents();
		loadAction();
		loadData();
	}

	private void loadData() {
		List<UserTypeData> userTypes = UserStyle.getUserStyles();
		comboBoxCellEditor = new JComboBox();
		HashMap<String, Integer> typeNameMap = new HashMap<String, Integer>();
		for (int i = 0; i < userTypes.size(); i++) {
			UserTypeData userTypeData = userTypes.get(i);
			String typeName = userTypeData.getTypeName();
			typeName = ZResourceUtils.getLabel(typeName);
			int id = userTypeData.getId();
			typeNameMap.put(typeName, id);
			comboBoxCellEditor.addItem(typeName);
		}
		tableModel.setTypeNameMaps(typeNameMap);
		table.getColumnModel().getColumn(2)
				.setCellEditor(new DefaultCellEditor(comboBoxCellEditor));
		JPasswordField passwordEdit = new JPasswordField();
		passwordEdit.setBorder(null);
		passwordEdit.setCaretColor(Color.blue);
		table.getColumnModel().getColumn(1)
				.setCellEditor(new DefaultCellEditor(passwordEdit));
		table.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
					private static final long serialVersionUID = -4807504363057990939L;

					// 重写 setValue 方法
					public void setValue(Object value) {
						if (value == null) {
							super.setValue("******");
							return;
						}
						String password = "";
						int wordLong = value.toString().length();
						for (int i = 0; i < wordLong; i++)
							password += "*";
						super.setValue(password);
					}
				});
		List<UserData> allUsers = new ClientRequest().getAllUsers();
		tableModel.setUserDataList(allUsers);

	}

	private void initComponents() {
		setBounds(0, 0, 460, 340);
		JPanel contentPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(2, 2));
		JScrollPane scrollPane = new JScrollPane();
		centerPanel.add(scrollPane, BorderLayout.CENTER);

		table = new UserManagerTable();
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();
		FlowLayout panelLayout = new FlowLayout();
		panelLayout.setAlignment(FlowLayout.LEFT);
		panel.setLayout(panelLayout);
		panel.setOpaque(false);
		centerPanel.add(panel, BorderLayout.NORTH);

		addButton = new JButton();
		addButton.setText(ZResourceUtils.getLabel("addUser"));
		panel.add(addButton);

		deleteButton = new JButton();
		deleteButton.setText(ZResourceUtils.getLabel("deleteUser"));
		panel.add(deleteButton);

		contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		contentPanel.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		sureButton = new JButton();
		sureButton.setText(ZResourceUtils.getLabel("sure"));

		cancelButton = new JButton();
		cancelButton.setText(ZResourceUtils.getLabel("cancel"));

		buttonPanel.add(sureButton);
		buttonPanel.add(cancelButton);

		contentPanel.add(centerPanel, BorderLayout.CENTER);
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);
		setContentPane(contentPanel);
	}

	private void loadAction() {
		tableModel = new UserManagerTableModel();
		List<UserData> userDatas = null;
		tableModel.setUserDataList(userDatas);
		table.setModel(tableModel);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.addRow(new UserData());
				tableModel.fireTableDataChanged();
			}
		});
		sureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<UserData> userDatas2 = tableModel.getUserDatas();
				if (userDatas2 == null || userDatas2.size() == 0) {
					JOptionPane.showMessageDialog(UserManagerDialog.this,
							"content empty!");
				}
				for (int i = 0; i < userDatas2.size(); i++) {
					UserData userData = userDatas2.get(i);
					boolean b = userData.CheckDataValidity();
					if (!b) {
						JOptionPane.showMessageDialog(UserManagerDialog.this,
								userData.toString() + " data error!");
						return;
					}
				}
				UserManager.uploadModifies(userDatas2);
				UserManagerDialog.this.dispose();
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserManagerDialog.this.dispose();
			}
		});
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(UserManagerDialog.this,
							"not select row!");
					return;
				}
				tableModel.removeRow(selectedRow);
			}
		});
	}
}
