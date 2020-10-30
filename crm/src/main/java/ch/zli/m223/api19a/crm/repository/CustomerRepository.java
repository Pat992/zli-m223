package ch.zli.m223.api19a.crm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zli.m223.api19a.crm.model.Customer;
import ch.zli.m223.api19a.crm.model.Memo;
import ch.zli.m223.api19a.crm.model.impl.CustomerImpl;

/**
 * Repository for the Customer, extends the JPA-repository.
 * @author Patrick Hettich
 *
 */
public interface CustomerRepository extends JpaRepository<CustomerImpl, Long>  {

	/**
	 * Creates a new Customer and returns it
	 * @param name
	 * @param street
	 * @param city
	 * @return
	 */
	default Customer createCustomer(String name, String street, String city) {
		List<Memo> memos = new ArrayList<Memo>();
		CustomerImpl customer = new CustomerImpl(name, street, city, memos);
		
		return save(customer);
	}
	
	/**
	 * Takes a customer and updates its values.
	 * @param customer
	 * @param name
	 * @param street
	 * @param city
	 * @return
	 */
	default Customer updateCustomer(Customer customer, String name, String street, String city) {
		return save(((CustomerImpl)customer).updateCustomer(name, street, city));
	}
}
