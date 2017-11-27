
package controllers.auditor;

import java.util.ArrayList;
import java.util.List;

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
		final Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		final List<Curricula> curricula = new ArrayList<Curricula>();

		result = new ModelAndView("curricula/list");
		result.addObject("requestURI", "curricula/auditor/list.do");
		result.addObject("auditor", auditor);

		if (auditor.getCurricula() == null)
			result.addObject("curricula", curricula);
		else
			result.addObject("curricula", auditor.getCurricula());

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int q) {
		ModelAndView result;
		try {
			final Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
			Assert.isTrue(auditor.getCurricula().equals(this.curriculaService.findOne(q)));
			result = this.createEditModelAndView(this.curriculaService.findOne(q), null);
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;

	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Curricula curricula, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.toString());

			result = this.createEditModelAndView(curricula, null);
		} else
			try {

				this.curriculaService.save(curricula);

				result = new ModelAndView("redirect:/curricula/auditor/list.do");

			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createEditModelAndView(curricula, "actor.commit.error");
			}
		return result;

	}

	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public ModelAndView generateCurricula() {

		ModelAndView result;
		final Curricula curricula = this.curriculaService.create();
		try {

			this.curriculaService.generate(curricula);

			result = new ModelAndView("redirect:/curricula/auditor/list.do");
		} catch (final Throwable e) {
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
