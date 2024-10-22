package ch.zli.m223.api19a.crm.model.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ch.zli.m223.api19a.crm.model.Customer;
import ch.zli.m223.api19a.crm.model.Memo;

/**
 * Implementation for Memo
 * @author Patrick Hettich
 *
 */
@Entity(name="Memo")
public class MemoImpl implements Memo {

	/**
	 * private Attributes for MemoImpl
	 */
	@Id
	@GeneratedValue
	private Long id;
	
	private long timeInMs;
	@Column(nullable=false)
	private String note;
	
	@ManyToOne
	private CustomerImpl customer;
	
	protected MemoImpl() {} // For Jpa
	
	/**
	 * ctor
	 * @param note
	 * @param customer
	 */
	public MemoImpl(String note, Customer customer) {
		this.note = note;
		this.customer = (CustomerImpl)customer;
		this.timeInMs = this.setCurrentTimeInMs();
	}

	/**
	 * Getter
	 */
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
	
	/**
	 * Sets current time in milliseconds as a long.
	 * @return
	 */
	private Long setCurrentTimeInMs() {
		Date date = new Date();
		long imeInMs = date.getTime();
		
		return imeInMs;
	}
}
