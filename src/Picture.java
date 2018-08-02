import javax.swing.JFrame;
import javax.swing.JLabel;

public class Picture {

	private JLabel picture;
	
	public Picture(int x, int y, int length, int height, JFrame frame) {
		picture = new JLabel();
		picture.setBounds(x, y, length, height);
		frame.getContentPane().add(picture);
	}
	
	public JLabel getPicture() {
		return this.picture;
	}
}
