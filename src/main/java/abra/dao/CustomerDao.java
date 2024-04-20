package abra.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import abra.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {
	
}