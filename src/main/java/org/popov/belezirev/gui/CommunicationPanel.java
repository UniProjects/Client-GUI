package org.popov.belezirev.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.popov.belezirev.gui.client.ClientGui;

public class CommunicationPanel extends JPanel {
	private static final int DEFAULT_INPUT_AREA_WIDTH = 25;

	private static final int DEFAULT_DISPLAY_LIST_WIDTH = 60;

	private static final int OK_BUTTON = 0;

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_TEXTAREA_VALUE = "";
	private static final String DEFAULT_DATE_FORMATER = "HH:mm";
	private static final String USERS_PANEL_TITLE = "Online Users";
	private static final int TOP_MARGIN_OFFSET = 0;
	private static final int LEFT_MARGIN_OFFSET = 5;
	private static final int BOTTOM_MARGIN_OFFSET = 0;
	private static final int RIGHT_MARGIN_OFFSET = 15;

	private JTextArea chatTextArea;
	private JTextField textLine;
	private JButton sendButton;
	private SimpleDateFormat dataFormat;
	private Calendar calendar;
	private String currentTime;
	private JScrollPane textAreaScroller;
	private ClientGui client;
	private DefaultListModel<String> model;

	public CommunicationPanel() {
		dataFormat = new SimpleDateFormat(DEFAULT_DATE_FORMATER);
	}

	public void createClient() {
		client = new ClientGui("localhost", 10513, "test");
		String username = getClientUserName();
		try {
			client.initClient();
			client.sendMessage(username);
			addToDisplayList(username);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addToDisplayList(String username) {
		model.addElement(username);
	}

	private String getClientUserName() {
		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter your username: ");
		JTextField inputArea = new JTextField(DEFAULT_INPUT_AREA_WIDTH);
		panel.add(label);
		panel.add(inputArea);
		while (true) {
			int selectedOption = JOptionPane.showOptionDialog(null, panel, "SIGN-IN", JOptionPane.NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			String username = inputArea.getText();
			if (selectedOption == OK_BUTTON && username != null && !username.isEmpty()) {
				return username;
			}
			if (selectedOption == JOptionPane.CLOSED_OPTION) {
				System.exit(0);
			}
		}
	}

	public void addPanels() {
		add(textAreaScroller, BorderLayout.CENTER);

		add(createUsersListPanel(), BorderLayout.WEST);

		add(createFooterPanel(), BorderLayout.SOUTH);
	}

	public void createListener() {
		ActionListener sendActionListener = getActionListener();
		sendButton.addActionListener(sendActionListener);
		textLine.addActionListener(sendActionListener);
		new Thread(() -> {
			while (true) {
				String readMessage = client.readMessage();
				if (readMessage != null && !readMessage.isEmpty()) {
					chatTextArea.append(readMessage + System.lineSeparator());
				}
			}
		}).start();
	}

	private ActionListener getActionListener() {
		return (e) -> {
			String message = textLine.getText();
			client.sendMessage(currentTime + " | " + message);
			textLine.setText(DEFAULT_TEXTAREA_VALUE);
		};
	}

	private JPanel createFooterPanel() {
		JPanel footerPanel = new JPanel();
		footerPanel.setLayout(new BorderLayout());
		footerPanel.add(textLine, BorderLayout.CENTER);
		footerPanel.add(sendButton, BorderLayout.EAST);
		return footerPanel;
	}

	private JPanel createUsersListPanel() {
		JList<String> onlineUsersDisplayList = createOnlineUsersDisplayList();
		JPanel usersListPanel = new JPanel();
		usersListPanel.setBorder(new TitledBorder(USERS_PANEL_TITLE));
		usersListPanel.setLayout(new BorderLayout());
		JScrollPane listScroll = new JScrollPane(onlineUsersDisplayList);
		listScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		usersListPanel.add(listScroll);
		return usersListPanel;
	}

	private JList<String> createOnlineUsersDisplayList() {
		model = new DefaultListModel<>();
		JList<String> onlineUsersDisplayList = new JList<>(model);
		onlineUsersDisplayList.setFixedCellWidth(DEFAULT_DISPLAY_LIST_WIDTH);
		model.addElement("hello");
		model.addElement("hel2lo");
		onlineUsersDisplayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		onlineUsersDisplayList.setFocusable(false);
		onlineUsersDisplayList.setBorder(
				new EmptyBorder(TOP_MARGIN_OFFSET, LEFT_MARGIN_OFFSET, BOTTOM_MARGIN_OFFSET, RIGHT_MARGIN_OFFSET));
		return onlineUsersDisplayList;
	}

	public JScrollPane getTextAreaScroller() {
		return textAreaScroller;
	}

	public void setTextAreaScroller(JScrollPane textAreaScroller) {
		this.textAreaScroller = textAreaScroller;
	}

	public JButton getSendButton() {
		return sendButton;
	}

	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
	}

	public JTextField getTextLine() {
		return textLine;
	}

	public void setTextLine(JTextField textLine) {
		this.textLine = textLine;
	}

	public JTextArea getChatTextArea() {
		return chatTextArea;
	}

	public void setChatTextArea(JTextArea chatTextArea) {
		this.chatTextArea = chatTextArea;
	}

}
