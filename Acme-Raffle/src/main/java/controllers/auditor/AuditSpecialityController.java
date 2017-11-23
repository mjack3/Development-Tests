
package controllers.auditor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Auditor;
import domain.Speciality;
import security.LoginService;
import services.SpecialityService;

@Controller
@RequestMapping("/speciality/auditor")
public class AuditSpecialityController {

	@Autowired
	private SpecialityService	specialityService;
	@Autowired
	private LoginService		loginService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

		result = new ModelAndView("speciality/list");
		result.addObject("requestURI", "speciality/auditor/list.do");

		result.addObject("speciality", auditor.getCurricula().getSpecialities());

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int q) {
		ModelAndView result;
		Speciality speciality = specialityService.findOne(q);
		result = createEditModelAndView(speciality, null);

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Speciality speciality = specialityService.create();

		result = createCreateModelAndView(speciality, null);

		return result;

	}

	@RequestMapping(value = "/saveCreate", method = RequestMethod.POST)
	public ModelAndView saveCreate(@Valid Speciality speciality, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {

			result = createCreateModelAndView(speciality, null);
		} else

		{
			try {

				specialityService.save(speciality);
				result = new ModelAndView("redirect:/speciality/auditor/list.do");
			} catch (Throwable e) {
				e.printStackTrace();
				result = createCreateModelAndView(speciality, "commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid Speciality speciality, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			for (ObjectError e : binding.getAllErrors()) {
				System.out.println(e.getDefaultMessage());
			}

			result = createEditModelAndView(speciality, null);
		} else

		{
			try {

				specialityService.save(speciality);
				result = new ModelAndView("redirect:/speciality/auditor/list.do");
			} catch (Throwable e) {
				e.printStackTrace();

				result = createEditModelAndView(speciality, "commit.error");
			}
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Speciality speciality) {

		ModelAndView result;
		result = this.createEditModelAndView(speciality, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Speciality speciality, final String message) {

		ModelAndView result;

		result = new ModelAndView("speciality/edit");
		result.addObject("speciality", speciality);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createCreateModelAndView(final Speciality speciality) {

		ModelAndView result;
		result = this.createEditModelAndView(speciality, null);
		return result;
	}

	protected ModelAndView createCreateModelAndView(final Speciality speciality, final String message) {

		ModelAndView result;

		result = new ModelAndView("speciality/create");
		result.addObject("speciality", speciality);
		result.addObject("message", message);

		return result;
	}

}
