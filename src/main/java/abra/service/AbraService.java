package abra.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import abra.controller.model.AuthorData;
import abra.controller.model.AuthorData.AuthorCustomers;
import abra.controller.model.BookData;
import abra.controller.model.CustomerData;
import abra.dao.AuthorDao;
import abra.dao.BookDao;
import abra.dao.CustomerDao;
import abra.entity.Author;
import abra.entity.Book;
import abra.entity.Customer;

@Service
public class AbraService {
	
	@Autowired
	private AuthorDao authorDao;

	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Transactional(readOnly = false)
	public AuthorData saveAuthor(AuthorData authorData) {
		Long authorId = authorData.getAuthorId();
		Author author = findOrCreateAuthor(authorId);
		
		copyAuthorFields(author, authorData);
		return new AuthorData(authorDao.save(author));
	}
	
	private void copyAuthorFields(Author author, AuthorData authorData) {
		author.setAuthorFirstName(authorData.getAuthorFirstName());
		author.setAuthorMiddleName(authorData.getAuthorMiddleName());
		author.setAuthorLastName(authorData.getAuthorLastName());
	}

	private Author findOrCreateAuthor(Long authorId) {
		if (Objects.isNull(authorId)) return new Author();
		else return findAuthorById(authorId);
	}
	
	private Author findAuthorById(Long authorId) {
		return authorDao.findById(authorId)
				.orElseThrow(() -> new NoSuchElementException(
						"Author w/ID = " + authorId + " was not found."));
	}
	
	@Transactional(readOnly = true)
	public List<AuthorData> retrieveAllAuthors() {
		List<Author> authors = authorDao.findAll();
		List<AuthorData> result = new LinkedList<>();
		
		for(Author author : authors) {
			AuthorData authorGroup = new AuthorData(author);
			
			result.add(authorGroup);
		}	
		
		return result;
	}
	
	@Transactional(readOnly = true)
	public AuthorData retrieveAuthorById(Long authorId) {
		return new AuthorData(findAuthorById(authorId));
	}

	@Transactional(readOnly = false)
	public void deleteAuthorById(Long authorId) {
		Author author = findAuthorById(authorId);
		authorDao.delete(author);
	}

	@Transactional(readOnly = false)
	public BookData saveBook(Long authorId, BookData bookData) {
		Author author = findAuthorById(authorId);
		Book book = findOrCreateBook(bookData.getBookId());
		
		copyBookFields(book, bookData);
		
		book.setAuthor(author);
		author.getBooks().add(book);
		Book dbBook = bookDao.save(book);
		
		return new BookData(dbBook);
	}

	private void copyBookFields(Book book, BookData bookData) {
		book.setBookId(bookData.getBookId());
		book.setTitle(bookData.getTitle());
	}

	private Book findOrCreateBook(Long bookId) {
		Book book;
		
		if (Objects.isNull(bookId)) book = new Book();
		else book = findBookById(bookId);
		
		return book;
	}

	private Book findBookById(Long bookId) {
		Book book = bookDao.findById(bookId)
				.orElseThrow(() -> new NoSuchElementException(
						"Book w/ID = " + bookId + " was not found."));
		
		return book;
	}

	@Transactional(readOnly = true)
	public List<BookData> retrieveAllBooks() {
		List<Book> books = bookDao.findAll();
		List<BookData> response = new LinkedList<>();
		
		for (Book book : books) {
			response.add(new BookData(book));
		}
		
		return response;
	}

	@Transactional(readOnly = true)
	public BookData retrieveBookById(Long bookId) {
		Book book = findBookById(bookId);
		
		if (book.getBookId() != bookId)
			throw new IllegalStateException("ID mismatch");
		
		return new BookData(book);
	}

	@Transactional(readOnly = false)
	public AuthorCustomers saveAuthorCustomer(Long authorId, AuthorCustomers authorCustomers) {
		Author author = findAuthorById(authorId);
		Long customerId = authorCustomers.getCustomerId();
		Customer customer = findOrCreateCustomer(authorId, customerId);
		
		copyCustomerFields(customer, authorCustomers);
		
		customer.getAuthors().add(author);
		author.getCustomers().add(customer);
		Customer dbCustomer = customerDao.save(customer);
		return new AuthorCustomers(dbCustomer);
	}

	private void copyCustomerFields(Customer customer, AuthorCustomers authorCustomers) {
		customer.setCustomerId(authorCustomers.getCustomerId());
		customer.setCustomerFirstName(authorCustomers.getCustomerFirstName());
		customer.setCustomerLastName(authorCustomers.getCustomerLastName());
		customer.setCustomerEmail(authorCustomers.getCustomerEmail());
	}

	private Customer findOrCreateCustomer(Long authorId, Long customerId) {
		Customer customer;
		
		if (Objects.isNull(customerId)) customer = new Customer();
		else customer = findCustomerById(authorId, customerId);
		
		return customer;
	}

	private Customer findCustomerById(Long authorId, Long customerId) {
		
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException(
						"Customer w/ID = " + customerId + " does not exist."));
		
		boolean found = false;
		for (Author author : customer.getAuthors()) {
			if (author.getAuthorId() != authorId) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			throw new IllegalArgumentException("Customer w/ID = " + customerId
					+ " has not bought any books from the author at ID = " + authorId);
		}
		
		return customer;
	}

	@Transactional(readOnly = false)
	public CustomerData saveCustomer(CustomerData customerData) {
		Long customerId = customerData.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId);
		
		copyCustomerFields(customer, customerData);
		return new CustomerData(customerDao.save(customer));
	}

	private void copyCustomerFields(Customer customer, CustomerData customerData) {
		customer.setCustomerId(customerData.getCustomerId());
		customer.setCustomerFirstName(customerData.getCustomerFirstName());
		customer.setCustomerLastName(customerData.getCustomerLastName());
		customer.setCustomerEmail(customerData.getCustomerEmail());
	}

	private Customer findOrCreateCustomer(Long customerId) {
		Customer customer;
		if (Objects.isNull(customerId)) customer = new Customer();
		else customer = findCustomerById(customerId);
		
		return customer;
	}

	private Customer findCustomerById(Long customerId) {
		return customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException(
						"Customer w/ID = " + customerId + " was not found."));
	}

	@Transactional(readOnly = true)
	public List<CustomerData> retrieveAllCustomers() {
		List<Customer> customers = customerDao.findAll();
		List<CustomerData> result = new LinkedList<>();
		
		for (Customer customer : customers) {
			CustomerData customerGroup = new CustomerData(customer);
			result.add(customerGroup);
		}
		
		return result;
	}

	@Transactional(readOnly = true)
	public CustomerData retrieveCustomerById(Long customerId) {
		Customer customer = findCustomerById(customerId);
		
		if (customer.getCustomerId() != customerId)
			throw new IllegalStateException("ID mismatch");
		
		return new CustomerData(customer);
	}
	

}