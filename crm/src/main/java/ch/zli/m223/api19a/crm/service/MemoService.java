package ch.zli.m223.api19a.crm.service;

import java.util.List;

import ch.zli.m223.api19a.crm.model.Memo;

public interface MemoService {

	List<Memo[]> getAllMemos();
	
	Memo createMemo(long customer_id, String note, long date);
	
	void deleteMemo(long id);
	
	Memo updateMemo(long id, long customer_id, String note, long date);
}
