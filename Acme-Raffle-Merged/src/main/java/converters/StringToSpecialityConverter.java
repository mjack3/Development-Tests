
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Speciality;
import repositories.SpecialityRepository;

@Component
@Transactional
public class StringToSpecialityConverter implements Converter<String, Speciality> {

	@Autowired
	SpecialityRepository specialityRepository;


	@Override
	public Speciality convert(final String text) {
		Speciality res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.specialityRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}

}
