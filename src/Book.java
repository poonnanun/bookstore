import java.awt.Image;
import java.awt.image.BufferedImage;

public class Book {

	private String id;
	private String name;
	private String writer;
	private boolean status;
	private Image realImage;
	private Image image;
	
	public Book(String id, String name, String writer, boolean status, Image image, Image realImage) {
		this.setId(id);
		this.setName(name);
		this.setWriter(writer);
		this.setStatus(status);
		this.setImage(image);
		this.setRealImage(realImage);
	}

	public Book() {
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public Image getRealImage() {
		return realImage;
	}
	
	public void setRealImage(Image image) {
		this.realImage = image;
	}
	
	@Override
	public String toString() {
		return id+ "   " + name;
	}
}
