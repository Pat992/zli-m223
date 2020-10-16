package ch.zli.m223.api19a.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zli.m223.api19a.crm.exception.CustomerNotFoundException;
import ch.zli.m223.api19a.crm.exception.MemoNotFoundException;
import ch.zli.m223.api19a.crm.exception.NoCustomerNameException;
import ch.zli.m223.api19a.crm.model.Customer;
import ch.zli.m223.api19a.crm.model.Memo;
import ch.zli.m223.api19a.crm.repository.CustomerRepository;
import ch.zli.m223.api19a.crm.repository.MemoRepository;
import ch.zli.m223.api19a.crm.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired private CustomerRepository customerRepository;
	@Autowired private MemoRepository memoRepository;

	@Override
	public List<Customer> getAllCustomers() { 
		return new ArrayList<>(customerRepository.findAll()); 
	}

	@Override
	public Customer getCustomerById(long id) {
		return this.customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
	}

	@Override
	public Customer createCustomer(String name, String street, String city) {
		if(name == null || name.trim().isEmpty()) { throw new NoCustomerNameException(); }
		
		return this.customerRepository.createCustomer(name, street, city);
	}

	@Override
	public void deleteCustomer(long id) {
		this.getCustomerById(id);
		this.customerRepository.deleteById(id);
	}

	@Override
	public Customer updateCustomer(long id, String name, String street, String city) {
		Customer customer = this.getCustomerById(id);
		
		return this.customerRepository.updateCustomer(customer, name, street, city);
	}

	@Override
	public List<Memo> getAllMemosFromCustomer(long customerId) {
		Customer customer = this.getCustomerById(customerId);
		return customer.getMemos();
	}	
	
	@Override
	public Memo getMemoById(long id) {
		return this.memoRepository.findById(id).orElseThrow(MemoNotFoundException::new);
	}

	@Override
	public Memo createMemo(long customerId, String note) {
		Customer customer = this.getCustomerById(customerId);
		return this.memoRepository.createMemo(customer, note);
	}

	@Override
	public void deleteMemo(long id) {
		getMemoById(id);
		this.memoRepository.deleteById(id);
	}

	@Override
	public Memo updateMemo(long id, long customerId, String note) {
		Memo memo = getMemoById(id);
		return this.memoRepository.updateMemo(memo, note);
	}
}
