import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ReturnDataScreen {

	private JFrame returnDataScreen;
	private static ReturnDataScreen window;
	private Loaner loaner;
	private BookPane pane1, pane2, pane3, pane4;
	private Background blur;
	private int page = 0;
	private List<Shelf> displayBook;
	private DataDownloader loader = new DataDownloader();
	private List<Book> loanedBook;
	private int count = 0;
	private Button next, previous, back, yes, no;
	private JLabel popup;
	
	public ReturnDataScreen(Loaner loaner) {
		this.loaner = loaner;
		initialize();
	}
	
	public static void goToReturnDataScreen(Loaner loaner) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new ReturnDataScreen(loaner);
					window.returnDataScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void initialize() {
		returnDataScreen = new JFrame();
		returnDataScreen.setResizable(false);
		returnDataScreen.setTitle("GJStore");
		returnDataScreen.setBounds(100, 100, 1280, 768);
	
		loanedBook = loaner.getBook();
		displayBook = new ArrayList<>();
		displayBook.add(new Shelf(getSearchBook()));
		
		yes = new Button(470, 430, 130, 45, "src/Assets/button/yes.png", returnDataScreen);
		yes.setVisible(false);
		no = new Button(689, 430, 130, 45, "src/Assets/button/no.png", returnDataScreen);
		no.setVisible(false);
		
		popup = new JLabel();
		popup.setBounds(390, 234, 500, 300);
		popup.setVisible(false);
		popup.setIcon(new ImageIcon("src/Assets/button/returnPopup.png"));
		returnDataScreen.getContentPane().add(popup);
		
		blur = new Background("src/Assets/background/Blur.png", returnDataScreen);
		blur.setVisible(false);
		
		JLabel ID = new JLabel(loaner.getId());
		ID.setBounds(240, 80, 300, 50);
		ID.setForeground(Color.white);
		ID.setFont(new Font("SansSerif", Font.PLAIN, 30));
		
		JLabel name = new JLabel(loaner.getName());
		name.setBounds(250, 130, 300, 50);
		name.setForeground(Color.white);
		name.setFont(new Font("SansSerif", Font.PLAIN, 30));
		
		JLabel surname = new JLabel(loaner.getSurname());
		surname.setBounds(220, 190, 300, 50);
		surname.setForeground(Color.white);
		surname.setFont(new Font("SansSerif", Font.PLAIN, 30));
		
		JLabel tel = new JLabel(loaner.getTel());
		tel.setBounds(250, 245, 300, 50);
		tel.setForeground(Color.white);
		tel.setFont(new Font("SansSerif", Font.PLAIN, 30));
		
		
		returnDataScreen.getContentPane().add(ID);
		returnDataScreen.getContentPane().add(name);
		returnDataScreen.getContentPane().add(surname);
		returnDataScreen.getContentPane().add(tel);
		
		pane1 = new BookPane(640, 45, 200, 281, "src/Assets/button/pane2.png", returnDataScreen, null);
		pane1.getPane().addActionListener(event ->{
			confirmReturn(pane1.getBook());
		});
		
		pane2 = new BookPane(930, 45, 200, 281, "src/Assets/button/pane1.png", returnDataScreen, null);
		pane2.getPane().addActionListener(event ->{
			confirmReturn(pane2.getBook());
		});
		
		pane3 = new BookPane(640, 360, 200, 281, "src/Assets/button/pane3.png", returnDataScreen, null);
		pane3.getPane().addActionListener(event ->{
			confirmReturn(pane3.getBook());
		});
		
		pane4 = new BookPane(930, 360, 200, 281, "src/Assets/button/pane4.png", returnDataScreen, null);
		pane4.getPane().addActionListener(event ->{
			confirmReturn(pane4.getBook());
		});
		
		setDisplayBook();

		next = new Button(1150, 670, 102, 40, "src/Assets/button/next.png", returnDataScreen);
		
		previous = new Button(530, 670, 146, 40, "src/Assets/button/previous.png", returnDataScreen);
		previous.getButton().setVisible(false);
		
		back = new Button(50, 650, 90, 40, "src/Assets/button/back.png", returnDataScreen);
		back.getButton().addActionListener(event ->{
			window.returnDataScreen.setVisible(false);
			ReturnScreen.goToReturnScreen();
		});
		
		yes.getButton().addActionListener(event ->{
			
		});
		
		next.getButton().addActionListener(event ->{
			page += 1;
			if(page != 0) {
				previous.setVisible(true);
			}
			if(displayBook.size()==page) {
					displayBook.add(new Shelf(getSearchBook()));
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
		Background returnDataScreenBackground = new Background("src/Assets/background/ReturnDataScreen.png", returnDataScreen);
		
		
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
	
	public void confirmReturn(Book book) {
		blur.setVisible(true);
		popup.setVisible(true);
		yes.setVisible(true);
		no.setVisible(true);
		//
		next.getButton().setEnabled(false);
		previous.getButton().setEnabled(false);
		back.getButton().setEnabled(false);
		pane1.setUnClickable(false);
		pane2.setUnClickable(false);
		pane3.setUnClickable(false);
		pane4.setUnClickable(false);
		//
		yes.getButton().addActionListener(event ->{
			try {
				for(int i = 0; i<loaner.getBook().size(); i++) {
					if(loaner.getBook().get(i).getId().equals(book.getId())) {
						loaner.getBook().remove(i);
						break;
					}
				}
				System.out.println(loaner.getBook().size());
				loader.getConnection().prepareStatement("update bookdetail set status=1 where id='"+book.getId()+"';").execute();
				if(loaner.getBook().size() == 0) {
					System.out.println(1);
					loader.getConnection().prepareStatement("delete from loandetail where id='"+loaner.getId()+"';").execute();
				}else if(loaner.getBook().size() == 1){
					loader.getConnection().prepareStatement("update loandetail set bookId='"+loaner.getBook().get(0).getId()+"' where id='"+loaner.getId()+"';").execute();
				}else {
					String bookId = loaner.getBook().get(0).getId();
					for(int a = 1 ; a<loaner.getBook().size() ; a++) {
						bookId += "/"+loaner.getBook().get(a).getId();
					}
					loader.getConnection().prepareStatement("update loandetail set bookId='"+bookId+"' where id='"+loaner.getId()+"';").execute();
				}
				window.returnDataScreen.setVisible(false);
				ReturnScreen.goToReturnScreen();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		no.getButton().addActionListener(event ->{
			blur.setVisible(false);
			popup.setVisible(false);
			yes.setVisible(false);
			no.setVisible(false);
			//
			next.getButton().setEnabled(true);
			previous.getButton().setEnabled(true);
			back.getButton().setEnabled(true);
			pane1.setUnClickable(true);
			pane2.setUnClickable(true);
			pane3.setUnClickable(true);
			pane4.setUnClickable(true);
		});
	}
	
	public List<Book> getSearchBook() {
		try {
			List<Book> bookList = new ArrayList<>();
			int check = 0;
			ResultSet rs = null;
			for(int a = count; a<=count+4; a++) {
				if(loanedBook.size() > a) {
					rs = loader.getConnection().createStatement().executeQuery("SELECT " + "*" + " FROM " + "bookdetail " + 
							"where " + "id='" + loanedBook.get(a).getId() + "';");
				}else {
					check = 1;
				}
				if(rs.next() && check == 0) {
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
