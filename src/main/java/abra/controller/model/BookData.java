package abra.controller.model;

import abra.entity.Author;
import abra.entity.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookData {
	private Long bookId;
	private String title;
	private BookAuthor author;
	
	public BookData(Book book) {
		bookId = book.getBookId();
		title = book.getTitle();
		author = new BookAuthor(book.getAuthor());
	}
	
	@Data
	@NoArgsConstructor
	public static class BookAuthor {
		private Long authorId;
		private String authorFirstName;
		private String authorMiddleName;
		private String authorLastName;
		
		public BookAuthor(Author author) {
			authorId = author.getAuthorId();
			authorFirstName = author.getAuthorFirstName();
			authorMiddleName = author.getAuthorMiddleName();
			authorLastName = author.getAuthorLastName();
		}
	}
}