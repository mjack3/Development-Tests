
package services;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import domain.Bill;
import domain.Money;
import repositories.BillRepository;

@Service
@Transactional
public class BillService {

	private BillRepository billRepository;


	public BillService() {
		super();
	}

	public Bill create() {
		Bill bill = new Bill();

		bill.setTicket(generateTicker());
		Money money = new Money();
		money.setAmount(99.95);
		money.setCurrency("€");
		bill.setMoney(money);
		bill.setMoment(new Date());

		return bill;

	}

	public long count() {
		return billRepository.count();
	}

	public List<Bill> findAll() {
		return billRepository.findAll();
	}

	public Bill save(Bill entity) {

		return billRepository.save(entity);
	}

	public Bill findOne(Integer id) {
		return billRepository.findOne(id);
	}

	private String generateTicker() {
		StringBuilder str = new StringBuilder();

		String letters = new String("QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm");
		Random rand = new Random(billRepository.count());

		str.append('#');

		for (int i = 0; i < 8; i++) {
			str.append(letters.charAt(rand.nextInt(letters.length())));
		}

		return str.toString();
	}

}
