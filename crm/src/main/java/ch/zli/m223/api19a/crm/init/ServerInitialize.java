package ch.zli.m223.api19a.crm.init;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ch.zli.m223.api19a.crm.model.Customer;
import ch.zli.m223.api19a.crm.repository.CustomerRepository;
import ch.zli.m223.api19a.crm.repository.MemoRepository;
import ch.zli.m223.api19a.crm.repository.UserRepository;

/**
 * Will create some test-data, to work with.
 * @author Patrick Hettich
 *
 */
@Component
public class ServerInitialize implements ApplicationRunner {
	@Autowired UserRepository userRepository;
	@Autowired CustomerRepository customerRepository;
	@Autowired MemoRepository memoRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Set<String> user = new HashSet<>(); user.add("user");
		Set<String> admin = new HashSet<>(); admin.add("admin");
		Set<String> usmin = new HashSet<>(); usmin.add("user");
		usmin.add("admin");
		
		userRepository.createUser("user", "1234", user);
		userRepository.createUser("admin", "1234", admin);
		userRepository.createUser("usmin", "1234", usmin);
		
		Customer customer1 = customerRepository.createCustomer("Customer", "Customer street", "Customer city");
		Customer customerGaga = customerRepository.createCustomer("Test Customer", "Test street", "Test city");
		Customer customerGugus = customerRepository.createCustomer("Final Customer", "Final road", "Final town");
		
		memoRepository.createMemo(customer1, "First entry");
		memoRepository.createMemo(customer1, "Second entry");
		memoRepository.createMemo(customer1, "Third entry");
		
		memoRepository.createMemo(customerGaga, "this is some information");
		memoRepository.createMemo(customerGaga, "some more information");
		memoRepository.createMemo(customerGaga, "a lot of information");
		
		memoRepository.createMemo(customerGugus, "He has a 10 cats");
		memoRepository.createMemo(customerGugus, "He has a dog");
	}

}
