package org.popov.belezirev.gui.builder;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.popov.belezirev.gui.CommunicationPanel;

public class CommunicationPanelBuilder {

    private static final int SIZE_INCREMENTER = 2;

    private CommunicationPanel communicationPanel = new CommunicationPanel();

    public CommunicationPanelBuilder setLayout() {
        communicationPanel.setLayout(new BorderLayout());
        return this;
    }

    public CommunicationPanelBuilder createTextArea() {
        JTextArea chatTextArea = new JTextArea();
        Font oldFont = chatTextArea.getFont();
        Font newFont = new Font(oldFont.getName(), Font.TYPE1_FONT, oldFont.getSize() + SIZE_INCREMENTER);
        chatTextArea.setEditable(false);
        chatTextArea.setFont(newFont);
        communicationPanel.setChatTextArea(chatTextArea);
        return this;
    }

    public CommunicationPanelBuilder createTextLine() {
        JTextField textLine = new JTextField();
        textLine.requestFocus();
        communicationPanel.setTextLine(textLine);
        return this;
    }

    public CommunicationPanelBuilder createSendButton(String textButtonText) {
        JButton sendButton = new JButton(textButtonText);
        sendButton.setFocusable(false);
        communicationPanel.setSendButton(sendButton);
        return this;
    }

    public CommunicationPanelBuilder createAreaScroller() {
        JScrollPane textAreaScroller = new JScrollPane(communicationPanel.getChatTextArea());
        textAreaScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textAreaScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        communicationPanel.setTextAreaScroller(textAreaScroller);
        return this;
    }

    public CommunicationPanel build() {
        communicationPanel.addPanels();
        communicationPanel.createClient();
        communicationPanel.createListener();
        return communicationPanel;
    }
}
