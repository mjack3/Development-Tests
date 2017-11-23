
package controllers.auditor;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Auditor;
import domain.Curricula;
import security.LoginService;
import services.CurriculaService;

@Controller
@RequestMapping("/curricula/auditor")
public class AuditCurriculaController {

	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private LoginService		loginService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		List<Curricula> curricula = new ArrayList<Curricula>();

		result = new ModelAndView("curricula/list");
		result.addObject("requestURI", "curricula/auditor/list.do");
		result.addObject("auditor", auditor);

		if (auditor.getCurricula() == null) {
			result.addObject("curricula", curricula);
		} else {
			result.addObject("curricula", auditor.getCurricula());
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int q) {
		ModelAndView result;

		result = createEditModelAndView(curriculaService.findOne(q), null);

		return result;

	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid Curricula curricula, BindingResult binding) {
		ModelAndView result;

		System.out.println(curricula.getName());

		curriculaService.save(curricula);
		result = new ModelAndView("redirect:/curricula/auditor/list.do");

		return result;
	}

	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public ModelAndView generateCurricula() {

		ModelAndView result;
		Curricula curricula = curriculaService.create();
		try {

			curriculaService.generate(curricula);

			result = new ModelAndView("redirect:/curricula/auditor/list.do");
		} catch (Throwable e) {
			result = new ModelAndView("redirect:/curricula/auditor/list.do");
			result.addObject("message", "commit.error");

		}
		return result;

	}

	protected ModelAndView createEditModelAndView(final Curricula curricula) {

		ModelAndView result;
		result = this.createEditModelAndView(curricula, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Curricula curricula, final String message) {

		ModelAndView result;

		result = new ModelAndView("curricula/edit");
		result.addObject("curricula", curricula);
		result.addObject("message", message);

		return result;
	}

}
