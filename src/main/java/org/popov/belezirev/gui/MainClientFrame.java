package org.popov.belezirev.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.popov.belezirev.gui.builder.CommunicationPanelBuilder;
import org.popov.belezirev.gui.utils.ComponentUtilities;

public class MainClientFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int HEIGHT = 450;
	private static final int WIDTH = 750;
	private static final boolean VISIBLE = true;
	private static final String FRAME_TITLE = "CHAT";
	private JTabbedPane tabs;
	private CommunicationPanel communicationPanel;
	private SettingsPanel settingsPanel;

	public MainClientFrame() {
		createFrame();
		setVisible(VISIBLE);
	}

	private void createFrame() {
		setTitle(FRAME_TITLE);
		setSize(WIDTH, HEIGHT);
		ComponentUtilities.setDefaultLookAndFeel();
		ComponentUtilities.centerComponenetPosition(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createTabs();
	}

	private void createTabs() {
		tabs = new JTabbedPane();
		tabs.setFocusable(false);
		communicationPanel = new CommunicationPanelBuilder().setLayout().createTextArea().createTextLine()
				.createSendButton("Send").createAreaScroller().build();
		settingsPanel = new SettingsPanel();
		tabs.addTab("Main Lobby", communicationPanel);
		tabs.addTab("Settings", settingsPanel);
		add(tabs);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				JFrame clientMainFrame = new MainClientFrame();
			}
		});
	}
}
