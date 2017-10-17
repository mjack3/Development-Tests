
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.BillingProcess;

@Repository
public interface BillingProcessRepository extends JpaRepository<BillingProcess, Integer> {

}
