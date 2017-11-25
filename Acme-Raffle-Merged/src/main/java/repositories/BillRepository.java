
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bill;
import domain.Manager;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

	/**
	 * Managers Deboks
	 * 
	 * @return collection<Manager>
	 */
	@Query("select distinct a from Manager a join a.raffles b join b.bills c where c.creditCard=null and c.payed=null and b.bills.size>=3")
	List<Manager> managerBillUnpaid();

	/**
	 * Managers no deboks
	 * 
	 * @return Collection<Manager>
	 */
	@Query("select distinct a from Manager a join a.raffles b join b.bills c where c.creditCard!=null and c.payed != null and b.bills.size<3")
	List<Manager> managerBillPaid();
}
