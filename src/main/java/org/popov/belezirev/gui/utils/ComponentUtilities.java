package org.popov.belezirev.gui.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public final class ComponentUtilities {
	private ComponentUtilities() {
		// Utility class
	}

	public static void setDefaultLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public static void centerComponenetPosition(Component component) {
		Dimension windowSizeDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerWindowXAxis = (int) (windowSizeDimension.getWidth() / 2 - component.getWidth() / 2);
		int centerWindowYAxis = (int) (windowSizeDimension.getHeight() / 2 - component.getHeight() / 2);
		component.setLocation(centerWindowXAxis, centerWindowYAxis);
	}
}
