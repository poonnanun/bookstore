import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Button {

	private JButton button;
	
	public Button(int x, int y, int length, int height, String source, JFrame frame) {
		button = new JButton("");
		button.setBounds(x, y, length, height);
		button.setIcon(new ImageIcon(source));
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setForeground(null);
		frame.getContentPane().add(button);
	}
	
	public Button(int x, int y, int length, int height, JFrame frame) {
		button = new JButton("");
		button.setBounds(x, y, length, height);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setForeground(null);
		frame.getContentPane().add(button);
	}
	
	public void setImage(Image image) {
		button.setIcon(new ImageIcon(image));
	}
	
	public JButton getButton() {
		return button;
	}
	
	public void setVisible(boolean visible) {
		button.setVisible(visible);
	}
}
