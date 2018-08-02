import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class LoanScreen {
	
	private JFrame loanScreen;
	private static LoanScreen window;
	private BookPane pane1, pane2, pane3, pane4;
	private int page = 0;
	private List<Shelf> displayBook;
	private DataDownloader loader = new DataDownloader();
	private boolean isSearch = false;
	private String type = "";
	private String key = "";
	
	public LoanScreen() throws SQLException, IOException{
		initialize();
	}
	
	public static void goToLoanScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new LoanScreen();
					window.loanScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void initialize() throws SQLException, IOException{
		loanScreen = new JFrame();
		loanScreen.setResizable(false);
		loanScreen.setTitle("GJStore");
		loanScreen.setBounds(100, 100, 1280, 768);
		
		displayBook = new ArrayList<>();
		displayBook.add(new Shelf(getDefaultBook()));
		
		TextBox idFill = new TextBox(95, 260, 300, 32, "src/Assets/button/searchBox.png", loanScreen);
		idFill.limitCharacter(9);
		
		Button idSearch = new Button(405, 260, 35, 35, "src/Assets/button/searchIcon.png", loanScreen);
		
		
		TextBox nameFill = new TextBox(95, 360, 300, 32, "src/Assets/button/searchBox.png", loanScreen);
		
		Button nameSearch = new Button(405, 360, 35, 35, "src/Assets/button/searchIcon.png", loanScreen);
		
		TextBox writerFill = new TextBox(95, 460, 300, 32, "src/Assets/button/searchBox.png", loanScreen);
		
		Button writerSearch = new Button(405, 460, 35, 35, "src/Assets/button/searchIcon.png", loanScreen);
		
		Button back = new Button(50, 650, 90, 40, "src/Assets/button/back.png", loanScreen);
		back.getButton().addActionListener(event ->{
			window.loanScreen.setVisible(false);
			MainScreen.goToMainMenu();
		});
		
		pane1 = new BookPane(640, 45, 200, 281, "src/Assets/button/pane2.png", loanScreen, null);
		pane1.getPane().addActionListener(event ->{
			goToDetail(pane1.getBook());
		});
		
		pane2 = new BookPane(930, 45, 200, 281, "src/Assets/button/pane1.png", loanScreen, null);
		pane2.getPane().addActionListener(event ->{
			goToDetail(pane2.getBook());
		});
		
		pane3 = new BookPane(640, 360, 200, 281, "src/Assets/button/pane3.png", loanScreen, null);
		pane3.getPane().addActionListener(event ->{
			goToDetail(pane3.getBook());
		});
		
		pane4 = new BookPane(930, 360, 200, 281, "src/Assets/button/pane4.png", loanScreen, null);
		pane4.getPane().addActionListener(event ->{
			goToDetail(pane4.getBook());
		});
		
		setDisplayBook();

		Button next = new Button(1150, 670, 102, 40, "src/Assets/button/next.png", loanScreen);
		
		Button previous = new Button(530, 670, 146, 40, "src/Assets/button/previous.png", loanScreen);
		previous.getButton().setVisible(false);
		
		Background loanScreenBackground = new Background("src/Assets/background/LoanScreen.png", loanScreen);
		loanScreenBackground.setVisible(true);
		
		next.getButton().addActionListener(event ->{
			page += 1;
			if(page != 0) {
				previous.setVisible(true);
			}
			if(displayBook.size()==page) {
				if(isSearch == false) {
					displayBook.add(new Shelf(getDefaultBook()));
				}
				else {
					displayBook.add(new Shelf(getSearchBook()));
				}
			}
			int haveNext = setDisplayBook();
			if(haveNext == 0) {
				next.setVisible(true);
			}else if(haveNext == 1){
				next.setVisible(false);
			}else if(haveNext == 2){
				page -= 1;
				displayBook.remove(displayBook.size()-1);
				setDisplayBook();
				next.setVisible(false);
			}
		});
		
		previous.getButton().addActionListener(event-> {
			page -= 1;
			next.setVisible(true);
			if(page != 0) {
				previous.setVisible(true);
			}else {
				previous.setVisible(false);
			}
			setDisplayBook();
		});
		
		idSearch.getButton().addActionListener(event ->{
			displayBook.clear();
			type = "id";
			isSearch = true;
			page = 0;
			key = idFill.getText();
			displayBook.add(new Shelf(getSearchBook()));
			previous.setVisible(false);
			setDisplayBook();
		});
		nameSearch.getButton().addActionListener(event ->{
			displayBook.clear();
			type = "name";
			isSearch = true;
			page = 0;
			key = nameFill.getText();
			displayBook.add(new Shelf(getSearchBook()));
			previous.setVisible(false);
			setDisplayBook();
		});
		writerSearch.getButton().addActionListener(event ->{
			displayBook.clear();
			type = "writer";
			isSearch = true;
			page = 0;
			key = writerFill.getText();
			displayBook.add(new Shelf(getSearchBook()));
			previous.setVisible(false);
			setDisplayBook();
		});
	}
	
	public void goToDetail(Book book) {
		window.loanScreen.setVisible(false);
		LoanDataScreen.goToLoanDataScreen(book);
	}
	
	public int setDisplayBook() {
		Shelf display = displayBook.get(page);
		int check = 0;
		if(display == null) {
			pane1.setVisible(false);
			pane2.setVisible(false);
			pane3.setVisible(false);
			pane4.setVisible(false);
			return 2;
		}
		// check all
		if(display.getBook(1) == null) {
			pane1.setVisible(false);
			pane2.setVisible(false);
			pane3.setVisible(false);
			pane4.setVisible(false);
			return 2;
		}else {
			pane1.setVisible(true);
			pane1.setNewPane(display.getBook(1));
		}
		// finish pane1
		if(display.getBook(2) == null) {
			pane2.setVisible(false);
			check = 1;
		}else {
			pane2.setVisible(true);
			pane2.setNewPane(display.getBook(2));
		}
		// finish pane2
		if(display.getBook(3) == null) {
			pane3.setVisible(false);
			check = 1;
		}else {
			pane3.setVisible(true);
			pane3.setNewPane(display.getBook(3));
		}
		// finish pane3
		if(display.getBook(4) == null) {
			pane4.setVisible(false);
			check = 1;
		}else {
			pane4.setVisible(true);
			pane4.setNewPane(display.getBook(4));
		}
		// finish pane4
		return check;
	}
	
	public List<Book> getDefaultBook() {
		try {
			return loader.getBookDefault(page);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Book> getSearchBook() {
		try {
			List<Book> bookList = new ArrayList<>();
			ResultSet rs = loader.getConnection().createStatement().executeQuery("SELECT " + "*" + " FROM " + "bookdetail " + 
					"where " + type + " like '%" +key+ "%'" + " limit 4 " + "offset " + (page*4));
			for(int a = 1; a<=4; a++) {
				if(rs.next()) {
					
					
					String id = rs.getString("id");
					String name = rs.getString("name");
					String writer = rs.getString("writer");
					int status = rs.getInt("status");
					boolean boolStatus = true;
					if(status == 1) {
						boolStatus = true;
					}else {
						boolStatus = false;
					}
					Blob blob = rs.getBlob("Image");
					byte[] b = blob.getBytes(1, (int)blob.length());
					ByteArrayInputStream bis = new ByteArrayInputStream(b);
					BufferedImage image = ImageIO.read(bis);
					Image newImage = image.getScaledInstance(120, 150, Image.SCALE_DEFAULT);
					
					Book book = new Book(id, name, writer, boolStatus, newImage, image);
					bookList.add(book);
				}else {
					bookList.add(null);
				}
			}
			return bookList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
