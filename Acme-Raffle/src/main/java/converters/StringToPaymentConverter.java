
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Payment;
import repositories.PaymentRepository;

@Component
@Transactional
public class StringToPaymentConverter implements Converter<String, Payment> {

	@Autowired
	PaymentRepository paymentRepository;


	@Override
	public Payment convert(final String text) {
		Payment res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.paymentRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}

}
