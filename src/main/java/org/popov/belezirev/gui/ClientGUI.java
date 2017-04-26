package org.popov.belezirev.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientGUI {
	private static final int HEIGHT = 450;
	private static final int WIDTH = 750;
	private static JFrame clientFrame;
	private static JTabbedPane tabs;
	private static CommunicationPanel communicationPanel;
	private static SettingsPanel settingsPanel;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createFrame();
				clientFrame.setVisible(true);
				communicationPanel.setFocus();
			}
		});
	}

	private static void createFrame() {
		setDefaultLookAndFeel();
		clientFrame = new JFrame("Client");
		clientFrame.setSize(WIDTH, HEIGHT);
		centerFramePosition();
		clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createTabs();
	}

	private static void createTabs() {
		tabs = new JTabbedPane();
		tabs.setFocusable(false);
		communicationPanel = new CommunicationPanel();
		settingsPanel = new SettingsPanel();
		tabs.addTab("Communication", communicationPanel);
		tabs.addTab("Settings", settingsPanel);
		clientFrame.add(tabs);
	}

	private static void setDefaultLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private static void centerFramePosition() {
		Dimension windowSizeDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerWindowXAxis = (int) (windowSizeDimension.getWidth() / 2 - clientFrame.getWidth() / 2);
		int centerWindowYAxis = (int) (windowSizeDimension.getHeight() / 2 - clientFrame.getHeight() / 2);
		clientFrame.setLocation(centerWindowXAxis, centerWindowYAxis);
	}
}
