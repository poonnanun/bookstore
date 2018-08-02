import java.awt.EventQueue;
import java.awt.Image;
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

public class ReturnScreen {

	private JFrame returnScreen;
	private static ReturnScreen window;
	private LoanerPane pane1, pane2, pane3;
	private int page = 0;
	private List<LonerGroup> displayLoaner;
	private DataDownloader loader = new DataDownloader();
	private Connection conn = loader.getConnection();
	private boolean isSearch = false;
	private String type = "";
	private String key = "";
	
	public ReturnScreen() {
		initialize();
	}
	
	public static void goToReturnScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new ReturnScreen();
					window.returnScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void initialize() {
		returnScreen = new JFrame();
		returnScreen.setResizable(false);
		returnScreen.setTitle("GJStore");
		returnScreen.setBounds(100, 100, 1280, 768);
		
		displayLoaner = new ArrayList<>();
		displayLoaner.add(new LonerGroup(getDefaultLoaner()));
		
		TextBox idBox = new TextBox(119, 210, 300, 32, "src/Assets/button/searchBox.png", returnScreen); 
		
		Button idSearch = new Button(430, 210, 35, 35, "src/Assets/button/searchIcon.png", returnScreen);
		
		TextBox nameBox = new TextBox(119, 278, 300, 32, "src/Assets/button/searchBox.png", returnScreen); 
		
		Button nameSearch = new Button(430, 278, 35, 35, "src/Assets/button/searchIcon.png", returnScreen);
		
		pane1 = new LoanerPane(612, 44, 570, 172, "src/Assets/button/loanPane1.png", returnScreen);
		pane1.getPane().addActionListener(event ->{
			goToDetail(pane1.getLoaner());
		});
		
		pane2 = new LoanerPane(612, 245, 570, 172, "src/Assets/button/loanPane2.png", returnScreen);
		pane2.getPane().addActionListener(event ->{
			goToDetail(pane2.getLoaner());
		});
		
		pane3 = new LoanerPane(612, 446, 570, 172, "src/Assets/button/loanPane3.png", returnScreen);
		pane3.getPane().addActionListener(event ->{
			goToDetail(pane3.getLoaner());
		});
		
		setDisplay();
		
		Button next = new Button(1150, 670, 102, 40, "src/Assets/button/next.png", returnScreen);
		
		Button previous = new Button(530, 670, 146, 40, "src/Assets/button/previous.png", returnScreen);
		previous.getButton().setVisible(false);
		
		Button back = new Button(50, 650, 90, 40, "src/Assets/button/back.png", returnScreen);
		back.getButton().addActionListener(event ->{
			window.returnScreen.setVisible(false);
			MainScreen.goToMainMenu();
		});
		
		next.getButton().addActionListener(event ->{
			page += 1;
			if(page != 0) {
				previous.setVisible(true);
			}
			if(displayLoaner.size()==page) {
				if(isSearch == false) {
					displayLoaner.add(new LonerGroup(getDefaultLoaner()));
				}
				else {
					displayLoaner.add(new LonerGroup(getSearchLoaner()));
				}
			}
			int haveNext = setDisplay();
			if(haveNext == 0) {
				next.setVisible(true);
			}else if(haveNext == 1){
				next.setVisible(false);
			}else if(haveNext == 2){
				page -= 1;
				displayLoaner.remove(displayLoaner.size()-1);
				setDisplay();
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
			setDisplay();
		});
		
		idSearch.getButton().addActionListener(event ->{
			displayLoaner.clear();
			type = "id";
			isSearch = true;
			page = 0;
			key = idBox.getText();
			displayLoaner.add(new LonerGroup(getSearchLoaner()));
			previous.setVisible(false);
			setDisplay();
		});
		
		nameSearch.getButton().addActionListener(event ->{
			displayLoaner.clear();
			type = "name";
			isSearch = true;
			page = 0;
			key = nameBox.getText();
			displayLoaner.add(new LonerGroup(getSearchLoaner()));
			previous.setVisible(false);
			setDisplay();
		});
		
		Background bg = new Background("src/Assets/background/ReturnScreen.png", returnScreen);
		bg.setVisible(true);
	}
	
	public int setDisplay() {
		LonerGroup display = displayLoaner.get(page);
		int check = 0;
		if(display == null) {
			pane1.setVisible(false);
			pane2.setVisible(false);
			pane3.setVisible(false);
			return 2;
		}
		// check all
		if(display.getLoaner(1) == null) {
			pane1.setVisible(false);
			pane2.setVisible(false);
			pane3.setVisible(false);
			return 2;
		}else {
			pane1.setVisible(true);
			pane1.setNewPane(display.getLoaner(1));
		}
		// finish pane1
		if(display.getLoaner(2) == null) {
			pane2.setVisible(false);
			check = 1;
		}else {
			pane2.setVisible(true);
			pane2.setNewPane(display.getLoaner(2));
		}
		// finish pane2
		if(display.getLoaner(3) == null) {
			pane3.setVisible(false);
			check = 1;
		}else {
			pane3.setVisible(true);
			pane3.setNewPane(display.getLoaner(3));
		}
		// finish pane3
		return check;
	}

	private List<Loaner> getDefaultLoaner() {
		List<Loaner> displayList = new ArrayList<>();
		ResultSet rs;
		try {
			rs = conn.createStatement().executeQuery("SELECT " + "*" + " FROM " + "loandetail" + " limit 3 " + "offset " + (page*3));
			for(int a = 1; a<=3; a++) {
				if(rs.next()) {
					String id = rs.getString("id");
					String name = rs.getString("name");
					String surname = rs.getString("surname");
					String tel = rs.getString("telephone");
					String book = rs.getString("bookId");
					String[] bookId = book.split("/");
					Loaner loaner = new Loaner(id, name, surname, tel);
					for(int c = 0; c < bookId.length; c++) {
						loaner.addBook(loader.getBookbyId(bookId[c]));
					}
					displayList.add(loaner);
				}else {
					displayList.add(null);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return displayList;
	}
	
	public List<Loaner> getSearchLoaner() {
		try {
			List<Loaner> displayList = new ArrayList<>();
			ResultSet rs = loader.getConnection().createStatement().executeQuery("SELECT " + "*" + " FROM " + "loandetail " + 
					"where " + type + " like '%" +key+ "%'" + " limit 3 " + "offset " + (page*3));
			for(int a = 1; a<=3; a++) {
				if(rs.next()) {
					String id = rs.getString("id");
					String name = rs.getString("name");
					String surname = rs.getString("surname");
					String tel = rs.getString("telephone");
					String book = rs.getString("bookId");
					String[] bookId = book.split("/");
					Loaner loaner = new Loaner(id, name, surname, tel);
					for(int c = 0; c < bookId.length; c++) {
						loaner.addBook(loader.getBookbyId(bookId[c]));
					}
					displayList.add(loaner);
				}else {
					displayList.add(null);
				}
			}
			return displayList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void goToDetail(Loaner loaner) {
		window.returnScreen.setVisible(false);
		ReturnDataScreen.goToReturnDataScreen(loaner);
	}
}
