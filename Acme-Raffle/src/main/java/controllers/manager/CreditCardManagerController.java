
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.ManagerService;
import domain.CreditCard;
import domain.Manager;

@RequestMapping("/creditcard/manager/")
@Controller
public class CreditCardManagerController {

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ManagerService		managerService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView resul = new ModelAndView("creditcard/create");
		final CreditCard creditCard = new CreditCard();

		resul.addObject("creditCard", creditCard);

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
		System.out.println(creditCard.getBrandName());
		if (binding.hasErrors())
			result = this.createEditModelAndViewBill(creditCard, null);
		else
			try {

				this.creditCardService.save(creditCard);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable e) {

				result = this.createEditModelAndViewBill(creditCard, "commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndViewBill(final CreditCard creditCard) {

		ModelAndView result;
		result = this.createEditModelAndViewBill(creditCard, null);
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
