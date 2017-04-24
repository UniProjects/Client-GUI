package org.popov.belezirev.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CommunicationPanel extends JPanel {
	private JTextArea log;
	private JTextField commandLine;
	private JButton sendButton;
	private SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy.MM.dd : HH:mm");
	private String message;

	public CommunicationPanel() {
		setLayout(new BorderLayout());
		log = new JTextArea();
		commandLine = new JTextField();
		sendButton = new JButton("Send");

		JScrollPane logScroller = new JScrollPane(log);
		logScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		logScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		add(logScroller, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.add(commandLine, BorderLayout.CENTER);
		southPanel.add(sendButton, BorderLayout.EAST);
		add(southPanel, BorderLayout.SOUTH);

		SendActionListener sendActionListener = new SendActionListener();
		sendButton.addActionListener(sendActionListener);
		commandLine.addActionListener(sendActionListener);

		Font oldFont = log.getFont();
		Font newFont = new Font(oldFont.getName(), Font.PLAIN, oldFont.getSize() + 2);
		log.setEditable(false);
		log.setFont(newFont);
	}

	private class SendActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Calendar calendar = Calendar.getInstance();
			String currentTime = dataFormat.format(calendar.getTime());
			log.append(currentTime + " | " + message + System.lineSeparator());
			commandLine.setText("");
		}
	}

	public synchronized void display(String msg) {

	}

	public void setFocus() {
		commandLine.requestFocus();
	}
}
