package org.popov.belezirev.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class CommunicationPanel extends JPanel {
	private static final int SIZE_INCREMENTER = 2;
	private JList<Object> onlineUsersDisplayList;
	private JTextArea chatTextArea;
	private JTextField textLine;
	private JButton sendButton;
	private SimpleDateFormat dataFormat;
	private String message;
	private Calendar calendar;
	private String currentTime;
	private JScrollPane textAreaScroller;

	public CommunicationPanel() {
		setLayout(new BorderLayout());
		chatTextArea = new JTextArea();
		textLine = new JTextField();
		sendButton = new JButton("Send");
		dataFormat = new SimpleDateFormat("yyyy.MM.dd : HH:mm");

		configureSendButton();
		configureChatTextArea();
		textAreaScroller = new JScrollPane(chatTextArea);
		configureTextAreaScroller();
		add(textAreaScroller, BorderLayout.CENTER);

		add(createUsersListPanel(), BorderLayout.WEST);

		add(createFooterPanel(), BorderLayout.SOUTH);

		SendActionListener sendActionListener = new SendActionListener();
		sendButton.addActionListener(sendActionListener);
		textLine.addActionListener(sendActionListener);
	}

	private void configureSendButton() {
		sendButton.setFocusable(false);
	}

	private void configureTextAreaScroller() {
		textAreaScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textAreaScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	private JPanel createFooterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(textLine, BorderLayout.CENTER);
		panel.add(sendButton, BorderLayout.EAST);
		return panel;
	}

	private JPanel createUsersListPanel() {
		createOnlineUsersDisplayList();
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Online Users"));
		panel.setLayout(new BorderLayout());
		JScrollPane listScroll = new JScrollPane(onlineUsersDisplayList);
		listScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel.add(listScroll);
		return panel;
	}

	private void createOnlineUsersDisplayList() {
		// LISTS ONLINE USERS --- IN DEV!!!!! DEMO !!
		Object data[] = { "Hello", "World", "123", "Bye-bye", "hqgq", "1f21f", "121f", "balbla", "f112ef21f2ff12", "12",
				"1f2312", "12312f", "World", "123", "Bye-bye", "hqgq", "1f21f", "121f", "balbla", "f112ef21f2ff12",
				"12", "1f2312", "12312f", "World", "123", "Bye-bye", "hqgq", "1f21f", "121f", "balbla",
				"f112ef21f2ff12", "12", "1f2312", "12312f", "World", "123", "Bye-bye", "hqgq", "1f21f", "121f",
				"balbla", "f112ef21f2ff12", "12", "1f2312", "12312f" };
		//////
		onlineUsersDisplayList = new JList<>(data);
		onlineUsersDisplayList.setLayoutOrientation(JList.VERTICAL);
		onlineUsersDisplayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		onlineUsersDisplayList.setFocusable(false);
		final int TOP_MARGIN_OFFSET = 0;
		final int LEFT_MARGIN_OFFSET = 5;
		final int BOTTOM_MARGIN_OFFSET = 0;
		final int RIGHT_MARGIN_OFFSET = 15;
		onlineUsersDisplayList.setBorder(
				new EmptyBorder(TOP_MARGIN_OFFSET, LEFT_MARGIN_OFFSET, BOTTOM_MARGIN_OFFSET, RIGHT_MARGIN_OFFSET));
	}

	private void configureChatTextArea() {
		Font oldFont = chatTextArea.getFont();
		Font newFont = new Font(oldFont.getName(), Font.TYPE1_FONT, oldFont.getSize() + SIZE_INCREMENTER);
		chatTextArea.setEditable(false);
		chatTextArea.setFont(newFont);
	}

	private class SendActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			message = textLine.getText();
			calendar = Calendar.getInstance();
			currentTime = dataFormat.format(calendar.getTime());
			if (!message.isEmpty()) {
				chatTextArea.append(currentTime + " | " + message + System.lineSeparator());
			}
			textLine.setText("");
		}
	}

	public void setFocus() {
		textLine.requestFocus();
	}

}
