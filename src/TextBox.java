import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TextBox {

	private JLabel bg;
	private JTextField textField;
	
	public TextBox(int x, int y, int length, int height, String source, JFrame frame) {
		textField = new JTextField(9);
		textField.setBounds(x+15, y, length-30, height);
		textField.setFont(new Font("SansSerif", Font.PLAIN, 20));
		textField.setBorder(BorderFactory.createEmptyBorder());
		textField.setOpaque(false);
		bg = new JLabel();
		bg.setIcon(new ImageIcon(source));
		bg.setBounds(x, y, length, height);

		frame.getContentPane().add(textField);
		frame.getContentPane().add(bg);
	}
	
	public void limitCharacter(int number) {
		textField.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	        	if (e.getKeyCode()==KeyEvent.VK_ENTER) {
	        		
	        		
	        	}
	            if (textField.getText().length() >= number )
	                e.consume();
	        	}
	        	
	        	
	    });
	}
	
	public String getText() {
		return textField.getText();
	}
	
	public JTextField getTextField() {
		return this.textField;
	}
}
