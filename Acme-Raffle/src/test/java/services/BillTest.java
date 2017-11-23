package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Bill;
import utilities.AbstractTest;

@Transactional
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BillTest extends AbstractTest {

	@Autowired
	private BillService billService;

	@Test
	public void driverBillPay() {

		DisplayBillpayTest("admin", null);

	}

	protected void DisplayBillpayTest(final String username,
			final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			Bill bill = new Bill();
			billService.generate(bill);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void payBillTest(String username, int billId, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			authenticate(username);
			final Bill bill = this.billService.findOne(billId);
			bill.setPayed(new Date(System.currentTimeMillis() - 1));

			this.billService.pay(bill);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);

	}

	@Test
	public void driverPayBill(){

		Object testingData[][] = {
				
				{"manager1",1381 , null },				
				{"manager1", 999999, NullPointerException.class },
				{"manager2", 1381, IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++) {
			payBillTest((String) testingData[i][0], (int) testingData[i][1],
					(Class<?>) testingData[i][2]);
		}
	}
}
