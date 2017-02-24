package utility;

import java.awt.Frame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/*
 * @author Christopher Lu
 * Implements static method that allows programmers to display error dialogue box with custom errorMessage as a string.
 */

public class ErrorBox {

	public static void displayError(String errorMessage) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Frame frame = new Frame();
				frame.toFront();
				JOptionPane.showMessageDialog(frame, errorMessage);
			}
		});
	}
	
}