import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class BookListScreen {

	private JFrame bookListScreen;
	private static BookListScreen window;
	private List<Shelf> displayBook;
	private BookPane pane1, pane2, pane3, pane4;
	private int page = 0;
	private boolean isSearch = false;
	private String type = "";
	private String key = "";
	private DataDownloader loader = new DataDownloader();
	
	public BookListScreen() {
		initialize();
	}
	
	public static void goToBookListScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new BookListScreen();
					window.bookListScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void initialize() {
		bookListScreen = new JFrame();
		bookListScreen.setResizable(false);
		bookListScreen.setTitle("GJStore");
		bookListScreen.setBounds(100, 100, 1280, 768);
		
		displayBook = new ArrayList<>();
		displayBook.add(new Shelf(getDefaultBook()));
		
		Background blur = new Background("src/Assets/background/Blur.png", bookListScreen);
		blur.setVisible(false);
		
		TextBox idBox = new TextBox(119, 210, 300, 32, "src/Assets/button/searchBox.png", bookListScreen); 
		
		Button idSearch = new Button(430, 210, 35, 35, "src/Assets/button/searchIcon.png", bookListScreen);
		
		pane1 = new BookPane(640, 45, 200, 281, "src/Assets/button/pane2.png", bookListScreen, null);
		
		pane2 = new BookPane(930, 45, 200, 281, "src/Assets/button/pane1.png", bookListScreen, null);
		
		pane3 = new BookPane(640, 360, 200, 281, "src/Assets/button/pane3.png", bookListScreen, null);
		
		pane4 = new BookPane(930, 360, 200, 281, "src/Assets/button/pane4.png", bookListScreen, null);
		setDisplayBook();
		
		Button back = new Button(50, 650, 90, 40, "src/Assets/button/back.png", bookListScreen);
		back.getButton().addActionListener(event ->{
			window.bookListScreen.setVisible(false);
			MainScreen.goToMainMenu();
		});
		
		Button add = new Button(172, 280, 167, 45, "src/Assets/button/add.png", bookListScreen);
		add.getButton().addActionListener(event ->{
			blur.setVisible(true);
			pane1.setUnClickable(false);
			pane2.setUnClickable(false);
			pane3.setUnClickable(false);
			pane4.setUnClickable(false);
			idBox.getTextField().setEditable(false);
			idSearch.getButton().setEnabled(false);
			back.getButton().setEnabled(false);
			AddBookPopup.goToAddBookPopup();
		});
		
		Button next = new Button(1150, 670, 102, 40, "src/Assets/button/next.png", bookListScreen);
		
		Button previous = new Button(530, 670, 146, 40, "src/Assets/button/previous.png", bookListScreen);
		previous.getButton().setVisible(false);
		
		Background bookListScreenBackground = new Background("src/Assets/background/BookListScreen.png", bookListScreen);
		bookListScreenBackground.setVisible(true);
		
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
			key = idBox.getText();
			displayBook.add(new Shelf(getSearchBook()));
			previous.setVisible(false);
			setDisplayBook();
		});
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
	
	
	
	public static void closeScreen() {
		window.bookListScreen.setVisible(false);
	}
}
