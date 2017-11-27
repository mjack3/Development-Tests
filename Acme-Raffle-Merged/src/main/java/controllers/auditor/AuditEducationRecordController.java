
package controllers.auditor;

import java.util.Date;

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
		final Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

		result = new ModelAndView("educationrecord/list");
		result.addObject("requestURI", "educationrecord/auditor/list.do");

		result.addObject("educationrecord", auditor.getCurricula().getEducationsRecords());

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int q) {
		ModelAndView result;
		try {
			final EducationRecord educationRecord = this.educationRecordService.findOne(q);
			result = this.createEditModelAndView(educationRecord, null);
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final EducationRecord educationRecord = this.educationRecordService.create();

		result = this.createCreateModelAndView(educationRecord, null);

		return result;

	}

	@RequestMapping(value = "/saveCreate", method = RequestMethod.POST)
	public ModelAndView saveCreate(@Valid final EducationRecord educationRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createCreateModelAndView(educationRecord, null);
		else
			try {
				if (educationRecord.getEndDate() != null) {
					if (educationRecord.getEndDate().before(educationRecord.getStartDate())) {
						binding.rejectValue("endDate", "error.date", "error");
						throw new IllegalArgumentException();

					}
					if (educationRecord.getEndDate().after(new Date())) {
						binding.rejectValue("endDate", "error.date.future", "error");
						throw new IllegalArgumentException();

					}
				}

				this.educationRecordService.save(educationRecord);
				result = new ModelAndView("redirect:/educationrecord/auditor/list.do");
			} catch (final Throwable e) {
				e.printStackTrace();
				result = this.createCreateModelAndView(educationRecord, "commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid final EducationRecord educationRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.getDefaultMessage());

			result = this.createEditModelAndView(educationRecord, null);
		} else
			try {
				if (educationRecord.getEndDate() != null) {
					if (educationRecord.getEndDate().before(educationRecord.getStartDate())) {
						binding.rejectValue("endDate", "error.date", "error");
						throw new IllegalArgumentException();

					}
					if (educationRecord.getEndDate().after(new Date())) {
						binding.rejectValue("endDate", "error.date.future", "error");
						throw new IllegalArgumentException();

					}
				}
				this.educationRecordService.save(educationRecord);
				result = new ModelAndView("redirect:/educationrecord/auditor/list.do");
			} catch (final Throwable e) {
				e.printStackTrace();

				result = this.createEditModelAndView(educationRecord, "commit.error");
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
