
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.SocialIdentity;
import security.LoginService;
import services.SocialIdentityService;

@Controller
@RequestMapping("/socialidentity/actor")
public class ActorSocialIdentityController {

	@Autowired
	private SocialIdentityService	socialIdentityService;

	@Autowired
	private LoginService			loginService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		result = new ModelAndView("socialIdentity/list");
		final Actor actor = this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		final Collection<SocialIdentity> socialIdentity = actor.getSocialIdentities();
		result.addObject("socialIdentity", socialIdentity);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = this.createNewModelAndView(this.socialIdentityService.create(), null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int q) {
		ModelAndView result;
		try {
			final Actor actor = this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
			if (actor.getSocialIdentities().contains(this.socialIdentityService.findOne(q)))
				result = this.createEditModelAndView(this.socialIdentityService.findOne(q), null);
			else
				result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int q) {
		ModelAndView result;
		final SocialIdentity socialIdentity = this.socialIdentityService.findOne(q);
		final Actor actor = this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		try {
			if (actor.getSocialIdentities().contains(socialIdentity)) {
				this.socialIdentityService.delete(socialIdentity);
				result = new ModelAndView("redirect:/socialidentity/actor/list.do");

			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveCreate(@Valid final SocialIdentity socialIdentity, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createNewModelAndView(socialIdentity, null);
		else
			try {
				this.socialIdentityService.save(socialIdentity);

				result = new ModelAndView("redirect:/socialidentity/actor/list.do");
			} catch (final Exception e) {
				result = this.createNewModelAndView(socialIdentity, "commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid final SocialIdentity socialIdentity, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(socialIdentity, null);
		else
			try {
				this.socialIdentityService.save(socialIdentity);

				result = new ModelAndView("redirect:/socialidentity/actor/list.do");
			} catch (final Exception e) {
				result = this.createEditModelAndView(socialIdentity, "commit.error");
			}

		return result;
	}

	protected ModelAndView createNewModelAndView(final SocialIdentity socialIdentity, final String message) {
		ModelAndView result;
		result = new ModelAndView("socialIdentity/create");
		result.addObject("socialIdentity", socialIdentity);
		result.addObject("message", message);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialIdentity socialIdentity, final String message) {
		final ModelAndView result = new ModelAndView("socialIdentity/edit");

		result.addObject("socialIdentity", socialIdentity);
		result.addObject("message", message);

		return result;
	}

}
