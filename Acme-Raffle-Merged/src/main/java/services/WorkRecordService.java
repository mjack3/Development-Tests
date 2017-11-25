
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Auditor;
import domain.WorkRecord;
import repositories.WorkRecordRepository;
import security.LoginService;

@Transactional
@Service
public class WorkRecordService {

	@Autowired
	private WorkRecordRepository	workRecordRepository;

	@Autowired
	private LoginService			loginService;
	@Autowired
	private AuditorService			auditorService;


	public WorkRecordService() {
		super();
	}

	public List<WorkRecord> findAll() {
		return workRecordRepository.findAll();
	}

	public WorkRecord save(WorkRecord entity) {

		WorkRecord workRecord = new WorkRecord();
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (workRecordRepository.exists(entity.getId())) {
			workRecord = workRecordRepository.findOne(entity.getId());
			Assert.isTrue(auditor.getCurricula().getWorkRecords().contains(workRecord));
			workRecord.setTitle(entity.getTitle());
			workRecord.setDescription(entity.getDescription());
			workRecord.setStartDate(entity.getStartDate());
			workRecord.setEndDate(entity.getEndDate());
			return workRecordRepository.save(workRecord);

		} else {
			System.out.println(entity.getTitle());
			WorkRecord workRecordnew = workRecordRepository.save(entity);
			auditor.getCurricula().getWorkRecords().add(workRecordnew);
			auditorService.save(auditor);

			return workRecordnew;

		}
	}
	public WorkRecord saveAF(WorkRecord entity) {

		WorkRecord workRecord = new WorkRecord();
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (workRecordRepository.exists(entity.getId())) {
			workRecord = workRecordRepository.findOne(entity.getId());
			Assert.isTrue(auditor.getCurricula().getWorkRecords().contains(workRecord));
			workRecord.setTitle(entity.getTitle());
			workRecord.setDescription(entity.getDescription());
			workRecord.setStartDate(entity.getStartDate());
			workRecord.setEndDate(entity.getEndDate());
			return workRecordRepository.saveAndFlush(workRecord);

		} else {
			WorkRecord workRecordnew = workRecordRepository.save(entity);
			auditor.getCurricula().getWorkRecords().add(workRecordnew);
			auditorService.save(auditor);

			return workRecordnew;

		}
	}

	public WorkRecord findOne(Integer id) {
		Assert.notNull(id);
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(auditor.getCurricula().getWorkRecords().contains(workRecordRepository.findOne(id)));

		return workRecordRepository.findOne(id);
	}

}
