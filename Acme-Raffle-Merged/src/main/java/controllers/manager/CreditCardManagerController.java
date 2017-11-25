
package controllers.manager;

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

		CreditCard creditCard = new CreditCard();

		ModelAndView resul = createEditModelAndViewBill(creditCard, null);

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
	public ModelAndView save(@Valid CreditCard creditCard, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			for (ObjectError e : binding.getAllErrors()) {
				System.out.println(e.toString());
			}

			result = createEditModelAndViewBill(creditCard, null);
		} else {
			try {
				//				Date d1 = new Date();
				//				Date d2 = new Date();
				//				d2.setMonth(creditCard.getMonth() - 1);
				//				d2.setYear((2000 + creditCard.getYear()) - 1900);
				//				d2.setHours(0);
				//				d2.setDate(1);
				//				d2.setMinutes(0);
				//				d2.setSeconds(0);

				creditCardService.save(creditCard);

				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (Throwable th) {
				th.printStackTrace();
				result = createEditModelAndViewBill(creditCard, "actor.commit.error");
			}
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
