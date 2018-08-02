import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LoanerPane {

	private JLabel pane;
	private JLabel id;
	private JLabel name;
	private JLabel surname;
	private JLabel tel;
	private JButton button;
	private Loaner loaner;
	
	public LoanerPane(int x, int y, int length, int height, String source, JFrame frame) {
		
		pane = new JLabel();
		pane.setIcon(new ImageIcon(source));
		pane.setBounds(x, y, length, height);
		
		id = new JLabel();
		id.setBounds(x+168, y+20, 300, 30);
		
		name = new JLabel();
		name.setBounds(x+175, y+52, 300, 30);
		
		surname = new JLabel();
		surname.setBounds(x+150, y+85, 300, 30);
		
		tel = new JLabel();
		tel.setBounds(x+175, y+120, 300, 30);
		
		button = new JButton();
		button.setBounds(x, y, length, height);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setForeground(null);
		
		frame.getContentPane().add(button);
		frame.getContentPane().add(tel);
		frame.getContentPane().add(surname);
		frame.getContentPane().add(name);
		frame.getContentPane().add(id);
		frame.getContentPane().add(pane);
	}
	
	public void initialize() {
		id.setText(loaner.getId());
		name.setText(loaner.getName());
		surname.setText(loaner.getSurname());
		tel.setText(loaner.getTel());
		
		id.setFont(new Font("SansSerif", Font.PLAIN, 25));
		name.setFont(new Font("SansSerif", Font.PLAIN, 25));
		surname.setFont(new Font("SansSerif", Font.PLAIN, 25));
		tel.setFont(new Font("SansSerif", Font.PLAIN, 25));
		id.setForeground(Color.white);
		name.setForeground(Color.white);
		surname.setForeground(Color.white);
		tel.setForeground(Color.white);
	}
	
	public JButton getPane() {
		return this.button;
	}
	
	public void setVisible(boolean isVisible) {
		button.setVisible(isVisible);
		pane.setVisible(isVisible);
		id.setVisible(isVisible);
		name.setVisible(isVisible);
		surname.setVisible(isVisible);
		tel.setVisible(isVisible);

	}
	
	public void setNewPane(Loaner loaner) {
		this.loaner = loaner;
		initialize();
	}
	
	public Loaner getLoaner() {
		return this.loaner;
	}
}
