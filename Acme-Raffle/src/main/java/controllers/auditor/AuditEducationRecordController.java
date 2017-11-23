
package controllers.auditor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Auditor;
import domain.EducationRecord;
import security.LoginService;
import services.EducationRecordService;

@Controller
@RequestMapping("/educationrecord/auditor")
public class AuditEducationRecordController {

	@Autowired
	private EducationRecordService	educationRecordService;
	@Autowired
	private LoginService			loginService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

		result = new ModelAndView("educationrecord/list");
		result.addObject("requestURI", "educationrecord/auditor/list.do");

		result.addObject("educationrecord", auditor.getCurricula().getEducationsRecords());

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int q) {
		ModelAndView result;
		EducationRecord educationrecord = educationRecordService.findOne(q);
		result = createEditModelAndView(educationrecord, null);

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		EducationRecord educationrecord = new EducationRecord();

		result = createNewModelAndView(educationrecord, null);

		return result;

	}

	@RequestMapping(value = "/saveCreate", method = RequestMethod.POST)
	public ModelAndView saveCreate(@Valid EducationRecord educationrecord, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {

			result = createNewModelAndView(educationrecord, null);
		} else

		{
			try {

				educationRecordService.save(educationrecord);
				result = new ModelAndView("redirect:/educationrecord/auditor/list.do");
			} catch (Throwable e) {

				result = createNewModelAndView(educationrecord, "commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid EducationRecord educationrecord, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {

			result = createEditModelAndView(educationrecord, null);
		} else

		{
			try {

				educationRecordService.save(educationrecord);
				result = new ModelAndView("redirect:/educationrecord/auditor/list.do");
			} catch (Throwable e) {
				e.printStackTrace();

				result = createEditModelAndView(educationrecord, "commit.error");
			}
		}

		return result;
	}

	protected ModelAndView createNewModelAndView(EducationRecord educationrecord, String message) {
		ModelAndView result;
		result = new ModelAndView("educationrecord/create");
		result.addObject("educationrecord", educationrecord);
		result.addObject("message", message);
		return result;
	}

	protected ModelAndView createEditModelAndView(EducationRecord educationrecord, String message) {
		ModelAndView result = new ModelAndView("educationrecord/edit");

		result.addObject("educationrecord", educationrecord);
		result.addObject("message", message);

		return result;
	}

}
