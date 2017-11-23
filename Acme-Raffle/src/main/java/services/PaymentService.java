
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Manager;
import domain.Payment;
import repositories.PaymentRepository;
import security.LoginService;

@Service
@Transactional
public class PaymentService {

	@Autowired
	private PaymentRepository	repository;

	@Autowired
	private LoginService		loginService;


	public PaymentService() {
		super();
	}

	public Payment create() {
		Manager manager = (Manager) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Payment payment = new Payment();
		payment.setCreditcard(manager.getCreditCard());

		return payment;

	}

	public List<Payment> findAll() {
		return repository.findAll();
	}

	public Payment save(Payment entity) {
		Assert.notNull(entity);

		return repository.save(entity);
	}

	public Payment findOne(Integer id) {
		Assert.notNull(id);
		return repository.findOne(id);
	}

}
