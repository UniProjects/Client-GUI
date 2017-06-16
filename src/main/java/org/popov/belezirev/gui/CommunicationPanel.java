package org.popov.belezirev.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.popov.belezirev.client.Credentials;
import org.popov.belezirev.gui.client.ClientGui;
import org.popov.belezirev.gui.client.MessageProcessor;

public class CommunicationPanel extends JPanel {
	private static final int SIGN_IN_PANEL_COMPONETS_SEPARATOR_WIDTH = 0;

	private static final int SIGN_IN_PANEL_COMPONETS_SEPARATOR_HEIGHT = 15;

	private static final int DEFAULT_INPUT_AREA_WIDTH = 25;

	private static final int DEFAULT_DISPLAY_LIST_WIDTH = 60;

	private static final int OK_BUTTON = 0;

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_TEXTAREA_VALUE = "";
	private static final String USERS_PANEL_TITLE = "Online Users";
	private static final int TOP_MARGIN_OFFSET = 0;
	private static final int LEFT_MARGIN_OFFSET = 5;
	private static final int BOTTOM_MARGIN_OFFSET = 0;
	private static final int RIGHT_MARGIN_OFFSET = 15;

	private JTextArea chatTextArea;
	private JTextField textLine;
	private JButton sendButton;
	private JScrollPane textAreaScroller;
	private ClientGui client;
	private DefaultListModel<String> model;
	private Credentials<String, String> credentials;

	public CommunicationPanel() {
	}

	public void createClient() {
		client = new ClientGui("localhost", 10513, new MessageProcessor(chatTextArea));
		credentials = getClientCredentials();
		try {
			client.initClient();
			client.sendMessage(credentials.getUsername());
			client.sendMessage(credentials.getPassword());
			client.sendMessage("gui");
			String userNamesJoined = client.readMessageSynchronously();
			List<String> userNames = parse(userNamesJoined);
			addToDisplayList(userNames);
			client.readMessageAsynchronously();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<String> parse(String userNamesJoined) {
		return Arrays.asList(userNamesJoined.split(","));
	}

	private void addToDisplayList(List<String> usernames) {
		for (String userName : usernames) {
			model.addElement(userName);
		}
	}

	private Credentials<String, String> getClientCredentials() {
		final String[] options = { "SIGN-IN" };
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		final JLabel usernameAreaLabel = new JLabel("Enter your username: ");
		final JLabel passwordAreaLabel = new JLabel("Enter your password: ");
		final JTextField usernameArea = new JTextField(DEFAULT_INPUT_AREA_WIDTH);
		final JPasswordField passwordArea = new JPasswordField(DEFAULT_DISPLAY_LIST_WIDTH);
		panel.add(usernameAreaLabel);
		panel.add(usernameArea);
		panel.add(Box.createRigidArea(
				new Dimension(SIGN_IN_PANEL_COMPONETS_SEPARATOR_WIDTH, SIGN_IN_PANEL_COMPONETS_SEPARATOR_HEIGHT)));
		panel.add(passwordAreaLabel);
		panel.add(passwordArea);
		while (true) {
			final int selectedOption = JOptionPane.showOptionDialog(null, panel, "SIGN-IN", JOptionPane.NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			final String username = usernameArea.getText();
			final String password = passwordArea.getPassword().toString();
			if (selectedOption == OK_BUTTON && isCredentialValid(username) && isCredentialValid(password)
					&& isUsernameAcknowledged(username)) {
				return new Credentials<>(username, password);
			}
			if (selectedOption == JOptionPane.CLOSED_OPTION) {
				System.exit(0);
			}
		}
	}

	private Boolean isUsernameAcknowledged(final String username) {
		return "valid_username".equals(client.readMessageSynchronously());
	}

	private Boolean isCredentialValid(final String credential) {
		return credential != null && !credential.isEmpty() ? true : false;
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

	}

	private ActionListener getActionListener() {
		return (e) -> {
			String message = textLine.getText();
			client.sendMessage(message);
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
