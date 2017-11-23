
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
		WorkRecord workrecord = workRecordService.findOne(q);
		result = createEditModelAndView(workrecord, null);

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		WorkRecord workrecord = new WorkRecord();

		result = createNewModelAndView(workrecord, null);

		return result;

	}

	@RequestMapping(value = "/saveCreate", method = RequestMethod.POST)
	public ModelAndView saveCreate(@Valid WorkRecord workrecord, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {

			result = createNewModelAndView(workrecord, null);
		} else

		{
			try {

				workRecordService.save(workrecord);
				result = new ModelAndView("redirect:/workrecord/auditor/list.do");
			} catch (Throwable e) {

				result = createNewModelAndView(workrecord, "commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid WorkRecord workrecord, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {

			result = createEditModelAndView(workrecord, null);
		} else

		{
			try {

				workRecordService.save(workrecord);
				result = new ModelAndView("redirect:/workrecord/auditor/list.do");
			} catch (Throwable e) {
				e.printStackTrace();

				result = createEditModelAndView(workrecord, "commit.error");
			}
		}

		return result;
	}

	protected ModelAndView createNewModelAndView(WorkRecord workrecord, String message) {
		ModelAndView result;
		result = new ModelAndView("workrecord/create");
		result.addObject("workrecord", workrecord);
		result.addObject("message", message);
		return result;
	}

	protected ModelAndView createEditModelAndView(WorkRecord workrecord, String message) {
		ModelAndView result = new ModelAndView("workrecord/edit");

		result.addObject("workrecord", workrecord);
		result.addObject("message", message);

		return result;
	}

}
