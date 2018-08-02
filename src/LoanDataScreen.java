import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LoanDataScreen {

	private JFrame loanDataScreen;
	private static LoanDataScreen window;
	private Book book;
	private DataDownloader loader = new DataDownloader();
	private Connection conn = loader.getConnection();
	
	public LoanDataScreen(Book book) {
		this.book = book;
		initialize();
	}
	
	public static void goToLoanDataScreen(Book book) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new LoanDataScreen(book);
					window.loanDataScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initialize() {
		loanDataScreen = new JFrame();
		loanDataScreen.setResizable(false);
		loanDataScreen.setTitle("GJStore");
		loanDataScreen.setBounds(100, 100, 1280, 768);
		
		JLabel outWarn = new JLabel();
		outWarn.setIcon(new ImageIcon("src/Assets/button/Stockout.png"));
		outWarn.setBounds(520, 264, 240, 240);
		outWarn.setVisible(false);
		loanDataScreen.getContentPane().add(outWarn);
		
		Background blur = new Background("src/Assets/background/Blur.png", loanDataScreen);
		blur.setVisible(false);
		blur.getLabel().addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	window.loanDataScreen.setVisible(false);
				LoanScreen.goToLoanScreen();
		    }  
		}); 
		
		// book part
		JLabel bookID = new JLabel(book.getId());
		bookID.setBounds(350, 3, 100, 30);
		bookID.setForeground(Color.DARK_GRAY);
		bookID.setFont(new Font("SansSerif", Font.PLAIN, 20));
		
		JLabel bookImage = new JLabel();
		Image displayImage = book.getRealImage().getScaledInstance(250, 310, Image.SCALE_DEFAULT);
		bookImage.setIcon(new ImageIcon(displayImage));
		bookImage.setBounds(120, 50, 250, 310);
		
		JLabel bookName = new JLabel();
		bookName.setText("<html>"+ book.getName() +"</html>");	
		bookName.setBounds(50, 320, 400, 200);
		bookName.setForeground(Color.white);
		bookName.setFont(new Font("SansSerif", Font.PLAIN, 25));
		
		JLabel bookWriter = new JLabel();
		bookWriter.setText(book.getWriter());	
		bookWriter.setBounds(200, 500, 300, 50);
		bookWriter.setForeground(Color.white);
		bookWriter.setFont(new Font("SansSerif", Font.PLAIN, 25));
	
		JLabel bookStatus = new JLabel();
		if(book.getStatus()) {
			bookStatus.setIcon(new ImageIcon("src/Assets/button/trueStatus.png"));
		}
		else {
			bookStatus.setIcon(new ImageIcon("src/Assets/button/falseStatus.png"));
		}
		bookStatus.setBounds(190, 547, 35, 35);
		loanDataScreen.getContentPane().add(bookID);
		loanDataScreen.getContentPane().add(bookImage);
		loanDataScreen.getContentPane().add(bookName);
		loanDataScreen.getContentPane().add(bookStatus);
		loanDataScreen.getContentPane().add(bookWriter);
		
		// loaner part
		Button back = new Button(50, 650, 90, 40, "src/Assets/button/back.png", loanDataScreen);
		back.getButton().addActionListener(event ->{
			window.loanDataScreen.setVisible(false);
			LoanScreen.goToLoanScreen();
		});
		
		TextBox id = new TextBox(825, 190, 354, 38, "src/Assets/button/idBox.png", loanDataScreen);
		
		TextBox name = new TextBox(825, 245, 354, 38, "src/Assets/button/nameBox.png", loanDataScreen);
		
		TextBox surname = new TextBox(825, 300, 354, 38, "src/Assets/button/lastNameBox.png", loanDataScreen);
		
		TextBox tel = new TextBox(825, 355, 354, 38, "src/Assets/button/telBox.png", loanDataScreen);
		
		Button confirm = new Button(800, 450, 167, 45, "src/Assets/button/confirm.png", loanDataScreen);
				
		confirm.getButton().addActionListener(event ->{
			if(book.getStatus()) {
				try {
					conn.prepareStatement("update bookdetail set status=0 where id='"+book.getId()+"';").execute();
					ResultSet rs = conn.createStatement().executeQuery("select * from loandetail where id='"+id.getText()+"';");
					if(rs.next() == false) {
						PreparedStatement ps = conn.prepareStatement("insert into loandetail(id,name,surname,telephone,bookId) values(?,?,?,?,?)");
						ps.setString(1, id.getText());
						ps.setString(2, name.getText());
						ps.setString(3, surname.getText());
						ps.setString(4, tel.getText());
						ps.setString(5, bookID.getText());
						ps.execute();
					}
					else {
						String loanedBook = rs.getString("bookId");
						loanedBook += "/"+bookID.getText();
						conn.prepareStatement("update loandetail set bookId='"+loanedBook+"' where id='"+id.getText()+"'").execute();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				window.loanDataScreen.setVisible(false);
				LoanScreen.goToLoanScreen();
			}
			else {
				blur.setVisible(true);
				outWarn.setVisible(true);
				
			}
		});
		
		Background loanDataScreenBackground = new Background("src/Assets/background/LoanDataScreen.png", loanDataScreen);
		loanDataScreenBackground.setVisible(true);
	}
	
	public void setDisplayBook(Book book) {
		this.book = book;
	}
}
