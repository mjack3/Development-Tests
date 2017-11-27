
package controllers.auditor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Auditor;
import domain.Speciality;
import security.LoginService;
import services.AuditorService;
import services.SpecialityService;

@Controller
@RequestMapping("/speciality/auditor")
public class AuditSpecialityController {

	@Autowired
	private SpecialityService	specialityService;
	@Autowired
	private LoginService		loginService;
	@Autowired
	private AuditorService		auditorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

		result = new ModelAndView("speciality/list");
		result.addObject("requestURI", "speciality/auditor/list.do");

		result.addObject("speciality", auditor.getCurricula().getSpecialities());

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int q) {
		ModelAndView result;
		try {
			final Speciality speciality = this.specialityService.findOne(q);
			final Auditor auditor = this.auditorService.findOneUserAccount(LoginService.getPrincipal().getId());
			Assert.isTrue(auditor.getCurricula().getSpecialities().contains(speciality));
			result = this.createEditModelAndView(speciality, null);
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Speciality speciality = this.specialityService.create();

		result = this.createCreateModelAndView(speciality, null);

		return result;

	}

	@RequestMapping(value = "/saveCreate", method = RequestMethod.POST)
	public ModelAndView saveCreate(@Valid final Speciality speciality, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createCreateModelAndView(speciality, null);
		else
			try {

				this.specialityService.save(speciality);
				result = new ModelAndView("redirect:/speciality/auditor/list.do");
			} catch (final Throwable e) {
				e.printStackTrace();
				result = this.createCreateModelAndView(speciality, "commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid final Speciality speciality, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.getDefaultMessage());

			result = this.createEditModelAndView(speciality, null);
		} else
			try {

				this.specialityService.save(speciality);
				result = new ModelAndView("redirect:/speciality/auditor/list.do");
			} catch (final Throwable e) {
				e.printStackTrace();

				result = this.createEditModelAndView(speciality, "commit.error");
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
