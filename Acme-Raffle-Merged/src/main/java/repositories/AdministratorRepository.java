
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Manager;
import domain.User;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	/**
	 * Devuelve a un administrador por el id de la cuenta de usuario
	 *
	 * @param id
	 * @return manager
	 */
	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findOneUserAccount(int id);

	@Query("select min(r.prizes.size),max(r.prizes.size),avg(r.prizes.size),stddev(r.prizes.size) from Raffle r")
	Object[] prizesPerRaffle();

	@Query("select min(p.codes.size),max(p.codes.size),avg(p.codes.size),stddev(p.codes.size) from Prize p")
	Object[] codesPerPrizes();

	@Query("select min(u.participations.size),max(u.participations.size),avg(u.participations.size),stddev(u.participations.size) from User u")
	Object[] validCodesPerUser();

	@Query("select u from User u where u.participations.size = (select max(a.participations.size) from User a)")
	List<User> userWithMoreValidCodes();
	@Query("select a from Administrator a join a.comments c where c.id = ?1")
	Administrator findOneByComment(int commentId);

	//--------------------------
	//---- DASHBOARD NIVEL B -----
	//-----------------------------

	//The average and the standard deviation of the number of comments per commentable entity.

	@Query("select avg(a.comments.size),stddev(a.comments.size) from Actor a")
	Double[] avgStddevNumberCommentsPerActor();

	@Query("select avg(r.comments.size),stddev(r.comments.size) from Raffle r")
	Double[] avgStddevNumberCommentsPerRaffle();

	@Query("select avg(p.comments.size),stddev(p.comments.size) from Prize p")
	Double[] avgStddevNumberCommentsPerPrize();

	//The average and the standard deviation of the number of stars per commentable entity.

	@Query("select avg(c.rating),stddev(c.rating) from Actor a join a.comments c")
	Double[] avgStddevNumberStarPerActor();

	@Query("select avg(c.rating),stddev(c.rating) from Raffle r join r.comments c")
	Double[] avgStddevNumberStarPerRaffle();

	@Query("select avg(c.rating),stddev(c.rating) from Prize p join p.comments c")
	Double[] avgStddevNumberStarPerPrize();

	//The average number of stars per actor, grouped by country.

	@Query("select avg(c.rating),a.country from Actor a join a.comments c group by a.country")
	Collection<Object[]> avgNumberStarPerActorGroupByCountry();

	//The average number of stars per actor, grouped by city.

	@Query("select avg(c.rating),a.city from Actor a join a.comments c group by a.city")
	Collection<Object[]> avgNumberStarPerActorGroupByCity();

	//2.0

	//The minimum, the average, and the maximum number of bills per manager
	@Query("select min(u.bills.size), avg(u.bills.size), max(u.bills.size) from Raffle u")
	Object[] minAvgMaxBillunpaid();

	//The minimum, the average, and the maximum number of unpaid bills per manager.
	@Query("select min(u.bills.size), avg(u.bills.size), max(u.bills.size) from Raffle u join u.bills a where a.creditCard=null")
	Object[] minAvgMaxBillpaid();

	//The minimum, the average, and the maximum number of paid bills per manager.
	@Query("select min(u.bills.size), avg(u.bills.size), max(u.bills.size) from Raffle u join u.bills a where a.creditCard!=null")
	Object[] minAvgMaxBillPerManager();

	//A listing of managers in decreasing order unpaid bills
	@Query("select distinct a from Manager a join a.raffles b join b.bills c where c.creditCard=null order by c.size desc")
	List<Manager> managersUnpaid();

	//The ratio of default debtors
	@Query("select count(om)*1./(select count(o)*1. from Manager o) from Manager om where om.isDebtor=true")
	Double ratioDebtors();
	//The minimum, the average, and the maximum number of specialities per auditor.
	@Query("select min(u.specialities.size), avg(u.specialities.size), max(u.specialities.size) from Curricula u")
	Object[] minAvgMaxSpecialitiesAuditor();
	//The ratio of auditors who have filled in the education record and/or the work experience in their curricula
	@Query("select count(om)*1./(select count(o)*1. from Auditor o) from Auditor om join om.curricula a join a.workRecords b join a.educationsRecords c where b.endDate!=null and c.endDate !=null")
	Double ratioEndAuditor();

}
