
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
import domain.WorkRecord;
import security.LoginService;
import services.WorkRecordService;

@Controller
@RequestMapping("/workrecord/auditor")
public class AuditWorkRecordController {

	@Autowired
	private WorkRecordService	workRecordService;
	@Autowired
	private LoginService		loginService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

		result = new ModelAndView("workrecord/list");
		result.addObject("requestURI", "workrecord/auditor/list.do");

		result.addObject("workrecord", auditor.getCurricula().getWorkRecords());

		return result;

	}

	@RequestMapping(value = "/auditregister/view", method = RequestMethod.GET)
	public ModelAndView listRegister() {
		ModelAndView result;
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

		result = new ModelAndView("workrecord/view");

		result.addObject("report", auditor.getReports());

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int q) {
		ModelAndView result;
		WorkRecord workRecord = workRecordService.findOne(q);
		result = createEditModelAndView(workRecord, null);

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		WorkRecord workRecord = new WorkRecord();

		result = createNewModelAndView(workRecord, null);

		return result;

	}

	@RequestMapping(value = "/saveCreate", method = RequestMethod.POST)
	public ModelAndView saveCreate(@Valid WorkRecord workRecord, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {

			result = createNewModelAndView(workRecord, null);
		} else

		{
			try {
				if (workRecord.getEndDate().before(workRecord.getStartDate())) {
					binding.rejectValue("endDate", "error.date", "error");
					throw new IllegalArgumentException();

				}
				workRecordService.save(workRecord);
				result = new ModelAndView("redirect:/workrecord/auditor/list.do");
			} catch (Throwable e) {

				result = createNewModelAndView(workRecord, "commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid WorkRecord workRecord, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {

			result = createEditModelAndView(workRecord, null);
		} else

		{
			try {
				if (workRecord.getEndDate().before(workRecord.getStartDate())) {
					binding.rejectValue("endDate", "error.date", "error");
					throw new IllegalArgumentException();

				}

				workRecordService.save(workRecord);
				result = new ModelAndView("redirect:/workrecord/auditor/list.do");
			} catch (Throwable e) {
				e.printStackTrace();

				result = createEditModelAndView(workRecord, "commit.error");
			}
		}

		return result;
	}

	protected ModelAndView createNewModelAndView(WorkRecord workRecord, String message) {
		ModelAndView result;
		result = new ModelAndView("workrecord/create");
		result.addObject("workRecord", workRecord);
		result.addObject("message", message);
		return result;
	}

	protected ModelAndView createEditModelAndView(WorkRecord workRecord, String message) {
		ModelAndView result = new ModelAndView("workrecord/edit");

		result.addObject("workRecord", workRecord);
		result.addObject("message", message);

		return result;
	}

}
