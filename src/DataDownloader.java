import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class DataDownloader {

	private final String USERNAME = "root";
	private final String PASSWORD = "faflukfon";
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://35.197.130.242:3306/kubook?useSSL=false";
	private ResultSet rs;
	private Connection connection;
	private boolean isNext = true;
	
	public DataDownloader()	{
		connect();
	}
	
	public void connect() {
		 try {
	            Class.forName(JDBC_DRIVER);
	            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	        } catch (SQLException e) {
	            System.err.println(e.getMessage());
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	}
	
//	public List<Book> getBookFromId() throws SQLException {
//		List<Book> displayList = new ArrayList<>();
//		ResultSet rs = connection.createStatement().executeQuery("SELECT " + "*" + " FROM " + "bookdetail" );
//		while(rs.next()) {
//			
//		}
//		return displayList;
//	}
	public Book getBookbyId(String id) {
		try {
			ResultSet rs = connection.createStatement().executeQuery("SELECT " + "*" + " FROM " + "bookdetail" + " where " + "id='"+id+"';");
			if(rs.next()) {
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
				return book;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Book> getBookDefault(int page) throws SQLException, IOException {
		List<Book> displayList = new ArrayList<>();
		rs = connection.createStatement().executeQuery("SELECT " + "*" + " FROM " + "bookdetail" + " limit 4 " + "offset " + (page*4));
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
				displayList.add(book);
			}else {
				displayList.add(null);
				isNext = false;
			}
		}
		return displayList;
	}
	
	
	public boolean isNext() {
		return isNext;
	}
	
	public Connection getConnection() {
		return connection;
	}
}
