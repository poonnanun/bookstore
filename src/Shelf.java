import java.util.List;

public class Shelf {

	private Book book1;
	private Book book2;
	private Book book3;
	private Book book4;
	
	
	public Shelf (Book book1, Book book2, Book book3, Book book4) {
		this.book1 = book1;
		this.book2 = book2;
		this.book3 = book3;
		this.book4 = book4;
	}
	
	public Shelf(List<Book> book) {
		this.book1 = book.get(0);
		this.book2 = book.get(1);
		this.book3 = book.get(2);
		this.book4 = book.get(3);
		
	}
	
	public Book getBook (int position) {
		switch(position){
			case 1 : return book1;
			case 2 : return book2;
			case 3 : return book3;
			case 4 : return book4;
			default : return null;
		}
	}
}
