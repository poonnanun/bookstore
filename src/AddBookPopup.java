import java.awt.EventQueue;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddBookPopup {

	private JFrame addBookPopup;
	private static AddBookPopup window;
	private String path;
	
	public AddBookPopup() {
		initialize();
	}
	
	public static void goToAddBookPopup() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new AddBookPopup();
					window.addBookPopup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void initialize() {
		addBookPopup = new JFrame();
		addBookPopup.setResizable(false);
		addBookPopup.setTitle("GJStore");
		addBookPopup.setBounds(400, 100, 768, 768);
		
		Background blur = new Background("src/Assets/background/Blur.png", addBookPopup);
		blur.setVisible(false);
		
		TextBox id = new TextBox(278, 190, 354, 38, "src/Assets/button/idBox.png", addBookPopup);
		
		TextBox name = new TextBox(278, 245, 354, 38, "src/Assets/button/idBox.png", addBookPopup);
		
		TextBox writer = new TextBox(278, 300, 354, 38, "src/Assets/button/idBox.png", addBookPopup);
		
		Button image = new Button(370, 380, 130, 150, addBookPopup);
		image.getButton().addActionListener(event ->{
			path = browseHandle();
			try {
				if(path == null) {
				}else {
					Image display = ImageIO.read(new FileInputStream(path)).getScaledInstance(130, 150, Image.SCALE_DEFAULT);
					image.setImage(display);
				}	
			} catch (FileNotFoundException e) {
	
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		Button confirm = new Button(500, 650, 167, 45, "src/Assets/button/confirm.png", addBookPopup);
		confirm.getButton().addActionListener(event ->{
			
			DataDownloader downloader = new DataDownloader();
			downloader.connect();
			Connection conn = downloader.getConnection();
			if(id.getText().equals("") || name.getText().equals("") || writer.getText().equals("")) {
				blur.setVisible(true);
				System.out.println(1);
			}else {
				try {
					PreparedStatement ps = conn.prepareStatement("insert into bookdetail(id,name,writer,status,image) values(?,?,?,?,?)");
					ps.setString(1, id.getText());
					ps.setString(2, name.getText());
					ps.setString(3, writer.getText());
					ps.setBoolean(4, true);
					ps.setBlob(5,  new FileInputStream(new File(path)));
					ps.execute();
					window.addBookPopup.setVisible(false);
					BookListScreen.closeScreen();
					BookListScreen.goToBookListScreen();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		Button cancel = new Button(100, 650, 167, 45, "src/Assets/button/cancel.png", addBookPopup);
		cancel.getButton().addActionListener(event ->{
			window.addBookPopup.setVisible(false);
			BookListScreen.closeScreen();
			BookListScreen.goToBookListScreen();
		});
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon("src/Assets/background/AddBook.png"));
		background.setBounds(0, 0, 768, 768);
		addBookPopup.getContentPane().add(background);
	}
	
	public String browseHandle() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".Images", ".jpg", ".png", ".gif");
        fileChooser.addChoosableFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File select = fileChooser.getSelectedFile();
            String path = select.getAbsolutePath();
            return path;
        } else if (result == JFileChooser.CANCEL_OPTION) {
        }
        return null;
    }
}
