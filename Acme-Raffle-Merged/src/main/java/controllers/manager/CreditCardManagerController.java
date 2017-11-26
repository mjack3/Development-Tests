
package controllers.manager;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.CreditCard;
import domain.Manager;
import services.CreditCardService;
import services.ManagerService;

@RequestMapping("/creditcard/manager/")
@Controller
public class CreditCardManagerController {

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ManagerService		managerService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final CreditCard creditCard = new CreditCard();

		final ModelAndView resul = this.createEditModelAndViewBill(creditCard, null);

		return resul;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView resul = new ModelAndView("creditcard/display");
		final Manager manager = this.managerService.findPrincipal();
		Assert.notNull(manager.getCreditCard());
		final CreditCard creditCard = manager.getCreditCard();
		resul.addObject("creditCard", creditCard);
		return resul;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final CreditCard creditCard, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.toString());

			result = this.createEditModelAndViewBill(creditCard, null);
		} else
			try {
				//Probar que caduca en 7 dias
				final Date d1 = new Date();
				System.out.println(d1.getYear());
				System.out.println((2000 + creditCard.getYear()) - 1900);
				System.out.println(d1.getMonth() - creditCard.getMonth() <= 1);
				System.out.println(d1.getDate() + 7 > 31);
				if (creditCard.getMonth() < d1.getMonth() && (2000 + creditCard.getYear() - 1900) - d1.getYear() == 0) {
					binding.rejectValue("month", "creditcard.month.novalid", "error");
					throw new IllegalArgumentException();
				}
				if ((2000 + creditCard.getYear() - 1900) - d1.getYear() == 0 && d1.getMonth() - creditCard.getMonth() <= 1 && d1.getDate() + 7 > 31) {
					binding.rejectValue("year", "creditcard.deadlineError", "error");
					throw new IllegalArgumentException();
				}

				this.creditCardService.save(creditCard);

				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createEditModelAndViewBill(creditCard, "actor.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndViewBill(final CreditCard creditCard, final String message) {

		ModelAndView result;

		result = new ModelAndView("creditcard/create");

		result.addObject("creditCard", creditCard);

		result.addObject("message", message);
		return result;
	}

}
