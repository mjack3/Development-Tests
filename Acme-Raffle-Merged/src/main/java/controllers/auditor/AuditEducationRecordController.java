
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
		EducationRecord educationRecord = educationRecordService.findOne(q);
		result = createEditModelAndView(educationRecord, null);

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		EducationRecord educationRecord = educationRecordService.create();

		result = createCreateModelAndView(educationRecord, null);

		return result;

	}

	@RequestMapping(value = "/saveCreate", method = RequestMethod.POST)
	public ModelAndView saveCreate(@Valid EducationRecord educationRecord, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {

			result = createCreateModelAndView(educationRecord, null);
		} else

		{
			try {

				if (educationRecord.getEndDate().before(educationRecord.getStartDate())) {
					binding.rejectValue("endDate", "error.date", "error");
					throw new IllegalArgumentException();

				}

				educationRecordService.save(educationRecord);
				result = new ModelAndView("redirect:/educationrecord/auditor/list.do");
			} catch (Throwable e) {
				e.printStackTrace();
				result = createCreateModelAndView(educationRecord, "commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid EducationRecord educationRecord, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			for (ObjectError e : binding.getAllErrors()) {
				System.out.println(e.getDefaultMessage());
			}

			result = createEditModelAndView(educationRecord, null);
		} else

		{
			try {
				if (educationRecord.getEndDate().before(educationRecord.getStartDate())) {
					binding.rejectValue("endDate", "error.date", "error");
					throw new IllegalArgumentException();

				}

				educationRecordService.save(educationRecord);
				result = new ModelAndView("redirect:/educationrecord/auditor/list.do");
			} catch (Throwable e) {
				e.printStackTrace();

				result = createEditModelAndView(educationRecord, "commit.error");
			}
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {

		ModelAndView result;
		result = this.createEditModelAndView(educationRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String message) {

		ModelAndView result;

		result = new ModelAndView("educationrecord/edit");
		result.addObject("educationRecord", educationRecord);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createCreateModelAndView(final EducationRecord educationRecord) {

		ModelAndView result;
		result = this.createEditModelAndView(educationRecord, null);
		return result;
	}

	protected ModelAndView createCreateModelAndView(final EducationRecord educationRecord, final String message) {

		ModelAndView result;

		result = new ModelAndView("educationrecord/create");
		result.addObject("educationRecord", educationRecord);
		result.addObject("message", message);

		return result;
	}

}
