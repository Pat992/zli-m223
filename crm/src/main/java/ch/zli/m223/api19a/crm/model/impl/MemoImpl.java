package ch.zli.m223.api19a.crm.model.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ch.zli.m223.api19a.crm.model.Customer;
import ch.zli.m223.api19a.crm.model.Memo;

@Entity(name="Memo")
public class MemoImpl implements Memo {

	@Id
	@GeneratedValue
	private Long id;
	
	private long timeInMs;
	private String note;
	
	@ManyToOne
	private CustomerImpl customer;
	
	protected MemoImpl() {} // For Jpa
	
	public MemoImpl(String note, Customer customer) {
		this.note = note;
		this.customer = (CustomerImpl)customer;
		this.timeInMs = this.setCurrentTimeInMs();
	}
	
	@Override
	public Long getId() { return this.id; }
	
	@Override
	public long getCustomerId() { return this.customer.getId(); }

	@Override
	public long getTimeInMs() { return this.timeInMs; }
	
	@Override
	public String getNote() { return this.note; }
	
	public MemoImpl updateMemo(String note) {
		this.note = note;
		this.timeInMs = this.setCurrentTimeInMs();
		return this;
	}
	
	private Long setCurrentTimeInMs() {
		ZonedDateTime startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault());
		
		return startOfToday.toEpochSecond() * 1000;
	}
}
