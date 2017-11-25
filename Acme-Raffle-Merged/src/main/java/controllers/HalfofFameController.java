
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


//	@RequestMapping(value = "/halfoffame", method = RequestMethod.GET)
//	public ModelAndView halfofame() {
//		ModelAndView result;
//
//		result = new ModelAndView("prize/halfoffame");
//
//		List<User> user = new ArrayList<User>();
//		List<Prize> prize = new ArrayList<Prize>();
//		for (User u : userservice.findAll()) {
//			for (Participation p : u.getParticipations()) {
//				for (Prize pr : prizeService.findAll())
//					if (pr.getCodes().contains(p.getUsedCode())) {
//						for (Code codes : pr.getCodes()) {
//							if (codes.getIsWinner()) {
//								prize.add(codes.getPrize());
//								user.add(u);
//							}
//						}
//					}
//			}
//		}
//		result.addObject("user", user);
//		result.addObject("prize", prize);
//		result.addObject("user075", userservice.users075());
//
//		return result;
//
//	}

}
