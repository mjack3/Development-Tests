
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.WorkRecord;

@Component
@Transactional
public class WorkRecordToStringConverter implements Converter<WorkRecord, String> {

	@Override
	public String convert(final WorkRecord workRecord) {
		String res;

		if (workRecord == null)
			res = null;
		else
			res = String.valueOf(workRecord.getId());

		return res;
	}

}
