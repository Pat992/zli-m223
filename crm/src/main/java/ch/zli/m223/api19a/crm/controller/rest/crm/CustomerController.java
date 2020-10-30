package ch.zli.m223.api19a.crm.controller.rest.crm;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.zli.m223.api19a.crm.controller.rest.crm.dto.ChangeCustomerDto;
import ch.zli.m223.api19a.crm.controller.rest.crm.dto.CustomerDto;
import ch.zli.m223.api19a.crm.controller.rest.crm.dto.CustomerInputDto;
import ch.zli.m223.api19a.crm.controller.rest.crm.dto.MemoDto;
import ch.zli.m223.api19a.crm.controller.rest.crm.dto.MemoInputDto;
import ch.zli.m223.api19a.crm.model.Customer;
import ch.zli.m223.api19a.crm.model.Memo;
import ch.zli.m223.api19a.crm.service.CustomerService;

/**
 * Controller to create endpoints for working with customer and memos.
 * @author Patrick Hettich
 *
 */
@CrossOrigin()
@RestController
public class CustomerController {
	@Autowired CustomerService customerService;
	
	// CRUD for customer
	
	/**
	 * Get all the existing customers.
	 * @return
	 */
	@GetMapping("/customers")
	public List<CustomerDto> getAllCustomers() {
		return customerService.getAllCustomers().stream()
				.map((Customer customer) -> { return new CustomerDto(customer); })
				.collect(Collectors.toList());
	}
	
	/**
	 * Delete a customer with the given ID
	 * @param id -> ID of the customer
	 */
	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable long id) {
		customerService.deleteCustomer(id);
	}
	
	/**
	 * Update a customer with the given ID
	 * data must be identical to ChangeCustomerDto
	 * @param id	-> ID of the customer
	 * @param ccDto	-> The Dto for the data required
	 * @return
	 */
	@PutMapping("/customers/{id}")
	public CustomerDto updateCustomer(@PathVariable long id, @RequestBody ChangeCustomerDto ccDto) {
		return new CustomerDto(customerService.updateCustomer(id, ccDto.name, ccDto.street, ccDto.city));
	}
	
	/**
	 * Add a new Customer,
	 * data must be similar to CustomerInputDto
	 * @param ciDto	-> The Dto for the data required
	 * @return
	 */
	@PostMapping("/customers") 
	public CustomerDto createCustomer(@RequestBody CustomerInputDto ciDto) {
		return new CustomerDto(customerService.createCustomer(ciDto.name, ciDto.street, ciDto.city));
	}
	

	// CRUD for memos
	
	/**
	 * Get all Memos from a given customer
	 * @param id -> ID of the customer
	 * @return
	 */
	@GetMapping("/customers/{id}/memos")
	public List<MemoDto> getAllMemosFromCustomer(@PathVariable long id) {
		return customerService.getAllMemosFromCustomer(id).stream()
		.map((Memo memo) -> { return new MemoDto(memo); })
		.collect(Collectors.toList());
	}
	
	/**
	 * Add a new memo to an existing Customer
	 * expects an ID and a MemoInputDto
	 * @param id	-> ID of the customer
	 * @param miDto	-> The Dto for the data required
	 * @return
	 */
	@PostMapping("/customers/{id}/memos")
	public MemoDto createMemo(@PathVariable long id, @RequestBody MemoInputDto miDto) {
		return new MemoDto(customerService.createMemo(id, miDto.note));
	}
	
	/**
	 * Delete a memo
	 * @param id -> ID of the memo
	 */
	@DeleteMapping("/customers/memos/{id}")
	public void deleteMemo(@PathVariable long id) {
		this.customerService.deleteMemo(id);
	}
	
	/**
	 * Update an existing memo
	 * @param custId	-> ID of the customer
	 * @param memoId	-> ID of the memo
	 * @param miDto		-> The Dto for the data required
	 * @return
	 */
	@PutMapping("/customers/{custId}/memos/{memoId}")
	public MemoDto updateMemo(@PathVariable long  custId, @PathVariable long memoId, @RequestBody MemoInputDto miDto) {
		return new MemoDto(this.customerService.updateMemo(memoId, custId, miDto.note));
	}
}
