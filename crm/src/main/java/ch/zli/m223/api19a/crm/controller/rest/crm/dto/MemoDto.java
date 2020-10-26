package ch.zli.m223.api19a.crm.controller.rest.crm.dto;

import ch.zli.m223.api19a.crm.model.Memo;

public class MemoDto {
	public Long id;
	public Long customer_id;
	public String note;
	public Long date;
	
	public MemoDto() {}
	
	public MemoDto(Memo memo) {
		this.id = memo.getId();
		this.customer_id = memo.getCustomerId();
		this.note = memo.getNote();
		this.date = memo.getTimeInMs();
	}
}
