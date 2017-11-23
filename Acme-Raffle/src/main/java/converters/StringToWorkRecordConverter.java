
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.WorkRecord;
import repositories.WorkRecordRepository;

@Component
@Transactional
public class StringToWorkRecordConverter implements Converter<String, WorkRecord> {

	@Autowired
	WorkRecordRepository workRecordRepository;


	@Override
	public WorkRecord convert(final String text) {
		WorkRecord res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.workRecordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}

}
