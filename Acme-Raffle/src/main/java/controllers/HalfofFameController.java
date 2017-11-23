
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;

@Controller
@RequestMapping("/prize")
public class HalfofFameController {

	@Autowired
	private UserService userservice;


	@RequestMapping(value = "/halfoffame", method = RequestMethod.GET)
	public ModelAndView halfofame() {
		ModelAndView result;

		result = new ModelAndView("prize/halfoffame");

		result.addObject("user", userservice.findAll());
		result.addObject("user075", userservice.users075());

		return result;

	}

}
