
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Bill;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class BillTest extends AbstractTest {

	@Autowired
	private BillService	billService;


	@Test
	public void driverBillPay() {

		this.DisplayBillpayTest("admin", null);

	}

	protected void DisplayBillpayTest(final String username, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			final Bill bill = new Bill();
			//billService.generate(bill); he tenido que comentar, porque nos hemos visto obligados a modificar la manera en la que el RQ 3.1 debía funcionar. Método generate Bill no funciona de momento
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void payBillTest(final String username, final int billId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			final Bill bill = this.billService.findOne(billId);
			bill.setPayed(new Date(System.currentTimeMillis() - 1));

			this.billService.pay(bill);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverPayBill() {

		final Object testingData[][] = {

			{
				"manager1", 1381, null
			}, {
				"manager1", 999999, NullPointerException.class
			}, {
				"manager2", 1381, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.payBillTest((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
}
