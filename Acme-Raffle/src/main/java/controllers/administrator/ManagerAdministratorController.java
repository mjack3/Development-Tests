
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ManagerService;
import domain.Manager;

@RequestMapping("/mana/administrator")
@Controller
public class ManagerAdministratorController {

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ManagerService			managerService;


	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView res;

		res = new ModelAndView("mana/list");

		res.addObject("manager", this.managerService.managerDebtor());

		return res;
	}

	@RequestMapping("/view")
	public ModelAndView view() {
		ModelAndView res;

		res = new ModelAndView("mana/view");

		return res;
	}

	@RequestMapping(value = "/desbanned", method = RequestMethod.GET)
	public ModelAndView desbanned(@RequestParam final int q) {
		Assert.notNull(q);
		ModelAndView result;
		final Manager manager = this.managerService.findOne(q);

		try {
			this.administratorService.DisbannedManager(manager);

			result = new ModelAndView("redirect:/mana/administrator/list.do");
		} catch (final Throwable th) {
			result = new ModelAndView("redirect:/mana/administrator/list.do");
			result.addObject("message", "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/banned", method = RequestMethod.GET)
	public ModelAndView pay(@RequestParam final int q) {
		Assert.notNull(q);
		ModelAndView result;
		final Manager manager = this.managerService.findOne(q);

		try {
			this.administratorService.bannedManager(manager);

			result = new ModelAndView("redirect:/mana/administrator/list.do");
		} catch (final Throwable th) {
			result = new ModelAndView("redirect:/mana/administrator/list.do");
			result.addObject("message", "commit.error");
		}

		return result;

	}

}
