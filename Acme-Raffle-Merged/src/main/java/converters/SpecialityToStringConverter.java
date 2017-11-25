
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Speciality;

@Component
@Transactional
public class SpecialityToStringConverter implements Converter<Speciality, String> {

	@Override
	public String convert(final Speciality speciality) {
		String res;

		if (speciality == null)
			res = null;
		else
			res = String.valueOf(speciality.getId());

		return res;
	}

}
