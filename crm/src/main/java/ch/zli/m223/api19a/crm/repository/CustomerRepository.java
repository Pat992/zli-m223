package ch.zli.m223.api19a.crm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zli.m223.api19a.crm.model.Customer;
import ch.zli.m223.api19a.crm.model.Memo;
import ch.zli.m223.api19a.crm.model.impl.CustomerImpl;

public interface CustomerRepository extends JpaRepository<CustomerImpl, Long>  {

	default Customer createCustomer(String name, String street, String city) {
		List<Memo> memos = new ArrayList<Memo>();
		CustomerImpl customer = new CustomerImpl(name, street, city, memos);
		
		return save(customer);
	}
	
	default Customer updateCustomer(Customer customer, String name, String street, String city) {
		return save(((CustomerImpl)customer).updateCustomer(name, street, city));
	}
}
