
package controllers.administrator;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AdministratorService;
import services.BillService;
import domain.Administrator;
import domain.Bill;

@RequestMapping("/administrator")
@Controller
public class AdministratorController {

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private BillService				billService;
	@Autowired
	private LoginService			loginService;


	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView res;

		res = new ModelAndView("administrator/dashboard");

		res.addObject("dashboard", this.administratorService.dashboard());

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int userAccountID) {
		ModelAndView result;
		Administrator admin;

		admin = this.administratorService.findActorByUsername(userAccountID);

		result = this.createEditModelAndView(admin);

		return result;
	}

	@RequestMapping(value = "/save-administrator", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@Valid final Administrator admin, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.toString());
			result = this.createEditModelAndView(admin, "admin.commit.error");
		} else
			try {
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createEditModelAndView(admin, "admin.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator admin) {

		ModelAndView result;
		result = this.createEditModelAndView(admin, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator admin, final String message) {

		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", admin);
		result.addObject("message", message);

		System.out.println(message);

		return result;
	}

	@RequestMapping(value = "/bill/list", method = RequestMethod.GET)
	public ModelAndView listBill() {

		ModelAndView res;
		res = new ModelAndView("bill/list");
		final List<Bill> bill = this.billService.findAll();

		res.addObject("requestURI", "/administrator/bill/list.do");
		res.addObject("bill", bill);
		return res;

	}

	protected ModelAndView createEditModelAndViewBill(final Bill bill) {

		ModelAndView result;
		result = this.createEditModelAndViewBill(bill, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewBill(final Bill bill, final String message) {

		ModelAndView result;

		result = new ModelAndView("bill/generate");
		result.addObject("bill", bill);
		result.addObject("message", message);

		return result;
	}

}
