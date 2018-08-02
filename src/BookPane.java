import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BookPane {

	private JButton button;
	private JLabel picture;
	private JLabel id;
	private JLabel name;
	private JLabel status;
	private JLabel background;
	private int x;
	private int y;
	private int length;
	private int height;
	private String source;
	private JFrame frame;
	private Book book;
	
	public BookPane(int x, int y, int length, int height, String source, JFrame frame, Book book) {
		// insert valuable
		this.book = book;
		this.x = x;
		this.y = y;
		this.length = length;
		this.height = height;
		this.frame = frame;
		this.source = source;
		
		// init component
		button = new JButton();
		background = new JLabel();
		picture = new JLabel();
		id = new JLabel();
		name = new JLabel();
		status = new JLabel();
		
		frame.getContentPane().add(button);
		frame.getContentPane().add(id);
		frame.getContentPane().add(name);
		frame.getContentPane().add(picture);
		frame.getContentPane().add(status);
		frame.getContentPane().add(background);
		initialize();
	}
	
	public void initialize() {
		
		button.setBounds(x, y, length, height);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setForeground(null);
		
		background.setBounds(x, y, length, height);
		background.setIcon(new ImageIcon(source));
		
		if(book != null) picture.setIcon(new ImageIcon(book.getImage()));
		picture.setBounds(x+40, y+35, 120, 150);
		
		if(book != null) id.setText(book.getId());
		id.setForeground(Color.gray);
		id.setFont(new Font("SansSerif", Font.PLAIN, 15));
		id.setBounds(x+105, y+3, 100, 30);

		if(book != null) {
			String text = book.getName();
			name.setText("<html>"+text+"</html>");	
		}
		
		name.setFont(new Font("SansSerif", Font.PLAIN, 20));
		name.setForeground(Color.white);
		name.setBounds(x+20, y+185, 190, 50);
		
		if(book != null) {
			if(book.getStatus()) {
				status.setIcon(new ImageIcon("src/Assets/button/trueStatus.png"));
			}
			else {
				status.setIcon(new ImageIcon("src/Assets/button/falseStatus.png"));
			}
		}
		status.setBounds(x+100, y+235, 35, 35);
	
	}
	
	public void setNewPane(Book book) {
		this.book = book;
		initialize();
	}
	
	public void setUnClickable(boolean isClickable) {
		button.setEnabled(isClickable);
	}
	
	public void setVisible(boolean isVisible) {
		button.setVisible(isVisible);
		picture.setVisible(isVisible);
		id.setVisible(isVisible);
		status.setVisible(isVisible);
		background.setVisible(isVisible);
	}
	
	public JButton getPane() {
		return button;
	}
	
	public Book getBook() {
		return this.book;
	}
}
