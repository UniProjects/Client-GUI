package org.popov.belezirev.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.popov.belezirev.client.Client;
import org.popov.belezirev.gui.client.ClientGui;

public class CommunicationPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_TEXTAREA_VALUE = "";
	private static final String DEFAULT_DATE_FORMATER = "yyyy.MM.dd  HH:mm";
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

	public CommunicationPanel() {
		dataFormat = new SimpleDateFormat(DEFAULT_DATE_FORMATER);
	}

	public void createClient() {
		client = new ClientGui("localhost", 10513, "test");
		try {
			client.initClient();
			client.sendMessage("test-username");
		} catch (IOException e) {
			e.printStackTrace();
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
			client.sendMessage(message);
			// calendar = Calendar.getInstance();
			// currentTime = dataFormat.format(calendar.getTime());
			// if (!message.isEmpty()) {
			// chatTextArea.append(currentTime + " | " + serverResponse +
			// System.lineSeparator());
			// }
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
		// LISTS ONLINE USERS --- IN DEV!!!!! DEMO !!
		String testUsers[] = { "Hello", "World", "123", "Bye-bye", "hqgq", "1f21f", "121f", "balbla", "f112ef21f2ff12",
				"12", "1f2312", "12312f", "World", "123", "Bye-bye", "hqgq", "1f21f", "121f", "balbla",
				"f112ef21f2ff12", "12", "1f2312", "12312f", "World", "123", "Bye-bye", "hqgq", "1f21f", "121f",
				"balbla", "f112ef21f2ff12", "12", "1f2312", "12312f", "World", "123", "Bye-bye", "hqgq", "1f21f",
				"121f", "balbla", "f112ef21f2ff12", "12", "1f2312", "12312f" };
		//////
		JList<String> onlineUsersDisplayList = new JList<>(testUsers);
		onlineUsersDisplayList.setLayoutOrientation(JList.VERTICAL);
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
