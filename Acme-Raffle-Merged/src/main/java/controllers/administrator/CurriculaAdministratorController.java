
package controllers.administrator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Auditor;
import domain.Curricula;
import services.AuditorService;
import services.CurriculaService;

@RequestMapping("/curricula")
@Controller
public class CurriculaAdministratorController {

	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private AuditorService		auditorService;

	private int					curriculaID;


	@RequestMapping(value = "/auditor/administrator/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final List<Curricula> curricula = this.curriculaService.findAll();

		result = new ModelAndView("curricula/list");
		result.addObject("requestURI", "curricula/auditor/administrator/list.do");
		result.addObject("curricula", curricula);

		return result;

	}

	@RequestMapping(value = "/educationrecord/auditor/administrator/list", method = RequestMethod.GET)
	public ModelAndView listEducation(@RequestParam final int q) {
		ModelAndView result;

		result = new ModelAndView("educationrecord/list");
		result.addObject("requestURI", "educationrecord/auditor/administrator/list.do");
		result.addObject("educationrecord", this.curriculaService.findOne(q).getEducationsRecords());

		return result;

	}

	@RequestMapping(value = "/workrecord/auditor/administrator/list", method = RequestMethod.GET)
	public ModelAndView listworkrecord(@RequestParam final int q) {
		ModelAndView result;

		result = new ModelAndView("workrecord/list");

		result.addObject("requestURI", "workrecord/auditor/administrator/list.do");
		result.addObject("workrecord", this.curriculaService.findOne(q).getWorkRecords());
		this.curriculaID = q;

		return result;

	}

	@RequestMapping(value = "/workrecord/auditor/auditregister/view.do", method = RequestMethod.GET)
	public ModelAndView listSpeciality() {
		ModelAndView result;

		result = new ModelAndView("workrecord/view");

		final Curricula curricula = this.curriculaService.findOne(this.curriculaID);

		Auditor auditor = new Auditor();

		for (final Auditor a : this.auditorService.findAll())
			if (a.getCurricula().equals(curricula)) {
				auditor = a;
				break;
			}

		result.addObject("report", auditor.getReports());

		return result;

	}

	@RequestMapping(value = "/speciality/auditor/administrator/list", method = RequestMethod.GET)
	public ModelAndView viewaudit(@RequestParam final int q) {
		ModelAndView result;

		result = new ModelAndView("speciality/list");
		result.addObject("requestURI", "speciality/auditor/administrator/list.do");
		result.addObject("speciality", this.curriculaService.findOne(q).getSpecialities());

		return result;

	}

}
