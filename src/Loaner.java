import java.util.ArrayList;
import java.util.List;

public class Loaner {

	private String id;
	private String name;
	private String surname;
	private String tel;
	private List<Book> loanBook;
	
	public Loaner(String id, String name, String surname, String tel) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.tel = tel;
		loanBook = new ArrayList<>();
	}
	
	public void removeBook() {
		
	}
	
	public void addBook(Book book) {
		loanBook.add(book);
	}
	
	public List<Book> getBook(){
		return this.loanBook;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public String getTel() {
		return this.tel;
	}
}
