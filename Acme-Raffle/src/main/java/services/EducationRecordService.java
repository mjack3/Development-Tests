
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
		EducationRecord educationRecord = new EducationRecord();
		educationRecord.setTitle(new String());
		educationRecord.setDescription(new String());
		educationRecord.setStartDate(new Date());
		educationRecord.setEndDate(new Date());

		return educationRecord;
	}

	public List<EducationRecord> findAll() {
		return educationRecordRepository.findAll();
	}

	public EducationRecord save(EducationRecord entity) {

		EducationRecord educationRecord = new EducationRecord();
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (educationRecordRepository.exists(entity.getId())) {
			educationRecord=educationRecordRepository.findOne(entity.getId());
			Assert.isTrue(auditor.getCurricula().getEducationsRecords().contains(educationRecord));
			educationRecord.setTitle(entity.getTitle());
			educationRecord.setDescription(entity.getDescription());
			educationRecord.setStartDate(entity.getStartDate());
			educationRecord.setEndDate(entity.getEndDate());
			return educationRecordRepository.save(educationRecord);

		} else {
			EducationRecord educationRecordnew = educationRecordRepository.save(entity);
			auditor.getCurricula().getEducationsRecords().add(educationRecordnew);
			auditorService.save(auditor);

			return educationRecordnew;

		}
	}
	
	public EducationRecord saveAF(EducationRecord entity) {

		EducationRecord educationRecord = new EducationRecord();
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (educationRecordRepository.exists(entity.getId())) {
			educationRecord=educationRecordRepository.findOne(entity.getId());
			Assert.isTrue(auditor.getCurricula().getEducationsRecords().contains(educationRecord));
			educationRecord.setTitle(entity.getTitle());
			educationRecord.setDescription(entity.getDescription());
			educationRecord.setStartDate(entity.getStartDate());
			educationRecord.setEndDate(entity.getEndDate());
			return educationRecordRepository.saveAndFlush(educationRecord);

		} else {
			EducationRecord educationRecordnew = educationRecordRepository.save(entity);
			auditor.getCurricula().getEducationsRecords().add(educationRecordnew);
			auditorService.saveAF(auditor);

			return educationRecordnew;

		}
	}

	public EducationRecord findOne(Integer id) {
		Assert.notNull(id);
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(auditor.getCurricula().getEducationsRecords().contains(educationRecordRepository.findOne(id)));

		return educationRecordRepository.findOne(id);
	}

}
