
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	/**
	 * Devuelve a un manager por el id de la cuenta de usuario
	 *
	 * @param id
	 * @return manager
	 */
	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findOneUserAccount(int id);

	@Query("select a from Manager a join a.comments c where c.id = ?1")
	Manager findOneByComment(int commentId);

	@Query("select a from Manager a where a.isDebtor=true")
	List<Manager> isDebtors();

}
