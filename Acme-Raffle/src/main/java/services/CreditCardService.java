
package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.CreditCard;
import domain.Manager;
import repositories.CreditCardRepository;
import security.LoginService;

@Transactional
@Service
public class CreditCardService {

	@Autowired
	private CreditCardRepository	creditCardRepository;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private LoginService			loginService;


	public CreditCardService() {
		super();
	}

	public List<CreditCard> findAll() {
		return creditCardRepository.findAll();
	}

	public CreditCard save(CreditCard entity) {
		Date today = new Date();

		CreditCard aux = new CreditCard();
		Manager manager = (Manager) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

		Assert.isTrue((entity.getMonth() - today.getMonth() != 1) && today.getDate() + 7 < 32 && today.getYear() - entity.getYear() != 0);
		aux = creditCardRepository.save(entity);
		manager.setCreditCard(aux);

		managerService.save(manager);

		return aux;

	}

	public CreditCard findOne(Integer id) {
		Assert.notNull(id);
		return creditCardRepository.findOne(id);
	}

}
