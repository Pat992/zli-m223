package ch.zli.m223.api19a.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zli.m223.api19a.crm.model.Customer;
import ch.zli.m223.api19a.crm.model.Memo;
import ch.zli.m223.api19a.crm.model.impl.MemoImpl;

public interface MemoRepository extends JpaRepository<MemoImpl, Long> {

	default Memo createMemo(Customer customer, String note) {
		MemoImpl memo = new MemoImpl(note, customer);
		return save(memo);
	}

	default Memo updateMemo(Memo memo, String note) {
		return save(((MemoImpl)memo).updateMemo(note));
	}
}
