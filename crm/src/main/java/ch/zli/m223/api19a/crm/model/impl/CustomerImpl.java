package ch.zli.m223.api19a.crm.model.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ch.zli.m223.api19a.crm.model.Customer;
import ch.zli.m223.api19a.crm.model.Memo;

@Entity(name="Customer")
public class CustomerImpl implements Customer {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private String street;
	private String city;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<MemoImpl> memos;
	
	protected CustomerImpl() {} // For Jpa
	
	public CustomerImpl(String name, String street, String city, List<Memo> memos) {
		this.name = name;
		this.street = street;
		this.city = city;
		
		this.memos = memos.stream()
				.map((Memo memo) -> { return (MemoImpl)memo; })
				.collect(Collectors.toList());
	}
	
	@Override
	public Long getId() { return this.id; }

	@Override
	public String getName() { return this.name; }

	@Override
	public String getStreet() { return this.street; }

	@Override
	public String getCity() { return this.city; }

	@Override
	public List<Memo> getMemos() {
		return this.memos.stream()
				.map((Memo memo) -> { return (Memo)memo; })
				.collect(Collectors.toList());
	}

	public CustomerImpl updateCustomer(String name, String street, String city) {
		this.name = name;
		this.street = street;
		this.city = city;
		
		return this; 
	}
}
