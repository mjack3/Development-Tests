
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Payment;
import repositories.PaymentRepository;

@Service
@Transactional
public class PaymentService {

	private PaymentRepository paymentRepository;


	public PaymentService() {
		super();
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return paymentRepository.exists(id);
	}

	public List<Payment> findAll(Iterable<Integer> arg0) {
		return paymentRepository.findAll(arg0);
	}

	public Payment save(Payment entity) {
		return paymentRepository.save(entity);
	}

	public Payment findOne(Integer id) {
		Assert.notNull(id);
		return paymentRepository.findOne(id);
	}

}
