package abra.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import abra.controller.model.AuthorData;
import abra.controller.model.AuthorData.AuthorCustomers;
import abra.controller.model.BookData;
import abra.controller.model.CustomerData;
import abra.service.AbraService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/abra")
@Slf4j
public class AbraController {
	
	@Autowired
	private AbraService abraService;
	
	
	/*
	 * Author "CREATE" method
	 */
	@PostMapping("/author")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AuthorData insertAuthor(@RequestBody AuthorData authorData) {
		log.info("Adding author {}", authorData);
		return abraService.saveAuthor(authorData);
	}
	
	/*
	 * Author "READ" - retrieve all
	 */
	@GetMapping("/author")
	public List<AuthorData> retrieveAllAuthors() {
		log.info("Retrieving all authors");
		return abraService.retrieveAllAuthors();
	}
	
	/*
	 * Author "READ" - retrieve by ID
	 */
	@GetMapping("/author/{authorId}")
	public AuthorData retrieveAuthorById(@PathVariable Long authorId) {
		log.info("Retrieving author with ID {}", authorId);
		return abraService.retrieveAuthorById(authorId);
	}
	/*
	 Author "UPDATE" method
	 */
	@PutMapping("/author/{authorId}")
	public AuthorData updateAuthor(@PathVariable Long authorId,
			@RequestBody AuthorData authorData) {
		authorData.setAuthorId(authorId);
		log.info("Making changes to the author at ID# {}", authorId);
		return abraService.saveAuthor(authorData);
	}
	
	/*
	 * Author "DELETE" method
	 */
	@DeleteMapping("/author/{authorId}")
	public Map<String, String> deleteAuthorById(@PathVariable Long authorId) {
		log.info("Deleting author w/ID = {}", authorId);
		abraService.deleteAuthorById(authorId);
		return Map.of("message", "Author w/ID = " + authorId + " was successfully deleted.");
	}
	
	/*
	 * Book "CREATE" method
	 */
	@PostMapping("/author/{authorId}/book")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BookData insertBook(@PathVariable Long authorId, @RequestBody BookData bookData) {
		log.info("Adding book {} to the author at ID {}", bookData, authorId);
		return abraService.saveBook(authorId, bookData);
	}
	
	/*
	 * Book "UPDATE" method
	 */
	@PutMapping("/author/{authorId}/book/{bookId}")
	public BookData updateBook(@PathVariable Long authorId, @PathVariable Long bookId,
			@RequestBody BookData bookData) {
		
		bookData.setBookId(bookId);
		log.info("Updating book {} for author w/ID = {}", bookData, authorId);
		return abraService.saveBook(authorId, bookData);
	}
	
	/*
	 * Book "READ" - all by author
	 */
	@GetMapping("/books")
	public List<BookData> retrieveAllBooks() {
		log.info("Retrieving all books");
		return abraService.retrieveAllBooks();
	}
	
	/*
	 * Book "READ" - by ID
	 */
	@GetMapping("/books/{bookId}")
	public BookData retrieveBookById(@PathVariable Long bookId) {
		log.info("Retrieving book w/ID = {}", bookId);
		return abraService.retrieveBookById(bookId);
	}
	
	
	/*
	 * Customer "CREATE" method
	 */
	@PostMapping("/customers")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CustomerData insertCustomer(@RequestBody CustomerData customerData) {
		log.info("Creating customer {}", customerData);
		return abraService.saveCustomer(customerData);
	}
	
	/*
	 * Customer "UPDATE" method
	 */
	@PutMapping("/customers/{customerId}")
	public CustomerData updateCustomer(@PathVariable Long customerId,
			@RequestBody CustomerData customerData) {
		
		customerData.setCustomerId(customerId);
		log.info("Updating customer w/ID = {}", customerId);
		return abraService.saveCustomer(customerData);
	}
	
	/*
	 * Customer "READ" - all
	 */
	@GetMapping("/customers")
	public List<CustomerData> retrieveAllCustomers() {
		log.info("Retrieving all customers");
		return abraService.retrieveAllCustomers();
	}
	
	/* 
	 * Customer "READ" - by ID
	 */
	@GetMapping("/customers/{customerId}")
	public CustomerData retrieveCustomerById(@PathVariable Long customerId) {
		log.info("Retrieving customer w/ID = {}", customerId);
		return abraService.retrieveCustomerById(customerId);
	}
	
	
	/*
	 * Author_Customer "CREATE" method
	 */
	 
	@PostMapping("/author/{authorId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AuthorCustomers insertAuthorCustomer(@PathVariable Long authorId,
			@RequestBody AuthorCustomers authorCustomers) {
		
		log.info("Adding customer {} to author w/ID = {}", authorCustomers, authorId);
		return abraService.saveAuthorCustomer(authorId, authorCustomers);
	}
	
	/*
	 * STRETCH:
	 *  - Add genre
	 *  - retrieveCustomerByBookId
	 *  - provide means to Checkout/pay
	 */
	
}