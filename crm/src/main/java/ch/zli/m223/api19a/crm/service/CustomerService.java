package ch.zli.m223.api19a.crm.service;

import java.util.List;

import ch.zli.m223.api19a.crm.model.Customer;

public interface CustomerService {
	
	List<Customer[]> getAllCustomer();
	
	Customer createCustomer(String name, String street, String city);
	
	void deleteCustomer(long id);
	
	Customer updateCustomer(long id, String name, String street, String city);
}
