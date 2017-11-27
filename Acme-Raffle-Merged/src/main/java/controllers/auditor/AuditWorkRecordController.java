
package controllers.auditor;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Auditor;
import domain.WorkRecord;
import security.LoginService;
import services.AuditorService;
import services.WorkRecordService;

@Controller
@RequestMapping("/workrecord/auditor")
public class AuditWorkRecordController {

	@Autowired
	private WorkRecordService	workRecordService;
	@Autowired
	private LoginService		loginService;
	@Autowired
	private AuditorService		auditorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

		result = new ModelAndView("workrecord/list");
		result.addObject("requestURI", "workrecord/auditor/list.do");

		result.addObject("workrecord", auditor.getCurricula().getWorkRecords());

		return result;

	}

	@RequestMapping(value = "/auditregister/view", method = RequestMethod.GET)
	public ModelAndView listRegister() {
		ModelAndView result;
		final Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

		result = new ModelAndView("workrecord/view");

		result.addObject("report", auditor.getReports());

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int q) {
		ModelAndView result;
		try {
			final WorkRecord workRecord = this.workRecordService.findOne(q);
			final Auditor auditor = this.auditorService.findOneUserAccount(LoginService.getPrincipal().getId());
			Assert.isTrue(auditor.getCurricula().getWorkRecords().contains(workRecord));
			result = this.createEditModelAndView(workRecord, null);
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final WorkRecord workRecord = new WorkRecord();

		result = this.createNewModelAndView(workRecord, null);

		return result;

	}

	@RequestMapping(value = "/saveCreate", method = RequestMethod.POST)
	public ModelAndView saveCreate(@Valid final WorkRecord workRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createNewModelAndView(workRecord, null);
		else
			try {

				if (workRecord.getEndDate() != null) {
					if (workRecord.getEndDate().before(workRecord.getStartDate())) {
						binding.rejectValue("endDate", "error.date", "error");
						throw new IllegalArgumentException();

					}

					if (workRecord.getEndDate().after(new Date())) {
						binding.rejectValue("endDate", "error.date.future", "error");
						throw new IllegalArgumentException();

					}
				}

				this.workRecordService.save(workRecord);
				result = new ModelAndView("redirect:/workrecord/auditor/list.do");
			} catch (final Throwable e) {

				result = this.createNewModelAndView(workRecord, "commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid final WorkRecord workRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(workRecord, null);
		else
			try {
				if (workRecord.getEndDate() != null) {
					if (workRecord.getEndDate().before(workRecord.getStartDate())) {
						binding.rejectValue("endDate", "error.date", "error");
						throw new IllegalArgumentException();

					}

					if (workRecord.getEndDate().after(new Date())) {
						binding.rejectValue("endDate", "error.date.future", "error");
						throw new IllegalArgumentException();

					}
				}

				this.workRecordService.save(workRecord);
				result = new ModelAndView("redirect:/workrecord/auditor/list.do");
			} catch (final Throwable e) {
				e.printStackTrace();

				result = this.createEditModelAndView(workRecord, "commit.error");
			}

		return result;
	}

	protected ModelAndView createNewModelAndView(final WorkRecord workRecord, final String message) {
		ModelAndView result;
		result = new ModelAndView("workrecord/create");
		result.addObject("workRecord", workRecord);
		result.addObject("message", message);
		return result;
	}

	protected ModelAndView createEditModelAndView(final WorkRecord workRecord, final String message) {
		final ModelAndView result = new ModelAndView("workrecord/edit");

		result.addObject("workRecord", workRecord);
		result.addObject("message", message);

		return result;
	}

}
