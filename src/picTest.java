//import java.awt.EventQueue;
//import java.awt.Image;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.sql.Blob;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//
//public class picTest {
//
//	private JFrame frame;
//	private static picTest window;
//	
//	public picTest() throws SQLException, IOException {
//		initialize();
//	}
//	
//	public static void goToReturnScreen() {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					window = new picTest();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//	
//	public void initialize() throws SQLException, IOException {
//		frame = new JFrame();
//		frame.setResizable(false);
//		frame.setTitle("GJStore");
//		frame.setBounds(100, 100, 1280, 768);
//		
//		DataDownloader d = new DataDownloader();
//		
//		d.connect();
//		Connection connection = d.getConnection();
//		
//		ResultSet rs = connection.createStatement().executeQuery("SELECT " + "*" + " FROM " + "bookdetail" +" WHERE "+"id"+" =  123456");
//		rs.next();
//		Blob blob = rs.getBlob("Image");
//		byte[] b = blob.getBytes(1, (int)blob.length());
//		ByteArrayInputStream bis = new ByteArrayInputStream(b);
//		BufferedImage image = ImageIO.read(bis);
//		Image newImage = image.getScaledInstance(500, 500, Image.SCALE_DEFAULT);
//		Picture test = new Picture(100, 100, 500, 500, frame);
//		test.getPicture().setIcon(new ImageIcon(newImage));
//		
//	}
//	
//	public static void main(String[] args) {
//		goToReturnScreen();
//	}
//}
