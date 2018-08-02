import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Background {

	private JLabel background;
	
	public Background(String url, JFrame frame) {
		background = new JLabel("");
		background.setIcon(new ImageIcon(url));
		background.setBounds(0, 0, 1280, 768);
		frame.getContentPane().add(background);
	}
	
	public void setVisible(boolean visible) {
		background.setVisible(visible);
	}
	
	public JLabel getLabel() {
		return this.background;
	}
}
