/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BillService;
import services.ManagerService;
import domain.Manager;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private BillService		billService;

	@Autowired
	private ManagerService	managerService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");

		result.addObject("name", name);

		result.addObject("moment", moment);

		//2.0

		if (LoginService.hasRole("MANAGER")) {
			final Manager manager = this.managerService.findPrincipal();
			final boolean sw = (manager.getCreditCard() == null);
			result.addObject("sw", sw);
		}

		return result;
	}

	@RequestMapping(value = "/cookies")
	public ModelAndView cookies() {
		ModelAndView result;

		result = new ModelAndView("legislation/cookies");

		return result;
	}

	@RequestMapping(value = "/lopd")
	public ModelAndView lopd() {
		ModelAndView result;

		result = new ModelAndView("legislation/lopd");

		return result;
	}

	@RequestMapping(value = "/lssi")
	public ModelAndView lssi() {
		ModelAndView result;

		result = new ModelAndView("legislation/lssi");

		return result;
	}
}
