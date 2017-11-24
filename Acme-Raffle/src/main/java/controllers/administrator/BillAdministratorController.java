
package controllers.administrator;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BillService;
import services.ConfigurationService;
import services.RaffleService;
import controllers.AbstractController;
import domain.Configuration;
import domain.Raffle;

@Controller
@RequestMapping("/bill/administrator/")
public class BillAdministratorController extends AbstractController {

	public BillAdministratorController() {
		super();
	}


	@Autowired
	private RaffleService			raffleService;

	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private BillService				billService;


	@RequestMapping(value = "generate", method = RequestMethod.GET)
	public ModelAndView generate() {
		ModelAndView resul;
		final Configuration configuration = this.configurationService.get();

		final Date d = new Date();
		configuration.setMonth(d.getMonth() + 1);
		configuration.setYear(d.getYear() + 1900);

		resul = this.createGenerateModelAndView(configuration, null);

		return resul;
	}

	@RequestMapping(value = "generate", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Configuration configuration, final BindingResult bindingResult) {
		ModelAndView resul;

		if (bindingResult.hasErrors())
			resul = this.createGenerateModelAndView(configuration, null);
		else
			try {

				this.check(configuration, bindingResult);

				this.billService.generateBills(configuration);
				resul = new ModelAndView("redirect:/welcome/index.do");
				resul.addObject("message", "ok.bill");

			} catch (final Throwable oops) {
				resul = this.createGenerateModelAndView(configuration, "bill.commit.error");
			}

		return resul;
	}

	private void check(final Configuration configuration, final BindingResult bindingResult) {
		// TODO Auto-generated method stub

		final String string = String.format("%02d", configuration.getMonth()) + "/" + configuration.getYear();

		//	Comprobamos que es una fecha válida

		final Date date = new Date();

		final int yearNow = date.getYear() + 1900;

		if (configuration.getYear() > yearNow) {
			bindingResult.rejectValue("year", "year.error", "invalid");
			throw new IllegalArgumentException();
		}

		//	Comprobamos que la fecha no está en el histórico

		for (final String historicString : configuration.getHistoric()) {

			final String[] part = historicString.split("/");
			final int month = new Integer(part[0]);
			final int year = new Integer(part[1]);

			final Date dateHistoric = new Date(year - 1900, month - 1, 1, 0, 0, 0);
			final Date dateForm = new Date(configuration.getYear() - 1900, configuration.getMonth() - 1, 1, 0, 0, 0);

			if (dateForm.equals(dateHistoric) || dateForm.before(dateHistoric)) {
				bindingResult.rejectValue("month", "year.errorEs", "invalid");
				bindingResult.rejectValue("year", "year.errorEs", "invalid");
				throw new IllegalArgumentException();
			}
		}

		//	Comprobamos que hay rifas para dicha fecha generar

		final Date moment = new Date();
		moment.setYear(configuration.getYear() - 1900);
		moment.setMonth(configuration.getMonth() - 1);
		moment.setDate(1);
		moment.setHours(0);
		moment.setMinutes(0);
		moment.setSeconds(0);

		final Collection<Raffle> raffles = this.raffleService.findAllByMoment(moment);
		if (raffles == null || raffles.isEmpty()) {
			bindingResult.rejectValue("year", "no.hay", "invalid");
			bindingResult.rejectValue("month", "no.hay", "invalid");
			throw new IllegalArgumentException();
		}
	}
	private ModelAndView createGenerateModelAndView(final Configuration configuration, final String message) {
		// TODO Auto-generated method stub

		final ModelAndView resul = new ModelAndView("bill/generate");
		resul.addObject("actionParam", "bill/administrator/generate.do");
		resul.addObject("configuration", configuration);
		resul.addObject("message", message);

		return resul;
	}
}
