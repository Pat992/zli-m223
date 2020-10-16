package ch.zli.m223.api19a.crm.service;

import java.util.List;

import ch.zli.m223.api19a.crm.model.Customer;
import ch.zli.m223.api19a.crm.model.Memo;

public interface CustomerService {
	
	List<Customer> getAllCustomers();
	
	Customer createCustomer(String name, String street, String city);
	
	void deleteCustomer(long id);
	
	Customer updateCustomer(long id, String name, String street, String city);
	
	Customer getCustomerById(long id);
	
	List<Memo> getAllMemosFromCustomer(long customerId);
	
	Memo getMemoById(long id);
	
	Memo createMemo(long customerId, String note);
	
	void deleteMemo(long id);
	
	Memo updateMemo(long id, long customer_id, String note);
}
