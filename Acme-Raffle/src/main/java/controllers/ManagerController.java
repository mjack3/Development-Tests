
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.CreditCard;
import domain.Manager;
import security.LoginService;
import services.ManagerService;

@Controller
@RequestMapping("/mana")
public class ManagerController extends AbstractController {

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private LoginService	loginService;


	public ManagerController() {
		super();
	}

	@RequestMapping(value = "/administrator/list.do", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result = new ModelAndView("mana/list");

		result.addObject("manager", managerService.listofdebtor());
		return result;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup() {
		final ModelAndView result = new ModelAndView("mana/signup");
		Manager mana = null;
		String url = null;

		mana = this.managerService.create();
		url = "mana/save-mana-create.do";
		result.addObject("manager", mana);

		result.addObject("message", null);
		result.addObject("url", url);

		return result;
	}

	@RequestMapping(value = "/save-mana-create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveManagerCreate(@Valid final Manager mana, final BindingResult binding, final String message) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.signupModelAndView(mana, "mana.commit.error");
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.toString());
		} else
			try {
				this.managerService.save(mana);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.signupModelAndView(mana, "mana.commit.error");
			}
		return result;
	}

	protected ModelAndView signupModelAndView(final Manager mana) {

		ModelAndView result;
		result = this.signupModelAndView(mana, null);
		return result;
	}

	protected ModelAndView signupModelAndView(final Manager mana, final String message) {

		ModelAndView result;

		result = new ModelAndView("mana/signup");
		result.addObject("manager", mana);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int userAccountID) {
		ModelAndView result;
		Manager mana;

		mana = this.managerService.findManagerByUsername(userAccountID);

		result = this.createEditModelAndView(mana);

		return result;
	}

	@RequestMapping(value = "/save-mana", method = RequestMethod.POST, params = "save")
	public ModelAndView saveManager(@Valid final Manager mana, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.toString());
			result = this.createEditModelAndView(mana, "mana.commit.error");
		} else
			try {
				this.managerService.save(mana);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createEditModelAndView(mana, "mana.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager mana) {

		ModelAndView result;
		result = this.createEditModelAndView(mana, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager mana, final String message) {

		ModelAndView result;

		result = new ModelAndView("mana/edit");
		result.addObject("manager", mana);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createCreditCard() {

		ModelAndView result;
		CreditCard creditcard = new CreditCard();

		result = createEditCreditModelAndView(creditcard);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editCreditCard() {

		ModelAndView result;
		Manager mana = (Manager) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		result = createEditCreditModelAndView(mana.getCreditCard());

		return result;

	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreditCard(@Valid CreditCard creditcard, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {

			result = this.createEditCreditModelAndView(creditcard, "mana.commit.error");
		} else
			try {
				Manager mana = (Manager) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
				mana.setCreditCard(creditcard);
				this.managerService.save(mana);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createEditCreditModelAndView(creditcard, "mana.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditCreditModelAndView(final CreditCard creditcard) {

		ModelAndView result;
		result = this.createEditCreditModelAndView(creditcard, null);
		return result;
	}

	protected ModelAndView createEditCreditModelAndView(final CreditCard creditcard, final String message) {

		ModelAndView result;

		result = new ModelAndView("mana/creditcard");
		result.addObject("creditcard", creditcard);
		result.addObject("message", message);
		Manager mana = (Manager) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (mana.getCreditCard() == null) {
			result.addObject("requestURI", "/mana/creditcard/create.do");
		} else {
			result.addObject("requestURI", "/mana/creditcard/edit.do");
		}
		return result;
	}

}
