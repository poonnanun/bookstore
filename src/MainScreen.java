import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class MainScreen {

	
	private JFrame mainMenu;
	private static MainScreen window;
	
	public MainScreen() {
		initialize();
	}
	
	public static void main(String[] args){
		goToMainMenu();
	}
	
	public static void goToMainMenu() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainScreen();
					window.mainMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void initialize() {
		mainMenu = new JFrame();
		mainMenu.setResizable(false);
		mainMenu.setTitle("GJStore");
		mainMenu.setBounds(100, 100, 1280, 768);
		
		
		Button loanButton = new Button(570, 260, 200, 50, "src/Assets/button/Loan.png", mainMenu);
		loanButton.getButton().addActionListener(event ->{
			window.mainMenu.setVisible(false);
			LoanScreen.goToLoanScreen();
		});
		
		Button returnButton = new Button(570, 340, 200, 50, "src/Assets/button/return.png", mainMenu);
		returnButton.getButton().addActionListener(event ->{
			window.mainMenu.setVisible(false);
			ReturnScreen.goToReturnScreen();
		});
		
		Button listButton = new Button(570, 420, 200, 50, "src/Assets/button/bookList.png", mainMenu);
		listButton.getButton().addActionListener(event ->{
			window.mainMenu.setVisible(false);
			BookListScreen.goToBookListScreen();
		});
		
		Button exit = new Button(1050, 650, 200, 50, "src/Assets/button/exit.png", mainMenu);
		exit.getButton().addActionListener(event ->{
			System.exit(1);
		});
		
		Background mainMenuBackground = new Background("src/Assets/background/MainMenu.png", mainMenu);
		mainMenuBackground.setVisible(true);
	}
}
