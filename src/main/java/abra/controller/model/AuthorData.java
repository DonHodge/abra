package abra.controller.model;

import java.util.HashSet;
import java.util.Set;
import abra.entity.Author;
import abra.entity.Book;
import abra.entity.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorData {
	private Long authorId;
	private String authorFirstName;
	private String authorMiddleName;
	private String authorLastName;
	private Set<AuthorBooks> books = new HashSet<>();
	private Set<AuthorCustomers> customers = new HashSet<>();
	
	public AuthorData(Author author) {
		authorId = author.getAuthorId();
		authorFirstName = author.getAuthorFirstName();
		authorMiddleName = author.getAuthorMiddleName();
		authorLastName = author.getAuthorLastName();
		
		for (Book book: author.getBooks()) {
			books.add(new AuthorBooks(book));
		}
		
		for (Customer customer : author.getCustomers()) {
			customers.add(new AuthorCustomers(customer));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class AuthorBooks {
		private Long bookId;
		private String title;
		
		public AuthorBooks(Book book) {
			bookId = book.getBookId();
			title = book.getTitle();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class AuthorCustomers {
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		
		public AuthorCustomers(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
		}
	}
}