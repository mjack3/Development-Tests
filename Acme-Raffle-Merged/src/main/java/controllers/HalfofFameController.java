
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
import services.ParticipationService;
import services.PrizeService;
import services.RaffleService;
import services.UserService;

@Controller
@RequestMapping("/prize")
public class HalfofFameController {

	@Autowired
	private UserService				userservice;
	@Autowired
	private PrizeService			prizeService;
	@Autowired
	private RaffleService			raffleService;
	@Autowired
	private ParticipationService	participationService;


	@RequestMapping(value = "/halfoffame", method = RequestMethod.GET)
	public ModelAndView halfofame() {
		ModelAndView result;

		result = new ModelAndView("prize/halfoffame");

		final List<User> user = new ArrayList<User>();
		final List<Prize> prize = new ArrayList<Prize>();

		for (final Participation p : this.participationService.findAll())
			for (final Prize pr : p.getRaffle().getPrizes())
				for (final Code co : pr.getCodes())
					if (co.getCode().compareTo(p.getUsedCode()) <= 1 && co.getIsWinner()) {
						if (!user.contains(p.getUser()))
							user.add(p.getUser());
						if (!prize.contains(pr))
							prize.add(pr);
					}

		final List<String> codeUsed = new ArrayList<String>();
		final List<User> user075 = new ArrayList<User>();
		for (final Participation p : this.participationService.findAll())
			codeUsed.add(p.getUsedCode());
		for (final User us : this.userservice.findAll())
			if (codeUsed.size() > 0)
				if (us.getParticipations().size() / codeUsed.size() >= 0.75)
					user075.add(us);

		result.addObject("user075", user075);

		result.addObject("user", user);
		result.addObject("prize", prize);

		return result;

	}

}
