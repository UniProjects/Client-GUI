package org.popov.belezirev.gui.client;

import javax.swing.JTextArea;

public class MessageProcessor {
	private JTextArea chatTextArea;

	public MessageProcessor(JTextArea chatTextArea) {
		this.chatTextArea = chatTextArea;
	}

	public void processMessage(String readMessage) {
		if (readMessage != null && !readMessage.isEmpty()) {
			chatTextArea.append(readMessage + System.lineSeparator());
		}
	}

}
