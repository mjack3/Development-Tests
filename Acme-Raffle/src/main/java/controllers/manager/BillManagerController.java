
package controllers.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BillService;
import domain.Bill;
import domain.Manager;
import domain.Raffle;

@RequestMapping("/bill/manager/")
@Controller
public class BillManagerController {

	@Autowired
	private BillService		billService;

	@Autowired
	private LoginService	loginService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("bill/list");
		final Manager manager = (Manager) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		final List<Bill> bills = new ArrayList<Bill>();
		for (final Raffle r : manager.getRaffles())
			bills.addAll(r.getBills());
		result.addObject("requestURI", "/bill/manager/list.do");
		result.addObject("bill", bills);
		result.addObject("creditCard", manager.getCreditCard());
		return result;
	}

	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView pay(@RequestParam final int q) {

		ModelAndView result;
		final Bill bill = this.billService.findOne(q);
		bill.setPayed(new Date(System.currentTimeMillis() - 1));
		try {
			this.billService.pay(bill);

			result = new ModelAndView("redirect:/bill/manager/list.do");
		} catch (final Throwable th) {
			result = new ModelAndView("redirect:/bill/manager/list.do");
			result.addObject("message", "commit.error");
		}

		return result;

	}

}
