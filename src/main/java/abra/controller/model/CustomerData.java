package abra.controller.model;

import java.util.HashSet;
import java.util.Set;
import abra.entity.Author;
import abra.entity.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerData {
private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	private Set<ProductAuthor> authors = new HashSet<>();
	
	public CustomerData(Customer customer) {
		customerId = customer.getCustomerId();
		customerFirstName = customer.getCustomerFirstName();
		customerLastName = customer.getCustomerLastName();
		customerEmail = customer.getCustomerEmail();
		
		for (Author author : customer.getAuthors()) {
			authors.add(new ProductAuthor(author));
		}
		 
	}
	
	@Data
	@NoArgsConstructor
	public static class ProductAuthor {
		private Long authorId;
		private String authorFirstName;
		private String authorMiddleName;
		private String authorLastName;
		
		public ProductAuthor(Author author) {
			authorId = author.getAuthorId();
			authorFirstName = author.getAuthorFirstName();
			authorMiddleName = author.getAuthorMiddleName();
			authorLastName = author.getAuthorLastName();
		}
	}
}