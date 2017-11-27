
package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Auditor;
import domain.EducationRecord;
import repositories.EducationRecordRepository;
import security.LoginService;

@Transactional
@Service
public class EducationRecordService {

	@Autowired
	private EducationRecordRepository	educationRecordRepository;
	@Autowired
	private LoginService				loginService;
	@Autowired
	private AuditorService				auditorService;


	public EducationRecordService() {
		super();
	}

	public EducationRecord create() {
		final EducationRecord educationRecord = new EducationRecord();
		educationRecord.setTitle(new String());
		educationRecord.setDescription(new String());
		educationRecord.setStartDate(new Date());
		educationRecord.setEndDate(new Date());

		return educationRecord;
	}

	public List<EducationRecord> findAll() {
		return this.educationRecordRepository.findAll();
	}

	public EducationRecord save(final EducationRecord entity) {

		EducationRecord educationRecord = new EducationRecord();
		final Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (this.educationRecordRepository.exists(entity.getId())) {
			educationRecord = this.educationRecordRepository.findOne(entity.getId());
			Assert.isTrue(auditor.getCurricula().getEducationsRecords().contains(educationRecord));
			educationRecord.setTitle(entity.getTitle());
			educationRecord.setDescription(entity.getDescription());
			educationRecord.setStartDate(entity.getStartDate());
			educationRecord.setEndDate(entity.getEndDate());
			return this.educationRecordRepository.save(educationRecord);

		} else {
			final EducationRecord educationRecordnew = this.educationRecordRepository.save(entity);
			auditor.getCurricula().getEducationsRecords().add(educationRecordnew);
			this.auditorService.save(auditor);

			return educationRecordnew;

		}
	}

	public EducationRecord saveAF(final EducationRecord entity) {

		EducationRecord educationRecord = new EducationRecord();
		final Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (this.educationRecordRepository.exists(entity.getId())) {
			educationRecord = this.educationRecordRepository.findOne(entity.getId());
			Assert.isTrue(auditor.getCurricula().getEducationsRecords().contains(educationRecord));
			educationRecord.setTitle(entity.getTitle());
			educationRecord.setDescription(entity.getDescription());
			educationRecord.setStartDate(entity.getStartDate());
			educationRecord.setEndDate(entity.getEndDate());
			return this.educationRecordRepository.saveAndFlush(educationRecord);

		} else {
			final EducationRecord educationRecordnew = this.educationRecordRepository.save(entity);
			auditor.getCurricula().getEducationsRecords().add(educationRecordnew);
			this.auditorService.save(auditor);

			return educationRecordnew;

		}
	}

	public EducationRecord findOne(final Integer id) {
		Assert.notNull(id);
		final Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(auditor.getCurricula().getEducationsRecords().contains(this.educationRecordRepository.findOne(id)));

		return this.educationRecordRepository.findOne(id);
	}

}
