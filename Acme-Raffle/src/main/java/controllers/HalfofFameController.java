
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Code;
import domain.Participation;
import domain.Prize;
import domain.User;
import services.PrizeService;
import services.UserService;

@Controller
@RequestMapping("/prize")
public class HalfofFameController {

	@Autowired
	private UserService		userservice;
	@Autowired
	private PrizeService	prizeService;


	@RequestMapping(value = "/halfoffame", method = RequestMethod.GET)
	public ModelAndView halfofame() {
		ModelAndView result;

		result = new ModelAndView("prize/halfoffame");

		List<User> users = new ArrayList<User>();
		List<Prize> prizes = new ArrayList<Prize>();
		for (User user : userservice.findAll()) {
			for (Participation p : user.getParticipations()) {
				for (Prize pr : prizeService.findAll())
					if (pr.getCodes().contains(p.getUsedCode())) {
						for (Code codes : pr.getCodes()) {
							if (codes.getIsWinner()) {
								prizes.add(codes.getPrize());
								users.add(user);
							}
						}
					}
			}
		}
		result.addObject("user", users);
		result.addObject("prize", prizes);
		result.addObject("user075", userservice.users075());

		return result;

	}

}
