
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BillRepository;
import security.LoginService;
import domain.Bill;
import domain.Configuration;
import domain.Manager;
import domain.Raffle;

@Service
@Transactional
public class BillService {

	@Autowired
	private BillRepository			billRepository;
	@Autowired
	private LoginService			loginService;
	@Autowired
	private ManagerService			managerService;
	@Autowired
	private RaffleService			raffleService;
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConfigurationService	configurationService;


	public BillService() {
		super();
	}

	public Bill create(final Double fee) {
		final Bill bill = new Bill();
		bill.setTicket(this.generateTicker());
		bill.setMoment(new Date(System.currentTimeMillis() - 1));
		bill.setMoney(fee);

		return bill;

	}

	public long count() {
		return this.billRepository.count();
	}

	public List<Bill> findAll() {
		return this.billRepository.findAll();
	}

	public Bill save(final Bill entity) {
		Assert.notNull(entity);

		Bill aux = new Bill();

		if (this.billRepository.exists(entity.getId())) {
			aux = this.billRepository.findOne(entity.getId());
			aux.setMoment(entity.getMoment());
			aux.setMoney(entity.getMoney());
			aux.setTicket(entity.getTicket());
			return this.billRepository.save(aux);

		} else
			return this.billRepository.save(entity);

	}

	public Bill findOne(final Integer id) {
		return this.billRepository.findOne(id);
	}

	private String generateTicker() {

		String ticket = "#";

		do
			ticket += RandomStringUtils.randomAlphanumeric(9).toUpperCase();
		while (this.isUnique(ticket));

		return ticket;

	}

	private boolean isUnique(final String ticket) {
		// TODO Auto-generated method stub

		boolean sw = false;
		final Collection<Bill> bills = this.billRepository.findAll();

		if (bills != null)
			for (final Bill bill : bills)
				if (bill.getTicket().equals(ticket)) {
					sw = true;
					break;
				}

		return sw;
	}
	public Bill pay(final Bill bill) {
		Assert.notNull(bill);
		Bill aux = new Bill();

		final Manager manager = (Manager) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.notNull(manager.getCreditCard());
		Assert.isNull(bill.getCreditCard());

		final List<Bill> bills = new ArrayList<Bill>();

		for (final Raffle a : manager.getRaffles())
			bills.addAll(a.getBills());

		Assert.isTrue(bills.contains(bill));

		bill.setCreditCard(manager.getCreditCard());

		aux = this.billRepository.save(bill);

		Assert.notNull(aux);

		return aux;

	}

	public Boolean checkBanned() {
		Boolean res = true;

		final List<Manager> managers = this.billRepository.managerBillUnpaid();
		for (final Manager a : managers) {
			a.setIsDebtor(res);

			this.managerService.save(a);

		}

		managers.clear();
		managers.addAll(this.billRepository.managerBillPaid());
		res = false;

		for (final Manager a : managers) {
			a.setIsDebtor(res);

			this.managerService.save(a);

		}

		return res;

	}

	public List<Manager> managerBillUnpaid() {
		return this.billRepository.managerBillUnpaid();
	}

	public void generateBills(final Configuration configuration) {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.hasRole("ADMIN"));
		final Date moment = new Date();
		moment.setYear(configuration.getYear() - 1900);
		moment.setMonth(configuration.getMonth() - 1);
		moment.setDate(1);
		moment.setHours(0);
		moment.setMinutes(0);
		moment.setSeconds(0);

		final Collection<Raffle> raffles = this.raffleService.findAllByMoment(moment);
		Assert.notNull(raffles);

		for (final Raffle raffle : raffles) {
			final Bill bill = this.create(configuration.getFee());
			//bill.setTicket("#" + RandomStringUtils.randomAlphanumeric(9).toUpperCase());
			bill.setRaffle(raffle);
			final Bill saved = this.save(bill);
			raffle.getBills().add(saved);
			this.raffleService.save(raffle);
		}

		//	actualizar archivo de configuracion

		configuration.addHistoric(configuration.getMonth(), configuration.getYear());
		this.configurationService.save(configuration);

	}
}
