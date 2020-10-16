package ch.zli.m223.api19a.crm.controller.rest.crm.dto;

import ch.zli.m223.api19a.crm.model.Customer;

public class CustomerDto {
	public Long id;
	public String name;
	public String street;
	public String city;
	
	public CustomerDto() {}
	
	public CustomerDto(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.street = customer.getStreet();
		this.city = customer.getCity();
	}
}
