package ch.zli.m223.api19a.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zli.m223.api19a.crm.model.Customer;
import ch.zli.m223.api19a.crm.model.Memo;
import ch.zli.m223.api19a.crm.model.impl.MemoImpl;

/**
 * Repository for the Memo, extends the JPA-repository.
 * @author Patrick Hettich
 *
 */
public interface MemoRepository extends JpaRepository<MemoImpl, Long> {

	/**
	 * Creates a new Memo
	 * @param customer
	 * @param note
	 * @return
	 */
	default Memo createMemo(Customer customer, String note) {
		MemoImpl memo = new MemoImpl(note, customer);
		return save(memo);
	}

	/**
	 * Updates an existing memo
	 * @param memo
	 * @param note
	 * @return
	 */
	default Memo updateMemo(Memo memo, String note) {
		return save(((MemoImpl)memo).updateMemo(note));
	}
}
